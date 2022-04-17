package cn.yanghuisen.covid19.mapper;

import cn.yanghuisen.covid19.pojo.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/15
 */
@Mapper
public interface AddressMapper {
    /**
     * 增加居住地
     * @param address address
     * @return result
     */
    int addAddress(Address address);

    /**
     * 根据市和日期删除
     * @param cityId 市
     * @param date 日期
     * @return result
     */
    int delAddress(Long cityId, Date date);

    /**
     * 根据Address查找Address
     * @param address 居住地
     * @return result
     */
    List<Map<String,Object>> findAddressByAddress(String address);
}
