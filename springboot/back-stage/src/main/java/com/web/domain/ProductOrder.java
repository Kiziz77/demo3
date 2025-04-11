package com.web.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商品订单表
 * @TableName product_order
 */
@Data
@TableName(value ="product_order")
public class ProductOrder implements Serializable {
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
    @TableField(fill = FieldFill.INSERT)
    private Date creatTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}