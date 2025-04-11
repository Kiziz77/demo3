package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.Config;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.ConfigReq;
import com.web.domain.Resp.ConfigResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 配置文件 Mapper 接口
 * </p>
 *
 * 
 * 
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {

    List<ConfigResp> selectRespList(Page page, ConfigReq req);
}
