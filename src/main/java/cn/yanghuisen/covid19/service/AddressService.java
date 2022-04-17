package cn.yanghuisen.covid19.service;

import java.util.List;
import java.util.Map;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
public interface AddressService {
    List<Map<String,Object>> findAddress(String address,String page,String limit);
}
