package com.web.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.domain.Address;
import com.web.exception.BusinessException;
import com.web.mapper.AddressMapper;
import com.web.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @description 收获地址Service实现
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Resource
    private HttpServletRequest request;

    /**
     * 添加收货地址
     * @param address
     */
    @Override
    public void add(Address address) {
        address.setId(null);
        Integer currentUserId = (Integer) request.getSession().getAttribute("userId");
        Assert.isFalse(currentUserId == null,() -> new BusinessException("为获取用户信息，添加收货地址失败"));
        address.setUserId(currentUserId);
        Integer isDefault = address.getIsDefault();
        Assert.isFalse(isDefault == null,() -> new BusinessException("没有填写是否为默认地址，添加收货地址失败"));
        if (isDefault == 1) {
             // 如果是默认地址，则将其他地址的默认状态改为0
            update().eq("user_id", currentUserId).set("is_default", 0).update();
        }
        save(address);
    }

    @Override
    public List<Address> queryList() {
        Integer currentUserId = (Integer) request.getSession().getAttribute("userId");
        List<Address> addresses = baseMapper.selectList(new LambdaQueryWrapper<Address>().eq(Address::getUserId, currentUserId));
        return addresses;
    }

    @Override
    public void edit(Address address) {
        Integer isDefault = address.getIsDefault();
        Integer userId = address.getUserId();
        Assert.isFalse(isDefault == null,() -> new BusinessException("没有填写是否为默认地址，添加收货地址失败"));
        if (isDefault == 1) {
            // 如果是默认地址，则将其他地址的默认状态改为0
            update().eq("user_id", userId).set("is_default", 0).update();
        }
        updateById(address);
    }
}




