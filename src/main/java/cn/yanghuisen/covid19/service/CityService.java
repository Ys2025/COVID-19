package cn.yanghuisen.covid19.service;

import cn.yanghuisen.covid19.pojo.City;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据省/市ID,获取市/区的居住地数量
     * @param proId proId
     * @return result
     */
    List<Map<String,Object>> findCityCount(String proId);
}
