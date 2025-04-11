package com.web.domain.Resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 库存信息返回对象
* </p>
*
* @author admin admin@126.com
*
*/
@Data
public class InventoryResp implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("药品ID")
    private Integer drugId;

            private String t2Drugname;
    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty("入库时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date storageTime;

    @ApiModelProperty("有效期至")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date expiryDate;

    @ApiModelProperty("当前数量")
    private Integer quantity;

    @ApiModelProperty("采购记录ID")
    private Integer purchaseId;

            private String t3Purchaseid;

}
