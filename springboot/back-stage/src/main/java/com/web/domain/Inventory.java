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
* 库存信息
* </p>
*
* @author admin admin@126.com
*
*/
@Data
@TableName("inventory")
@ApiModel(value = "Inventory对象", description = "库存信息")
public class Inventory implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("药品ID")
    private Integer drugId;
    @ApiModelProperty("批次号")
    private String batchNumber;
    @ApiModelProperty("入库时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date storageTime;
    @ApiModelProperty("有效期至")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date expiryDate;
    @ApiModelProperty("当前数量")
    private Integer quantity;
    @ApiModelProperty("采购记录ID")
    private Integer purchaseId;

}
