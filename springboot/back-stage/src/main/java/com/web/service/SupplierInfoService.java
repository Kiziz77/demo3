package com.web.service;

import com.web.domain.Req.SupplierInfoReq;
import com.web.domain.Resp.SupplierInfoResp;
import com.web.utils.PageUtils;
import com.web.domain.SupplierInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
* 供应商信息 服务类
* </p>
*
* @author admin admin@126.com
*
*/
public interface SupplierInfoService extends IService<SupplierInfo> {

    PageUtils selectPage(SupplierInfoReq req);

    SupplierInfoResp info(Long id);
}
