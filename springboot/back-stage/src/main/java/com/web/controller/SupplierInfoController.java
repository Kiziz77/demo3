package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.SupplierInfoReq;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

import com.web.service.SupplierInfoService;
import com.web.domain.SupplierInfo;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 供应商信息
* @author admin admin@126.com
*/
@RestController
@RequestMapping("/supplierInfo")
public class SupplierInfoController {

@Resource
private SupplierInfoService supplierInfoService;

    /**
    * 后台分页查询
    * @return
    */
    @PostMapping("/page")
    public Result page(@RequestBody SupplierInfoReq req){
        PageUtils page = supplierInfoService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 新增
    * @param supplierInfo
    * @return
    */
    @PostMapping("/save")
    public Result save(@RequestBody SupplierInfo supplierInfo ) {
        supplierInfoService.save(supplierInfo);
        return Result.success();
    }

    /**
    * 修改
    * @param supplierInfo
    * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody SupplierInfo supplierInfo) {
        supplierInfoService.updateById(supplierInfo);
        return Result.success();
    }

    /**
    * 根据id查询
    * @param id
    * @return
    */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(supplierInfoService.info(id));
    }

    /**
    * 根据id删除
    * @param id
    * @return
    */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Long id){
        return Result.success(supplierInfoService.removeById(id));
    }


    /**
    * 批量删除
    * @param ids
    * @return
    */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Long[] ids){
        return Result.success(supplierInfoService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
    * 分页查询
    * @param req
    * @return
    */
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestBody SupplierInfoReq req){
        PageUtils page = supplierInfoService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 加载所有
    * @return
    */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(supplierInfoService.list());
    }




}

