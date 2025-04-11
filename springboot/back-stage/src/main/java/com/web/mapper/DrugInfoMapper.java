package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.DrugInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.DrugInfoReq;
import com.web.domain.Resp.DrugInfoResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 药品信息 Mapper 接口
 * </p>
 */
@Mapper
public interface DrugInfoMapper extends BaseMapper<DrugInfo> {

    List<DrugInfoResp> selectRespList(Page page, @Param("req") DrugInfoReq req);
}
