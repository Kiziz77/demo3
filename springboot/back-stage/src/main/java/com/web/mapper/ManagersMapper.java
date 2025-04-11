package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.Managers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.ManagersReq;
import com.web.domain.Resp.ManagersResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理员 Mapper 接口
 * </p>
 *
 * 
 * 
 */
@Mapper
public interface ManagersMapper extends BaseMapper<Managers> {

    List<ManagersResp> selectRespList(Page page,@Param("req") ManagersReq req);
}
