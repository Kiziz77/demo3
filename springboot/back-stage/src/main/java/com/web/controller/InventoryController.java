package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.InventoryReq;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

import com.web.service.InventoryService;
import com.web.domain.Inventory;

import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
*
* 库存信息
* @author admin admin@126.com
*/
@RestController
@RequestMapping("/inventory")
public class InventoryController {

@Resource
private InventoryService inventoryService;

    /**
    * 后台分页查询
    * @return
    */
    @PostMapping("/page")
    public Result page(@RequestBody InventoryReq req){
        PageUtils page = inventoryService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 新增
    * @param inventory
    * @return
    */
    @PostMapping("/save")
    public Result save(@RequestBody Inventory inventory ) {
        inventoryService.save(inventory);
        return Result.success();
    }

    /**
    * 修改
    * @param inventory
    * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody Inventory inventory) {
        inventoryService.updateById(inventory);
        return Result.success();
    }

    /**
    * 根据id查询
    * @param id
    * @return
    */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(inventoryService.info(id));
    }

    /**
    * 根据id删除
    * @param id
    * @return
    */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Long id){
        return Result.success(inventoryService.removeById(id));
    }


    /**
    * 批量删除
    * @param ids
    * @return
    */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Long[] ids){
        return Result.success(inventoryService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
    * 分页查询
    * @param req
    * @return
    */
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestBody InventoryReq req){
        PageUtils page = inventoryService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 加载所有
    * @return
    */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(inventoryService.list());
    }




}

