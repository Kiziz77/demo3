package com.web.domain.Req;

import io.swagger.annotations.ApiModelProperty;
import com.web.utils.PageInfo;
import lombok.Data;
import java.util.Date;

/**
* 采购记录
*/
@Data
public class PurchaseRecordReq extends PageInfo {

    @ApiModelProperty("供应商ID")
    private Integer supplierId;
    @ApiModelProperty("药品ID")
    private Integer drugId;
    @ApiModelProperty("采购数量")
    private Integer purchaseQuantity;
    @ApiModelProperty("采购记录ID")
    private Integer purchaseId;
    @ApiModelProperty("采购单价")
    private Integer purchaseUnitPrice;

    public PageInfo getPageInfo() {
        return this;
    }

}
