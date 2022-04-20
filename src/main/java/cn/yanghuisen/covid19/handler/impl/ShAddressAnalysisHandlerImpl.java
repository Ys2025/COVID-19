package cn.yanghuisen.covid19.handler.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.yanghuisen.covid19.handler.AddressAnalysisHandler;
import cn.yanghuisen.covid19.mapper.AddressMapper;
import cn.yanghuisen.covid19.mapper.CityMapper;
import cn.yanghuisen.covid19.mapper.DayDataMapper;
import cn.yanghuisen.covid19.mapper.LogMapper;
import cn.yanghuisen.covid19.pojo.Address;
import cn.yanghuisen.covid19.pojo.City;
import cn.yanghuisen.covid19.pojo.DayData;
import cn.yanghuisen.covid19.pojo.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 * 上海居住地解析
 */
@Slf4j
@Component
public class ShAddressAnalysisHandlerImpl implements AddressAnalysisHandler {
    private CityMapper cityMapper;
    private DayDataMapper dayDataMapper;
    private AddressMapper addressMapper;
    private LogMapper logMapper;

    @Autowired
    public void setCityMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Autowired
    public void setDayDataMapper(DayDataMapper dayDataMapper) {
        this.dayDataMapper = dayDataMapper;
    }

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Autowired
    public void setLogMapper(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public void analysis(Map<String,Object> params) throws Exception {
        // 获取省/市
        long proId = MapUtil.getLong(params, "proId",0L);
        // url
        String url = MapUtil.getStr(params, "url");
        if (0 == proId || StrUtil.isBlank(url)){
            throw new Exception("错误的参数");
        }
        // 查找该省/市的所有的市/区
        List<City> cityList = cityMapper.findCityByProId(proId);
        // 获取文章源码
        HttpRequest request = HttpUtil.createGet(url);
        String body = request.execute().body();
        // 正在表达式
        String regex = "<section data-id=\"72469\" data-tools=\"135编辑器\">(.*?)</section>";
        // 正则匹配 得到16个区的信息
        ArrayList<String> dataDetail = ReUtil.findAll(regex, body, 0, new ArrayList<String>());
        for (String str : dataDetail) {
            // 替换字符串，避免因每次页面源码不同导致的正则匹配失败
            str = str.replaceAll("</span><span style=\"color: inherit;font-family: mp-quote, -apple-system-font, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei UI&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif;text-align: justify;\">","");
            str = str.replaceAll("</span><span style=\"font-size: 16px;color: inherit;font-family: mp-quote, -apple-system-font, BlinkMacSystemFont, Arial, sans-serif;text-align: justify;\">","");
            str = str.replaceAll("</span><span style=\"font-size: 16px;color: inherit;font-family: mp-quote, -apple-system-font, BlinkMacSystemFont, Arial, sans-serif;\">","");
            str = str.replaceAll("<span style=\"font-family: 微软雅黑, sans-serif;font-size: 16px;\">","<span style=\"font-size: 16px;\">");
            str = str.replaceAll("<span style=\"color: inherit;caret-color: red;font-size: 16px;\">","<span style=\"font-size: 16px;\">");
            str = str.replaceAll("<span style=\"font-size: 16px;color: inherit;caret-color: red;\">","<span style=\"font-size: 16px;\">");
            str = str.replaceAll("<span style=\"color: inherit;caret-color: red;\">","");
            str = str.replaceAll("<span style=\"color: inherit;font-family: mp-quote, -apple-system-font, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei UI&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif;text-align: justify;\">","");
            str = str.replaceAll("<br  />","");
            // 正则
            String regex1 = "<span style=\"font-size: 16px;\">(.*?)</span>";
            // 正则匹配，得到病例数量和病例居住地
            ArrayList<String> itemDateDetail = ReUtil.findAll(regex1, str, 0, new ArrayList<>());
            long cityId = -1;
            // 通过雪花算法生成ID
            long id = IdUtil.getSnowflakeNextId();
            // 遍历数据并写入数据库
            for (int i = 0; i < itemDateDetail.size(); i++) {
                String itemStr = itemDateDetail.get(i);
                if (itemStr.contains("1111")){
                    int ss = 0;
                }
                // 字符替换，避免因正则匹配不到
                itemStr = itemStr.replaceAll("例确诊病例","例本土确诊病例");
                itemStr = itemStr.replaceAll("例本土新冠肺炎确诊病例","例本土确诊病例");
                itemStr = itemStr.replaceAll("例新冠肺炎本土确诊病例","例本土确诊病例");
                // 一般来说 第一个就是改区的确诊数量和无症状数量
                if (i == 0){
                    // 正则表达式
                    String regex2 = "<span style=\"font-size: 16px;\">(.*?)，(.*?)新增(.*?)例本土确诊病例，新增(.*?)例本土无症状感染者，分别居住于：</span>";
                    // 判断是否满足该正则
                    if (ReUtil.isMatch(regex2,itemStr)){
                        // 通过正则获取满足的所有分组
                        List<String> allGroups = ReUtil.getAllGroups(Pattern.compile(regex2), itemStr);
                        // 时间
                        String date = allGroups.get(1);
                        // 地区名称
                        String name = allGroups.get(2);
                        // 确诊
                        String diagnosis = allGroups.get(3);
                        // 无症状
                        String asymptomatic = allGroups.get(4);
                        log.info("{}: {}例确认病例, {}例无症状感染者",name,diagnosis,asymptomatic);
                        // 遍历市/区
                        for (City city : cityList) {
                            // 如果市/区的名字相同则写入数据库，记录改天确诊数量和无症状数量
                            if (city.getName().equals(name)) {
                                // 市/区的ID
                                cityId = city.getId();
                                DayData dayData = new DayData();
                                // id
                                dayData.setId(id);
                                // 市/区的ID
                                dayData.setCityId(cityId);
                                // 日期
                                dayData.setDate(DateUtil.parse(date,"yyyy年MM月dd日"));
                                // 确诊数量
                                dayData.setDiagnosis(Integer.valueOf(diagnosis));
                                // 无症状数量
                                dayData.setAsymptomatic(Integer.valueOf(asymptomatic));
                                // 删除历史的该天数据
                                dayDataMapper.delDayData(dayData);
                                // 插入新的数据
                                dayDataMapper.addDayData(dayData);
                                // 删除历史的该天数据
                                addressMapper.delAddress(cityId,DateUtil.parse(date,"yyyy年MM月dd日"));
                                break;
                            }
                        }
                    }else {
                        Log log1 = new Log("ShAddressAnalysisHandlerImpl#analysis|fail1", str);
                        log1.setId(IdUtil.getSnowflakeNextId());
                        logMapper.addLog(log1);
                        Log log2 = new Log("ShAddressAnalysisHandlerImpl#analysis|fail2", itemStr);
                        log2.setId(IdUtil.getSnowflakeNextId());
                        logMapper.addLog(log2);
                        break;
                    }
                }else {
                    // 获取居住地
                    String addressStr = ReUtil.get(regex1, itemStr, 1);
                    // 替换字符
                    addressStr = addressStr.replaceAll("，","");
                    addressStr = addressStr.replaceAll("。","");
                    addressStr = addressStr.replaceAll("&nbsp;","");
                    addressStr = addressStr.replaceAll("、","");
                    addressStr = addressStr.replaceAll("<br  />","");
                    if (StrUtil.isNotBlank(addressStr) && !"已对相关居住地落实终末消毒措施".equals(addressStr)){
                        Address address = new Address();
                        // id
                        address.setId(IdUtil.getSnowflakeNextId());
                        // 对应的天的ID
                        address.setDayDataId(id);
                        // 居住地
                        address.setAddress(addressStr);
                        addressMapper.addAddress(address);
                    }
                }
            }
        }
    }
}
