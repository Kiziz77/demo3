package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.CustomerOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.CustomerOrderReq;
import com.web.domain.Resp.CustomerOrderResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Date;

/**
 * <p>
 * 购买订单 Mapper 接口
 * </p>
 */
@Mapper
public interface CustomerOrderMapper extends BaseMapper<CustomerOrder> {

    List<CustomerOrderResp> selectRespList(Page page, @Param("req") CustomerOrderReq req);
}
