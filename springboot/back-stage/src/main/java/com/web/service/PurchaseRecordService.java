package com.web.service;

import com.web.domain.Req.PurchaseRecordReq;
import com.web.domain.Resp.PurchaseRecordResp;
import com.web.utils.PageUtils;
import com.web.domain.PurchaseRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Date;

/**
* <p>
* 采购记录 服务类
* </p>
*
* @author admin admin@126.com
*
*/
public interface PurchaseRecordService extends IService<PurchaseRecord> {

    PageUtils selectPage(PurchaseRecordReq req);

    PurchaseRecordResp info(Long id);
}
