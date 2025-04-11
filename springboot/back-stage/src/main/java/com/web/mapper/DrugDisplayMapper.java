package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.DrugDisplay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.DrugDisplayReq;
import com.web.domain.Resp.DrugDisplayResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 药品展示 Mapper 接口
 * </p>
 */
@Mapper
public interface DrugDisplayMapper extends BaseMapper<DrugDisplay> {

    List<DrugDisplayResp> selectRespList(Page page, @Param("req") DrugDisplayReq req);
}
