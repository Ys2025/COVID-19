package cn.yanghuisen.covid19.service.impl;

import cn.yanghuisen.covid19.mapper.DayDataMapper;
import cn.yanghuisen.covid19.pojo.DayData;
import cn.yanghuisen.covid19.service.DayDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
@Service
public class DayDataServiceImpl implements DayDataService {

    private DayDataMapper dayDataMapper;

    @Autowired
    public void setDayDataMapper(DayDataMapper dayDataMapper) {
        this.dayDataMapper = dayDataMapper;
    }

    @Override
    public List<DayData> findDayDataByCityId(String cityId) {
        return dayDataMapper.findDayDataByCityId(cityId);
    }
}
