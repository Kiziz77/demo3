package com.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.domain.Products;
import com.web.domain.Req.ProductsReq;
import com.web.domain.Resp.ProductsResp;
import com.web.mapper.ProductsMapper;
import com.web.service.ProductsService;
import com.web.utils.PageUtils;
import com.web.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @description 商品Service实现
*/
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements ProductsService {
    @Resource
    private ProductsMapper productsMapper;


    @Override
    public PageUtils selectPage(ProductsReq req) {
        Page<ProductsResp> page = new Query<ProductsResp>(req.getPageInfo()).getPage();
        page.setRecords(productsMapper.selectRespList(page, req));
        return new PageUtils(page);
    }

    @Override
    public ProductsResp info(Long id) {
        return BeanUtil.copyProperties(productsMapper.selectById(id), ProductsResp.class);
    }
}




