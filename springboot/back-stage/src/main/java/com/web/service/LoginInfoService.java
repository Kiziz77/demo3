package com.web.service;

import com.web.domain.Req.LoginInfoReq;
import com.web.domain.Resp.LoginInfoResp;
import com.web.utils.PageUtils;
import com.web.domain.LoginInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * 
 * 
 */
public interface LoginInfoService extends IService<LoginInfo> {

    PageUtils selectPage(LoginInfoReq req);

    LoginInfoResp info(Integer id);

    void saveLoginInfo(String userName, String userIp, String userAgent, String referer, String sessionId);

    List<Map<String, Object>> getData1();
}
