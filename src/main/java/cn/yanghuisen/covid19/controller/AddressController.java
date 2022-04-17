package cn.yanghuisen.covid19.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.yanghuisen.covid19.common.Result;
import cn.yanghuisen.covid19.common.TableResult;
import cn.yanghuisen.covid19.handler.AddressAnalysisHandler;
import cn.yanghuisen.covid19.job.ShCovid19Job;
import cn.yanghuisen.covid19.service.AddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    private AddressService addressService;
    @Autowired
    private ShCovid19Job shCovid19Job;

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping("/getAddress")
    public TableResult<Map<String,Object>> getAddress(String address,String page,String limit){
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
        List<Map<String, Object>> addressList = addressService.findAddress(address, page, limit);
        PageInfo<Map<String, Object>> mapPageInfo = new PageInfo<>(addressList);
        return TableResult.success(mapPageInfo.getTotal(),mapPageInfo.getList());
    }

    @RequestMapping("/analysis")
    public Result<String> analysis(@RequestBody Map<String,Object> params) {
        String beanName = MapUtil.getStr(params, "beanName");
        // 获取AddressAnalysisHandler类的所有在容器中的BeanName
        String[] beanNames = SpringUtil.getBeanNamesForType(AddressAnalysisHandler.class);
        if (null == beanNames || beanNames.length == 0){
            return Result.fail("未找到对应的Bean");
        }
        // 判断是否存在对应的Bean
        if (!Arrays.asList(beanNames).contains(beanName)){
            return Result.fail("未找到对应的Bean");
        }else {
            // 获取Bean
            Object bean = SpringUtil.getBean(beanName);
            // instanceof新特性 可以把强制转换简写
            if (!(bean instanceof AddressAnalysisHandler addressAnalysisHandler)){
                return Result.fail("未找到对应的Bean");
            }else {
                Result<String> result;
                try {
                    addressAnalysisHandler.analysis(params);
                    result = Result.success("解析成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    result = Result.error(e.getMessage());
                }
                return result;
            }
        }
    }

    @RequestMapping("/demo")
    public Result<String> demo(){
        shCovid19Job.run();
        return Result.success("");
    }
}