package com.web.service;

import com.web.domain.Req.UsersReq;
import com.web.domain.Resp.UsersResp;
import com.web.utils.PageUtils;
import com.web.domain.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * 
 * 
 */
public interface UsersService extends IService<Users> {

    PageUtils selectPage(UsersReq req);

    UsersResp info(Integer id);
}
