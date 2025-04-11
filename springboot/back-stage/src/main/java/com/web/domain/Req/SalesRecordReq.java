package com.web.domain.Req;

import io.swagger.annotations.ApiModelProperty;
import com.web.utils.PageInfo;
import lombok.Data;
import java.util.Date;

/**
* 销售记录
*/
@Data
public class SalesRecordReq extends PageInfo {

    @ApiModelProperty("库存ID")
    private Integer inventoryId;
    @ApiModelProperty("销售数量")
    private Integer salesQuantity;
    @ApiModelProperty("客户名称")
    private String customerName;

    public PageInfo getPageInfo() {
        return this;
    }

}
