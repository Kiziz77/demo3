package com.web.service;

import com.web.domain.Req.DrugInfoReq;
import com.web.domain.Resp.DrugInfoResp;
import com.web.utils.PageUtils;
import com.web.domain.DrugInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
* 药品信息 服务类
* </p>
*
* @author admin admin@126.com
*
*/
public interface DrugInfoService extends IService<DrugInfo> {

    PageUtils selectPage(DrugInfoReq req);

    DrugInfoResp info(Long id);
}
