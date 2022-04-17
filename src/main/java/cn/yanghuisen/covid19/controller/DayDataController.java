package cn.yanghuisen.covid19.controller;

import cn.yanghuisen.covid19.common.Result;
import cn.yanghuisen.covid19.pojo.DayData;
import cn.yanghuisen.covid19.service.DayDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
@RestController
@RequestMapping("/dayData")
public class DayDataController {

    private DayDataService dayDataService;

    @Autowired
    public void setDayDataService(DayDataService dayDataService) {
        this.dayDataService = dayDataService;
    }

    @RequestMapping("/findDayDataByCityId")
    public Result<List<DayData>> findDayDataByCityId(String cityId){
        PageHelper.startPage(1,10);
        List<DayData> dayDataList = dayDataService.findDayDataByCityId(cityId);
        PageInfo<DayData> dataPageInfo = new PageInfo<>(dayDataList);
        List<DayData> dataList = dataPageInfo.getList();
        Collections.reverse(dataList);
        return Result.success(dataList);

    }
}
