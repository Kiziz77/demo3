package com.web.domain.Req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.domain.ProductOrder;
import com.web.utils.PageInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品订单表
 * @TableName product_order
 */
@Data
public class ProductOrderReq extends PageInfo {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 关联主体ID（数据id）
     */
    private Integer momentId;

    /**
     * '关联模块 表名
     */
    private String signName;

    /**
     * 路由
     */
    private String fullPath;

    /**
     * 商品图片
     */
    private String pictures;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 购买数量
     */
    private Integer buyNumber;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 折扣单价
     */
    private BigDecimal discountPrice;

    /**
     * 总价格
     */
    private BigDecimal totalPrice;

    /**
     * 折扣总价
     */
    private BigDecimal discountTotalPrice;

    /**
     * 类型  0：订单，1：购物车
     */
    private Integer orderType;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 订单状态 0:待支付，1：已支付，已发货，已完成，已退款，已取消
     */
    private Integer orderStatus;

    /**
     * 支付方式 0：微信，1：支付宝
     */
    private Integer paymentMethod;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 备注
     */
    private String remark;

    /**
     * 物流
     */
    private String logistics;

    /**
     * 购买时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date buyDatetime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatTime;

    private List<ProductOrder> productOrderList;

    /**
     * 是否是购物车购买
     */
    private List<Integer> ids;
    /**
     * 订单操作 0：退款，1：确认收货，2：退货
     */
    private Integer statusIndex;

    public PageInfo getPageInfo() {
        return this;
    }
}