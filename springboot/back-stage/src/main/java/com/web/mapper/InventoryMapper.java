package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.Inventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.InventoryReq;
import com.web.domain.Resp.InventoryResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Date;

/**
 * <p>
 * 库存信息 Mapper 接口
 * </p>
 */
@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {

    List<InventoryResp> selectRespList(Page page, @Param("req") InventoryReq req);
}
