package com.web.service;

import com.web.domain.Req.InventoryReq;
import com.web.domain.Resp.InventoryResp;
import com.web.utils.PageUtils;
import com.web.domain.Inventory;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Date;

/**
* <p>
* 库存信息 服务类
* </p>
*
* @author admin admin@126.com
*
*/
public interface InventoryService extends IService<Inventory> {

    PageUtils selectPage(InventoryReq req);

    InventoryResp info(Long id);
}
