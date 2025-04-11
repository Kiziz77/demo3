package com.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.web.domain.ProductOrder;
import com.web.domain.Req.ProductOrderReq;
import com.web.utils.PageUtils;

import java.util.List;
import java.util.Map;


/**

* @description 商品订单Service
*/
public interface ProductOrderService extends IService<ProductOrder> {


    /**
     * 购买商品
     * @param productOrder
     */
    Map<String, Object> buyProduct(ProductOrderReq productOrder);

    PageUtils queryMyOrder(ProductOrderReq productOrder);

    Map<String,Object> addShoppingCart(ProductOrderReq productOrder);

    List<ProductOrder> queryShoppingCart();

    Map<String, Object> editShoppingCart(ProductOrderReq productOrder);

    Map<String, Object> orderOperation(ProductOrderReq productOrder);

    void deleteByIdTableName(Long id, String products);
}
