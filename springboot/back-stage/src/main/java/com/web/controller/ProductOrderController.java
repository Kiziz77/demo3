package com.web.controller;

import com.web.domain.ProductOrder;
import com.web.domain.Req.ProductOrderReq;
import com.web.service.ProductOrderService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/productOrder")
public class ProductOrderController {

    @Autowired
    private ProductOrderService service;

    /**
     * 购买商品
     * @return
     */
    @PostMapping("/buyProduct")
    public Result buyProduct(@RequestBody ProductOrderReq productOrder) {
        Map<String, Object> map = service.buyProduct(productOrder);
        return Result.success(map);
    }
    /**
     * 加入购物车
     * @return
     */
    @PostMapping("/addShoppingCart")
    public Result addShoppingCart(@RequestBody ProductOrderReq productOrder) {
        Map<String,Object> map = service.addShoppingCart(productOrder);
        return Result.success(map);
    }


    /**
     * 修改购物车数量价格
     * @return
     */
    @PutMapping("/editShoppingCart")
    public Result editShoppingCart(@RequestBody ProductOrderReq productOrder) {
        Map<String,Object> map = service.editShoppingCart(productOrder);
        return Result.success(map);
    }

    /**
     * 移出购物车
     * @return
     */
    @DeleteMapping("/deletedGoods/{id}")
    public Result deletedGoods(@PathVariable("id") Integer id) {
        service.removeById(id);
        return Result.success();
    }
    /**
     * 查询购物车
     * @return
     */
    @GetMapping("/queryShoppingCart")
    public Result queryShoppingCart() {
        List<ProductOrder> list = service.queryShoppingCart();
        return Result.success(list);
    }

    /**
     * 查询我的订单
     * @return
     */
    @PostMapping("/queryMyOrderPage")
    public Result queryMyOrderPage(@RequestBody ProductOrderReq productOrder) {
        PageUtils page = service.queryMyOrder(productOrder);
        return Result.success(page);
    }

    /**
     * 订单操作 退款/确认收货/退货/发货/删除
     * @return
     */
    @PostMapping("/orderOperation")
    public Result orderOperation(@RequestBody ProductOrderReq productOrder) {
        Map<String,Object> map = service.orderOperation(productOrder);
        return Result.success(map);
    }
}
