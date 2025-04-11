package com.web.domain.Req;

import io.swagger.annotations.ApiModelProperty;
import com.web.utils.PageInfo;
import lombok.Data;

/**
* 药品信息
*/
@Data
public class DrugInfoReq extends PageInfo {

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

    public PageInfo getPageInfo() {
        return this;
    }

}
