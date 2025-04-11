package com.web.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.Products;
import com.web.domain.Req.ProductsReq;
import com.web.domain.Resp.ProductsResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @description 商品Mapper
*/
@Mapper
public interface ProductsMapper extends BaseMapper<Products> {
    
    List<ProductsResp> selectRespList(Page page, @Param("req") ProductsReq req);

}




