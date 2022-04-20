package cn.yanghuisen.covid19.controller;

import cn.hutool.core.util.StrUtil;
import cn.yanghuisen.covid19.common.Result;
import cn.yanghuisen.covid19.pojo.City;
import cn.yanghuisen.covid19.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
@RestController
@RequestMapping("/city")
public class CityController {

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping("/findCityByProId")
    public Result<List<City>> findCityByProId(String proId){
        return Result.success(cityService.findCityByProId(proId));
    }

    @RequestMapping("/findCityCount")
    public Result<List<Map<String,Object>>> findCityCount(String proId){
        if (StrUtil.isBlank(proId)){
            return Result.fail("参数不能为空",null);
        }
        return Result.success(cityService.findCityCount(proId));
    }
}
