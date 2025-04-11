package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.DrugInfo;
import com.web.domain.Req.DrugInfoReq;
import com.web.domain.Resp.DrugInfoResp;
import com.web.mapper.DrugInfoMapper;
import com.web.service.DrugInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * <p>
 * 药品信息 服务实现类
 * </p>0
 *
 */
@Service
public class DrugInfoServiceImpl extends ServiceImpl<DrugInfoMapper, DrugInfo> implements DrugInfoService {

    @Resource
    private DrugInfoMapper drugInfoMapper;


    @Override
    public PageUtils selectPage(DrugInfoReq req) {
        Page<DrugInfoResp> page = new Query<DrugInfoResp>(req.getPageInfo()).getPage();
        page.setRecords(drugInfoMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    @Override
    public DrugInfoResp info(Long id) {
        return BeanUtil.copyProperties(drugInfoMapper.selectById(id), DrugInfoResp.class);
    }
}
