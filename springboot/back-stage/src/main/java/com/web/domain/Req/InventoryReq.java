package com.web.domain.Req;

import io.swagger.annotations.ApiModelProperty;
import com.web.utils.PageInfo;
import lombok.Data;
import java.util.Date;

/**
* 库存信息
*/
@Data
public class InventoryReq extends PageInfo {

    @ApiModelProperty("药品ID")
    private Integer drugId;
    @ApiModelProperty("批次号")
    private String batchNumber;
    @ApiModelProperty("当前数量")
    private Integer quantity;
    @ApiModelProperty("采购记录ID")
    private Integer purchaseId;

    public PageInfo getPageInfo() {
        return this;
    }

}
