package com.web.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.domain.LikeCollect;
import com.web.domain.ProductOrder;
import com.web.domain.Products;
import com.web.domain.Req.ProductOrderReq;
import com.web.domain.Resp.ProductOrderResp;
import com.web.exception.BusinessException;
import com.web.mapper.ProductOrderMapper;
import com.web.service.ProductOrderService;
import com.web.service.ProductsService;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.*;


/**
* @description 商品订单service实现类
*/
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder> implements ProductOrderService {

    private static final Random random = new Random();

    @Resource
    private HttpServletRequest request;

    /**
     * 购买商品，创建订单
     *
     * @param productOrder
     */
    @Override
    public Map<String, Object> buyProduct(ProductOrderReq productOrder) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        List<ProductOrder> productOrderList = productOrder.getProductOrderList();

        if (CollUtil.isEmpty(productOrderList)) {
            map.put("code", 500);
            map.put("msg", "商品列表不能为空");
            return map;
        }
        List<ProductOrder> addList = new ArrayList<>();
        for (ProductOrder order : productOrderList) {
            // 生成订单编号
            long timestamp = System.currentTimeMillis();
            int randomInt = random.nextInt(999999); // 生成一个0到999999之间的随机整数
            String orderNumber = String.format("%d%06d", timestamp, randomInt);

            order.setOrderNumber(orderNumber);
            order.setUserId(userId);
            order.setOrderType(productOrder.getOrderType());
            order.setOrderStatus(productOrder.getOrderStatus());
            order.setPaymentMethod(productOrder.getPaymentMethod());
            order.setAddress(productOrder.getAddress());
            order.setPhone(productOrder.getPhone());
            order.setConsignee(productOrder.getConsignee());
            order.setRemark(productOrder.getRemark());
            order.setBuyDatetime(new Date());

            order.setId(null);
            addList.add(order);
        }
        // 删除购物车
        List<Integer> ids = productOrder.getIds();
        if (!CollUtil.isEmpty(ids)) {
            removeByIds(ids);
        }

        saveBatch(addList);
        map.put("code", 200);
        map.put("msg", "success");
        return map;
    }

    /**
     * 查询我的订单
     *
     * @return
     */
    @Override
    public PageUtils queryMyOrder(ProductOrderReq req) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");
        if (!role.equals("管理员")){
            req.setUserId(userId);
        }
        Page<ProductOrderResp> page = new Query<ProductOrderResp>(req.getPageInfo()).getPage();
        page.setRecords(baseMapper.selectRespList(page, req));

        return new PageUtils(page);
    }

    /**
     * 加入购物车
     * @param productOrder
     */
    @Override
    public Map<String,Object> addShoppingCart(ProductOrderReq productOrder) {
        //检查是否加入购物车
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        Integer momentId = productOrder.getMomentId();
        String signName = productOrder.getSignName();
        Long count = baseMapper.selectCount(new LambdaQueryWrapper<ProductOrder>()
                .eq(ProductOrder::getUserId, userId)
                .eq(ProductOrder::getMomentId, momentId)
                .eq(ProductOrder::getSignName, signName)
                .eq(ProductOrder::getOrderType, 1)
        );
        Map<String,Object> map = new HashMap<>();
        if (count > 0) {
            map.put("code", 1);
            map.put("message", "该商品已加入购物车");
            return map;
        }
        ProductOrder order = new ProductOrder();
        BeanUtil.copyProperties(productOrder,order);
        order.setOrderType(1);// 购物车
        order.setUserId(userId);
        save(order);
        map.put("code", 200);
        map.put("message", "加入购物车成功");
        return map;
    }

    /**
     * 查询购物车
     * @return
     */
    @Override
    public List<ProductOrder> queryShoppingCart() {

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        List<ProductOrder> productOrders = baseMapper.selectList(new LambdaQueryWrapper<ProductOrder>()
                .eq(ProductOrder::getUserId, userId)
                .eq(ProductOrder::getOrderType, 1)
        );
        return productOrders;
    }

    @Override
    public Map<String, Object> editShoppingCart(ProductOrderReq productOrder) {
        ProductOrder order = new ProductOrder();
        BeanUtil.copyProperties(productOrder,order);
        // 计算总价
        Integer buyNumber = order.getBuyNumber();
        BigDecimal price = order.getPrice();
        order.setTotalPrice(price.multiply(new BigDecimal(buyNumber)));
        updateById(order);
        return null;
    }

    @Override
    public Map<String, Object> orderOperation(ProductOrderReq productOrder) {
        Integer statusIndex = productOrder.getStatusIndex();
        Integer id = productOrder.getId();
        ProductOrder byId = this.getById(id);

        Map<String, Object> map = new HashMap<>();
        // 0：退款，1：确认收货，2：退货 ,3:发货，4：删除
        switch (statusIndex){
            case 0:
            case 2:
                byId.setOrderStatus(4);
                // 进行金额返回
                break;
            case 1:
                byId.setOrderStatus(3);
                break;
            case 3:
                byId.setOrderStatus(2);
                break;
            case 4:
                this.removeById(id);
                return map;
        }
        this.updateById(byId);
        return map;
    }

    /**
     * 删除所有用户对应商品的购物车信息
     * @param id 商品id
     * @param products
     */
    @Override
    public void deleteByIdTableName(Long id, String products) {
        baseMapper.delete(new LambdaQueryWrapper<ProductOrder>().eq(ProductOrder::getMomentId, id).eq(ProductOrder::getSignName, products).eq(ProductOrder::getOrderType, 1));
    }
}




