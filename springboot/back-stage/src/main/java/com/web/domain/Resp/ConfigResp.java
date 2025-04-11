package com.web.domain.Resp;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 配置文件
 * </p>
 *
 * 
 * 
 */
@Data
@ApiModel(value = "Config对象", description = "配置文件")
public class ConfigResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("参数")
    private String value;


}
