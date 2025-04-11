package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.SalesRecord;
import com.web.domain.Req.SalesRecordReq;
import com.web.domain.Resp.SalesRecordResp;
import com.web.mapper.SalesRecordMapper;
import com.web.service.SalesRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;

/**
 * <p>
 * 销售记录 服务实现类
 * </p>0
 *
 */
@Service
public class SalesRecordServiceImpl extends ServiceImpl<SalesRecordMapper, SalesRecord> implements SalesRecordService {

    @Resource
    private SalesRecordMapper salesRecordMapper;


    @Override
    public PageUtils selectPage(SalesRecordReq req) {
        Page<SalesRecordResp> page = new Query<SalesRecordResp>(req.getPageInfo()).getPage();
        page.setRecords(salesRecordMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    @Override
    public SalesRecordResp info(Long id) {
        return BeanUtil.copyProperties(salesRecordMapper.selectById(id), SalesRecordResp.class);
    }
}
