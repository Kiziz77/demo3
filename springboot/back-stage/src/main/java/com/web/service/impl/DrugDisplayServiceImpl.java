package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.DrugDisplay;
import com.web.domain.Req.DrugDisplayReq;
import com.web.domain.Resp.DrugDisplayResp;
import com.web.mapper.DrugDisplayMapper;
import com.web.service.DrugDisplayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * <p>
 * 药品展示 服务实现类
 * </p>0
 *
 */
@Service
public class DrugDisplayServiceImpl extends ServiceImpl<DrugDisplayMapper, DrugDisplay> implements DrugDisplayService {

    @Resource
    private DrugDisplayMapper drugDisplayMapper;


    @Override
    public PageUtils selectPage(DrugDisplayReq req) {
        Page<DrugDisplayResp> page = new Query<DrugDisplayResp>(req.getPageInfo()).getPage();
        page.setRecords(drugDisplayMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    @Override
    public DrugDisplayResp info(Long id) {
        return BeanUtil.copyProperties(drugDisplayMapper.selectById(id), DrugDisplayResp.class);
    }
}
