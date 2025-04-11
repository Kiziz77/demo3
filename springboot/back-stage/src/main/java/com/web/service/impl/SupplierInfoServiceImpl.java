package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.SupplierInfo;
import com.web.domain.Req.SupplierInfoReq;
import com.web.domain.Resp.SupplierInfoResp;
import com.web.mapper.SupplierInfoMapper;
import com.web.service.SupplierInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * <p>
 * 供应商信息 服务实现类
 * </p>0
 *
 */
@Service
public class SupplierInfoServiceImpl extends ServiceImpl<SupplierInfoMapper, SupplierInfo> implements SupplierInfoService {

    @Resource
    private SupplierInfoMapper supplierInfoMapper;


    @Override
    public PageUtils selectPage(SupplierInfoReq req) {
        Page<SupplierInfoResp> page = new Query<SupplierInfoResp>(req.getPageInfo()).getPage();
        page.setRecords(supplierInfoMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    @Override
    public SupplierInfoResp info(Long id) {
        return BeanUtil.copyProperties(supplierInfoMapper.selectById(id), SupplierInfoResp.class);
    }
}
