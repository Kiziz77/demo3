package com.web.domain.Resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 销售记录返回对象
* </p>
*
* @author admin admin@126.com
*
*/
@Data
public class SalesRecordResp implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("库存ID")
    private Integer inventoryId;

            private String t2Drugid;
    @ApiModelProperty("销售数量")
    private Integer salesQuantity;

    @ApiModelProperty("销售时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date salesTime;

    @ApiModelProperty("客户名称")
    private String customerName;


}
