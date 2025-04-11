package com.web.service;

import com.web.domain.Req.ConfigReq;
import com.web.domain.Resp.ConfigResp;
import com.web.utils.PageUtils;
import com.web.domain.Config;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 配置文件 服务类
 * </p>
 *
 * 
 * 
 */
public interface ConfigService extends IService<Config> {

    PageUtils selectPage(ConfigReq req);

    ConfigResp info(Integer id);
}
