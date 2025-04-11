package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.PurchaseRecordReq;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

import com.web.service.PurchaseRecordService;
import com.web.domain.PurchaseRecord;

import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
*
* 采购记录
* @author admin admin@126.com
*/
@RestController
@RequestMapping("/purchaseRecord")
public class PurchaseRecordController {

@Resource
private PurchaseRecordService purchaseRecordService;

    /**
    * 后台分页查询
    * @return
    */
    @PostMapping("/page")
    public Result page(@RequestBody PurchaseRecordReq req){
        PageUtils page = purchaseRecordService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 新增
    * @param purchaseRecord
    * @return
    */
    @PostMapping("/save")
    public Result save(@RequestBody PurchaseRecord purchaseRecord ) {
        purchaseRecordService.save(purchaseRecord);
        return Result.success();
    }

    /**
    * 修改
    * @param purchaseRecord
    * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody PurchaseRecord purchaseRecord) {
        purchaseRecordService.updateById(purchaseRecord);
        return Result.success();
    }

    /**
    * 根据id查询
    * @param id
    * @return
    */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(purchaseRecordService.info(id));
    }

    /**
    * 根据id删除
    * @param id
    * @return
    */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Long id){
        return Result.success(purchaseRecordService.removeById(id));
    }


    /**
    * 批量删除
    * @param ids
    * @return
    */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Long[] ids){
        return Result.success(purchaseRecordService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
    * 分页查询
    * @param req
    * @return
    */
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestBody PurchaseRecordReq req){
        PageUtils page = purchaseRecordService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 加载所有
    * @return
    */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(purchaseRecordService.list());
    }




}

