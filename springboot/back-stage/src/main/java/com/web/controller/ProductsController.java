package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Products;
import com.web.domain.Req.ProductsReq;
import com.web.service.LikeCollectService;
import com.web.service.ProductOrderService;
import com.web.service.ProductsService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Resource
    private ProductsService productsService;
    @Resource
    private LikeCollectService likeCollectService;
    @Resource
    private ProductOrderService productOrderService;
    
    /**
     * 后台分页查询
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestBody ProductsReq req){
        PageUtils page = productsService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 新增
     * @param products
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Products products ) {
        productsService.save(products);
        return Result.success();
    }

    /**
     * 修改
     * @param products
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Products products) {
        productsService.updateById(products);
        return Result.success();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(productsService.info(id));
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Long id){
        // 同步删除收藏点赞表内容
        likeCollectService.deleteByIdTableName(id,"products");
        // 删除购物车信息
        productOrderService.deleteByIdTableName(id,"products");
        return Result.success(productsService.removeById(id));
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Long[] ids){
        // 同步删除收藏点赞表内容
        for (Long id : ids) {
            likeCollectService.deleteByIdTableName(id,"products");
            productOrderService.deleteByIdTableName(id,"products");
        }
        return Result.success(productsService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
     * 分页查询
     * @param req
     * @return
     */
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestBody ProductsReq req){
        PageUtils page = productsService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 加载所有
     * @return
     */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(productsService.list());
    }



}
