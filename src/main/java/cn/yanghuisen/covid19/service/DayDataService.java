package cn.yanghuisen.covid19.service;

import cn.yanghuisen.covid19.pojo.DayData;

import java.util.List;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
public interface DayDataService {

    /**
     * 根据CityID查找DayDate
     * @param cityId 市/区
     * @return result
     */
    List<DayData> findDayDataByCityId(String cityId);
}
