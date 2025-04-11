package com.web.service;

import com.web.domain.Req.CustomerOrderReq;
import com.web.domain.Resp.CustomerOrderResp;
import com.web.utils.PageUtils;
import com.web.domain.CustomerOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Date;

/**
* <p>
* 购买订单 服务类
* </p>
*
* @author admin admin@126.com
*
*/
public interface CustomerOrderService extends IService<CustomerOrder> {

    PageUtils selectPage(CustomerOrderReq req);

    CustomerOrderResp info(Long id);
}
