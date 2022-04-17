package cn.yanghuisen.covid19.service;

import java.util.List;
import java.util.Map;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
public interface AddressService {
    /**
     * 根据居住地查找居住地
     * @param address  居住地
     * @param page 分页
     * @param limit 每页数量
     * @return result
     */
    List<Map<String,Object>> findAddress(String address,String page,String limit);
}
