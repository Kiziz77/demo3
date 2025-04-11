package com.web.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
* <p>
* 购买订单
* </p>
*
* @author admin admin@126.com
*
*/
@Data
@TableName("customer_order")
@ApiModel(value = "CustomerOrder对象", description = "购买订单")
public class CustomerOrder implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("客户姓名")
    private String customerName;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("药品ID")
    private Integer drugId;
    @ApiModelProperty("购买数量")
    private Integer quantity;
    @ApiModelProperty("订单时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

}
