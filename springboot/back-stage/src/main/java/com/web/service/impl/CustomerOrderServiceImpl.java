package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.CustomerOrder;
import com.web.domain.Req.CustomerOrderReq;
import com.web.domain.Resp.CustomerOrderResp;
import com.web.mapper.CustomerOrderMapper;
import com.web.service.CustomerOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;

/**
 * <p>
 * 购买订单 服务实现类
 * </p>0
 *
 */
@Service
public class CustomerOrderServiceImpl extends ServiceImpl<CustomerOrderMapper, CustomerOrder> implements CustomerOrderService {

    @Resource
    private CustomerOrderMapper customerOrderMapper;


    @Override
    public PageUtils selectPage(CustomerOrderReq req) {
        Page<CustomerOrderResp> page = new Query<CustomerOrderResp>(req.getPageInfo()).getPage();
        page.setRecords(customerOrderMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    @Override
    public CustomerOrderResp info(Long id) {
        return BeanUtil.copyProperties(customerOrderMapper.selectById(id), CustomerOrderResp.class);
    }
}
