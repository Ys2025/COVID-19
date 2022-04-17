package cn.yanghuisen.covid19.service.impl;

import cn.yanghuisen.covid19.mapper.AddressMapper;
import cn.yanghuisen.covid19.pojo.Address;
import cn.yanghuisen.covid19.service.AddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/16
 */
@Service
public class AddressServiceImpl implements AddressService {

    private AddressMapper addressMapper;

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public List<Map<String,Object>> findAddress(String address,String page,String limit) {
        return addressMapper.findAddressByAddress(address);
    }
}
