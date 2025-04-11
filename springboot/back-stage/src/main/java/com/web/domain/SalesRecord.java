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
* 销售记录
* </p>
*
* @author admin admin@126.com
*
*/
@Data
@TableName("sales_record")
@ApiModel(value = "SalesRecord对象", description = "销售记录")
public class SalesRecord implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("库存ID")
    private Integer inventoryId;
    @ApiModelProperty("销售数量")
    private Integer salesQuantity;
    @ApiModelProperty("销售时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date salesTime;
    @ApiModelProperty("客户名称")
    private String customerName;

}
