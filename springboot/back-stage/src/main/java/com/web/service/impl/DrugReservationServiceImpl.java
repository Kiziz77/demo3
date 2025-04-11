package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.DrugReservation;
import com.web.domain.Req.DrugReservationReq;
import com.web.domain.Resp.DrugReservationResp;
import com.web.mapper.DrugReservationMapper;
import com.web.service.DrugReservationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;

/**
 * <p>
 * 药品预约 服务实现类
 * </p>0
 *
 */
@Service
public class DrugReservationServiceImpl extends ServiceImpl<DrugReservationMapper, DrugReservation> implements DrugReservationService {

    @Resource
    private DrugReservationMapper drugReservationMapper;


    @Override
    public PageUtils selectPage(DrugReservationReq req) {
        Page<DrugReservationResp> page = new Query<DrugReservationResp>(req.getPageInfo()).getPage();
        page.setRecords(drugReservationMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    @Override
    public DrugReservationResp info(Long id) {
        return BeanUtil.copyProperties(drugReservationMapper.selectById(id), DrugReservationResp.class);
    }
}
