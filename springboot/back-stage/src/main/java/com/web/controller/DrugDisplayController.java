package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.DrugDisplayReq;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

import com.web.service.DrugDisplayService;
import com.web.domain.DrugDisplay;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 药品展示
* @author admin admin@126.com
*/
@RestController
@RequestMapping("/drugDisplay")
public class DrugDisplayController {

@Resource
private DrugDisplayService drugDisplayService;

    /**
    * 后台分页查询
    * @return
    */
    @PostMapping("/page")
    public Result page(@RequestBody DrugDisplayReq req){
        PageUtils page = drugDisplayService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 新增
    * @param drugDisplay
    * @return
    */
    @PostMapping("/save")
    public Result save(@RequestBody DrugDisplay drugDisplay ) {
        drugDisplayService.save(drugDisplay);
        return Result.success();
    }

    /**
    * 修改
    * @param drugDisplay
    * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody DrugDisplay drugDisplay) {
        drugDisplayService.updateById(drugDisplay);
        return Result.success();
    }

    /**
    * 根据id查询
    * @param id
    * @return
    */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(drugDisplayService.info(id));
    }

    /**
    * 根据id删除
    * @param id
    * @return
    */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Long id){
        return Result.success(drugDisplayService.removeById(id));
    }


    /**
    * 批量删除
    * @param ids
    * @return
    */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Long[] ids){
        return Result.success(drugDisplayService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
    * 分页查询
    * @param req
    * @return
    */
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestBody DrugDisplayReq req){
        PageUtils page = drugDisplayService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 加载所有
    * @return
    */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(drugDisplayService.list());
    }




}

