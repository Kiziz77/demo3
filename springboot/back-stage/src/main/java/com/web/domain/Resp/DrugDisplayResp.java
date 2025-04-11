package com.web.domain.Resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* <p>
* 药品展示返回对象
* </p>
*
* @author admin admin@126.com
*
*/
@Data
public class DrugDisplayResp implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("药品ID")
    private Integer drugId;

            private String t2Drugname;
    @ApiModelProperty("药品图片")
    private String drugImage;

    @ApiModelProperty("展示状态")
    private String displayStatus;

    @ApiModelProperty("单价")
    private Integer unitPrice;

            private String t3Unitprice;

}
