package com.web.domain.Resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 采购记录返回对象
* </p>
*
* @author admin admin@126.com
*
*/
@Data
public class PurchaseRecordResp implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("供应商ID")
    private Integer supplierId;

            private String t2Suppliername;
    @ApiModelProperty("药品ID")
    private Integer drugId;

            private String t3Drugname;
    @ApiModelProperty("采购数量")
    private Integer purchaseQuantity;

    @ApiModelProperty("采购时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date purchaseTime;

    @ApiModelProperty("采购记录ID")
    private Integer purchaseId;

    @ApiModelProperty("采购单价")
    private Integer purchaseUnitPrice;


}
