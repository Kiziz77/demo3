package com.web.domain.Resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 药品预约返回对象
* </p>
*
* @author admin admin@126.com
*
*/
@Data
public class DrugReservationResp implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("客户姓名")
    private String customerName;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("药品ID")
    private Integer drugId;

    @ApiModelProperty("预约数量")
    private Integer reserveQuantity;

    @ApiModelProperty("预约时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reserveTime;


}
