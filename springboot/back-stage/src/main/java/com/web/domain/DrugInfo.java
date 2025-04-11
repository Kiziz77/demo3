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
* 药品信息
* </p>
*
* @author admin admin@126.com
*
*/
@Data
@TableName("drug_info")
@ApiModel(value = "DrugInfo对象", description = "药品信息")
public class DrugInfo implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("药品名称")
    private String drugName;
    @ApiModelProperty("规格")
    private String specification;
    @ApiModelProperty("生产商")
    private String manufacturer;
    @ApiModelProperty("批准文号")
    private String approvalNumber;
    @ApiModelProperty("单价")
    private Integer unitPrice;

}
