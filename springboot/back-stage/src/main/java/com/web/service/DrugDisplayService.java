package com.web.service;

import com.web.domain.Req.DrugDisplayReq;
import com.web.domain.Resp.DrugDisplayResp;
import com.web.utils.PageUtils;
import com.web.domain.DrugDisplay;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
* 药品展示 服务类
* </p>
*
* @author admin admin@126.com
*
*/
public interface DrugDisplayService extends IService<DrugDisplay> {

    PageUtils selectPage(DrugDisplayReq req);

    DrugDisplayResp info(Long id);
}
