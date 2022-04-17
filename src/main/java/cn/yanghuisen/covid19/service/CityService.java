package cn.yanghuisen.covid19.service;

import cn.yanghuisen.covid19.pojo.City;

import java.util.List;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
public interface CityService {
    /**
     * 根据省ID查找市/区
     * @param proId 省的ID
     * @return result
     */
    List<City> findCityByProId(String proId);
}
