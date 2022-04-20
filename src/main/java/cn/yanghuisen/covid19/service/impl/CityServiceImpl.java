package cn.yanghuisen.covid19.service.impl;

import cn.yanghuisen.covid19.mapper.CityMapper;
import cn.yanghuisen.covid19.pojo.City;
import cn.yanghuisen.covid19.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
@Service
public class CityServiceImpl implements CityService {

    private CityMapper cityMapper;

    @Autowired
    public void setCityMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public List<City> findCityByProId(String proId) {
        return cityMapper.findCityByProId(Long.parseLong(proId));
    }

    @Override
    public List<Map<String, Object>> findCityCount(String proId) {
        return cityMapper.findCityCount(Long.parseLong(proId));
    }
}
