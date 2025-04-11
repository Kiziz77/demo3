package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.SalesRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.SalesRecordReq;
import com.web.domain.Resp.SalesRecordResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Date;

/**
 * <p>
 * 销售记录 Mapper 接口
 * </p>
 */
@Mapper
public interface SalesRecordMapper extends BaseMapper<SalesRecord> {

    List<SalesRecordResp> selectRespList(Page page, @Param("req") SalesRecordReq req);
}
