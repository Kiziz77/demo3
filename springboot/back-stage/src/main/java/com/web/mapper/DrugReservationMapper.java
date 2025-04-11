package com.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.DrugReservation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.domain.Req.DrugReservationReq;
import com.web.domain.Resp.DrugReservationResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Date;

/**
 * <p>
 * 药品预约 Mapper 接口
 * </p>
 */
@Mapper
public interface DrugReservationMapper extends BaseMapper<DrugReservation> {

    List<DrugReservationResp> selectRespList(Page page, @Param("req") DrugReservationReq req);
}
