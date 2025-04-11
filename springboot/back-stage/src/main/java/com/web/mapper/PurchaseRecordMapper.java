package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.PurchaseRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.PurchaseRecordReq;
import com.web.domain.Resp.PurchaseRecordResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Date;

/**
 * <p>
 * 采购记录 Mapper 接口
 * </p>
 */
@Mapper
public interface PurchaseRecordMapper extends BaseMapper<PurchaseRecord> {

    List<PurchaseRecordResp> selectRespList(Page page, @Param("req") PurchaseRecordReq req);
}
