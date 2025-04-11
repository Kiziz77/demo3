package com.web.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.ProductOrder;
import com.web.domain.Req.ProductOrderReq;
import com.web.domain.Resp.ProductOrderResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @description 商品订单Mapper
*/
@Mapper
public interface ProductOrderMapper extends BaseMapper<ProductOrder> {

    List<ProductOrderResp> selectRespList(Page<ProductOrderResp> page, @Param("req") ProductOrderReq req);
}




