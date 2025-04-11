package com.web.domain.Req;

import io.swagger.annotations.ApiModelProperty;
import com.web.utils.PageInfo;
import lombok.Data;

/**
* 药品展示
*/
@Data
public class DrugDisplayReq extends PageInfo {

    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("药品ID")
    private Integer drugId;
    @ApiModelProperty("药品图片")
    private String drugImage;
    @ApiModelProperty("展示状态")
    private String displayStatus;
    @ApiModelProperty("单价")
    private Integer unitPrice;

    public PageInfo getPageInfo() {
        return this;
    }

}
