package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.domain.Inventory;
import com.web.domain.Req.InventoryReq;
import com.web.domain.Resp.InventoryResp;
import com.web.mapper.InventoryMapper;
import com.web.service.InventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;

/**
 * <p>
 * 库存信息 服务实现类
 * </p>0
 *
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Resource
    private InventoryMapper inventoryMapper;


    @Override
    public PageUtils selectPage(InventoryReq req) {
        Page<InventoryResp> page = new Query<InventoryResp>(req.getPageInfo()).getPage();
        page.setRecords(inventoryMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    @Override
    public InventoryResp info(Long id) {
        return BeanUtil.copyProperties(inventoryMapper.selectById(id), InventoryResp.class);
    }
}
