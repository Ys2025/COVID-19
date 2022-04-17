package cn.yanghuisen.covid19.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.yanghuisen.covid19.handler.AddressAnalysisHandler;
import cn.yanghuisen.covid19.mapper.DayDataMapper;
import cn.yanghuisen.covid19.mapper.LogMapper;
import cn.yanghuisen.covid19.pojo.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/15
 */
@Component
@Slf4j
public class ShCovid19Job {

    @Value("${covid-19.shCovid19Job:false}")
    private boolean runJob;


    private AddressAnalysisHandler addressAnalysisHandler;

    private DayDataMapper dayDataMapper;

    private LogMapper logMapper;

    @Autowired
    @Qualifier("shAddressAnalysisHandlerImpl")
    public void setAddressAnalysisHandler(AddressAnalysisHandler addressAnalysisHandler) {
        this.addressAnalysisHandler = addressAnalysisHandler;
    }

    @Autowired
    public void setLogMapper(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Autowired
    public void setDayDataMapper(DayDataMapper dayDataMapper) {
        this.dayDataMapper = dayDataMapper;
    }

    @Scheduled(cron = "0 1/15 8-10 * * ?")
    public void run(){
        if (!runJob){
            log.info("Stop JOb....");
            return;
        }
        // 获取最新的解析日期
        Date maxDayDataDate = dayDataMapper.getMaxDayDataDate();
        // 获取昨天的日期
        String yesterdayStr = DateUtil.formatDate(DateUtil.yesterday());
        Date yesterday = DateUtil.parse(yesterdayStr);
        // 比较日期相等，如果相等则直接Return
        if (maxDayDataDate.equals(yesterday)){
            log.info("{}已解析...",yesterdayStr);
            return;
        }
        // 上海发布的公众号地址
        String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid=MjM5NTA5NzYyMA==&type=9&query=&token=1337377029&lang=zh_CN&f=json&ajax=1";
        // cookie
        // 此处需要在resources下创建cookie.txt 文件 其内容为自己的cookie
        String cookie = ResourceUtil.readUtf8Str("cookie.txt");
        HttpRequest request = HttpUtil.createGet(url).cookie(cookie);
        String jsonContent = request.execute().body();

        if (JSONUtil.isTypeJSON(jsonContent)){
            JSONObject jsonObject = JSONUtil.parseObj(jsonContent);
            JSONArray jsonArray = jsonObject.getJSONArray("app_msg_list");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                String title = itemJsonObject.getStr("title");
                String link = itemJsonObject.getStr("link");
                link = link.replaceAll("http:","https:");
                if (ReUtil.isMatch("(.*?)（0-24时）本市各区确诊病例、无症状感染者居住地信息",title) || title.contains("居住地信息")){
                    Map<String,Object> params = new HashMap<>(2);
                    Long proId = 1514979369040113664L;
                    params.put("proId", proId);
                    params.put("url", link);
                    try {
                        addressAnalysisHandler.analysis(params);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log log = new Log();
                        log.setId(IdUtil.getSnowflakeNextId());
                        log.setCodeStr("ShCovid19Job#run");
                        log.setDetail(ExceptionUtil.stacktraceToString(e));
                        logMapper.addLog(log);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new ShCovid19Job().run();
    }
}
