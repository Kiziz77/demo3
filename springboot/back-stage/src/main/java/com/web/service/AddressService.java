package com.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.Address;

import java.util.List;


/**

* @description 收货地址Service
*/
public interface AddressService extends IService<Address> {

    void add(Address address);

    List<Address> queryList();

    void edit(Address address);
}
