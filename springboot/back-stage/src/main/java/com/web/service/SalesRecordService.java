package com.web.service;

import com.web.domain.Req.SalesRecordReq;
import com.web.domain.Resp.SalesRecordResp;
import com.web.utils.PageUtils;
import com.web.domain.SalesRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Date;

/**
* <p>
* 销售记录 服务类
* </p>
*
* @author admin admin@126.com
*
*/
public interface SalesRecordService extends IService<SalesRecord> {

    PageUtils selectPage(SalesRecordReq req);

    SalesRecordResp info(Long id);
}
