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
* 药品展示
* </p>
*
* @author admin admin@126.com
*
*/
@Data
@TableName("drug_display")
@ApiModel(value = "DrugDisplay对象", description = "药品展示")
public class DrugDisplay implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("药品ID")
    private Integer drugId;
    @ApiModelProperty("药品图片")
    private String drugImage;
    @ApiModelProperty("展示状态")
    private String displayStatus;
    @ApiModelProperty("单价")
    private Integer unitPrice;

}
