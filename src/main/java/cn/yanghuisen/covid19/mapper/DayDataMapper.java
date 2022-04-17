package cn.yanghuisen.covid19.mapper;

import cn.yanghuisen.covid19.pojo.DayData;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/15
 */
@Mapper
public interface DayDataMapper {
    /**
     * 新增每天数据
     * @param dayData DayData
     * @return result
     */
    int addDayData(DayData dayData);

    /**
     * 删除每天数据
     * @param dayData DayData
     * @return result
     */
    int delDayData(DayData dayData);

    /**
     * 根据CityId查找所有的DayData
     * @param cityId 市/区的ID
     * @return result
     */
    List<DayData> findDayDataByCityId(String cityId);

    /**
     * 获取最大的DayData日期
     * @return result
     */
    Date getMaxDayDataDate();
}
