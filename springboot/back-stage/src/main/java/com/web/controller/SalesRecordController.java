package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.SalesRecordReq;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

import com.web.service.SalesRecordService;
import com.web.domain.SalesRecord;

import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
*
* 销售记录
* @author admin admin@126.com
*/
@RestController
@RequestMapping("/salesRecord")
public class SalesRecordController {

@Resource
private SalesRecordService salesRecordService;

    /**
    * 后台分页查询
    * @return
    */
    @PostMapping("/page")
    public Result page(@RequestBody SalesRecordReq req){
        PageUtils page = salesRecordService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 新增
    * @param salesRecord
    * @return
    */
    @PostMapping("/save")
    public Result save(@RequestBody SalesRecord salesRecord ) {
        salesRecordService.save(salesRecord);
        return Result.success();
    }

    /**
    * 修改
    * @param salesRecord
    * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody SalesRecord salesRecord) {
        salesRecordService.updateById(salesRecord);
        return Result.success();
    }

    /**
    * 根据id查询
    * @param id
    * @return
    */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(salesRecordService.info(id));
    }

    /**
    * 根据id删除
    * @param id
    * @return
    */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Long id){
        return Result.success(salesRecordService.removeById(id));
    }


    /**
    * 批量删除
    * @param ids
    * @return
    */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Long[] ids){
        return Result.success(salesRecordService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
    * 分页查询
    * @param req
    * @return
    */
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestBody SalesRecordReq req){
        PageUtils page = salesRecordService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 加载所有
    * @return
    */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(salesRecordService.list());
    }




}

