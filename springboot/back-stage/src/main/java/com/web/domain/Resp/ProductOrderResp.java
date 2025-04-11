package com.web.domain.Resp;

import com.web.domain.ProductOrder;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品订单表
 * @TableName product_order
 */
@Data
public class ProductOrderResp extends ProductOrder implements Serializable {


}