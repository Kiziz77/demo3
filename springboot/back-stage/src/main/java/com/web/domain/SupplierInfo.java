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

/**
* <p>
* 供应商信息
* </p>
*
* @author admin admin@126.com
*
*/
@Data
@TableName("supplier_info")
@ApiModel(value = "SupplierInfo对象", description = "供应商信息")
public class SupplierInfo implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("联系人")
    private String contactPerson;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("地址")
    private String address;

}
