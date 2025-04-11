package com.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.Products;
import com.web.domain.Req.ProductsReq;
import com.web.domain.Resp.ProductsResp;
import com.web.utils.PageUtils;


/**

* @description 商品Service
*/
public interface ProductsService extends IService<Products> {
    
    PageUtils selectPage(ProductsReq req);

    ProductsResp info(Long id);
}
