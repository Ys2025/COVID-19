package cn.yanghuisen.covid19.mapper;

import cn.yanghuisen.covid19.pojo.City;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/15
 */
@Mapper
public interface CityMapper {
    /**
     * 增加城市
     * @param city City
     * @return result
     */
    int addCity(City city);

    /**
     * 更具省ID获取城市
     * @param proId 省
     * @return result
     */
    List<City> findCityByProId(Long proId);
}
