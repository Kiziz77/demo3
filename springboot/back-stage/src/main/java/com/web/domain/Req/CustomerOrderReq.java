package com.web.domain.Req;

import io.swagger.annotations.ApiModelProperty;
import com.web.utils.PageInfo;
import lombok.Data;
import java.util.Date;

/**
* 购买订单
*/
@Data
public class CustomerOrderReq extends PageInfo {

    @ApiModelProperty("客户姓名")
    private String customerName;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("药品ID")
    private Integer drugId;
    @ApiModelProperty("购买数量")
    private Integer quantity;

    public PageInfo getPageInfo() {
        return this;
    }

}
