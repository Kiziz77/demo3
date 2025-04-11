package com.web.domain.Resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 药品信息返回对象
* </p>
*
* @author admin admin@126.com
*
*/
@Data
public class DrugInfoResp implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
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
