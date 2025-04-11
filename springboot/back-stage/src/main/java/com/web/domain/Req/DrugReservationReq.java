package com.web.domain.Req;

import io.swagger.annotations.ApiModelProperty;
import com.web.utils.PageInfo;
import lombok.Data;
import java.util.Date;

/**
* 药品预约
*/
@Data
public class DrugReservationReq extends PageInfo {

    @ApiModelProperty("客户姓名")
    private String customerName;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("药品ID")
    private Integer drugId;
    @ApiModelProperty("预约数量")
    private Integer reserveQuantity;

    public PageInfo getPageInfo() {
        return this;
    }

}
