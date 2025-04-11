package com.web.domain.Req;

import io.swagger.annotations.ApiModelProperty;
import com.web.utils.PageInfo;
import lombok.Data;

/**
* 供应商信息
*/
@Data
public class SupplierInfoReq extends PageInfo {

    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("联系人")
    private String contactPerson;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("地址")
    private String address;

    public PageInfo getPageInfo() {
        return this;
    }

}
