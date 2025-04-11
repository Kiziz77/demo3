package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.PurchaseRecord;
import com.web.domain.Req.PurchaseRecordReq;
import com.web.domain.Resp.PurchaseRecordResp;
import com.web.mapper.PurchaseRecordMapper;
import com.web.service.PurchaseRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;

/**
 * <p>
 * 采购记录 服务实现类
 * </p>0
 *
 */
@Service
public class PurchaseRecordServiceImpl extends ServiceImpl<PurchaseRecordMapper, PurchaseRecord> implements PurchaseRecordService {

    @Resource
    private PurchaseRecordMapper purchaseRecordMapper;


    @Override
    public PageUtils selectPage(PurchaseRecordReq req) {
        Page<PurchaseRecordResp> page = new Query<PurchaseRecordResp>(req.getPageInfo()).getPage();
        page.setRecords(purchaseRecordMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    @Override
    public PurchaseRecordResp info(Long id) {
        return BeanUtil.copyProperties(purchaseRecordMapper.selectById(id), PurchaseRecordResp.class);
    }
}
