package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.SupplierInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.SupplierInfoReq;
import com.web.domain.Resp.SupplierInfoResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 供应商信息 Mapper 接口
 * </p>
 */
@Mapper
public interface SupplierInfoMapper extends BaseMapper<SupplierInfo> {

    List<SupplierInfoResp> selectRespList(Page page, @Param("req") SupplierInfoReq req);
}
