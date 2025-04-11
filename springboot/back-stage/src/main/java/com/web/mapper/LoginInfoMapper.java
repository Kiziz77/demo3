package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.LoginInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.LoginInfoReq;
import com.web.domain.Resp.LoginInfoResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录日志 Mapper 接口
 * </p>
 *
 * 
 * 
 */
@Mapper
public interface LoginInfoMapper extends BaseMapper<LoginInfo> {

    List<LoginInfoResp> selectRespList(Page page,@Param("req") LoginInfoReq req);

    List<Map<String, Object>> getData1();
}
