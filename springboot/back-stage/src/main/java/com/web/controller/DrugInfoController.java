package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.DrugInfoReq;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

import com.web.service.DrugInfoService;
import com.web.domain.DrugInfo;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 药品信息
* @author admin admin@126.com
*/
@RestController
@RequestMapping("/drugInfo")
public class DrugInfoController {

@Resource
private DrugInfoService drugInfoService;

    /**
    * 后台分页查询
    * @return
    */
    @PostMapping("/page")
    public Result page(@RequestBody DrugInfoReq req){
        PageUtils page = drugInfoService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 新增
    * @param drugInfo
    * @return
    */
    @PostMapping("/save")
    public Result save(@RequestBody DrugInfo drugInfo ) {
        drugInfoService.save(drugInfo);
        return Result.success();
    }

    /**
    * 修改
    * @param drugInfo
    * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody DrugInfo drugInfo) {
        drugInfoService.updateById(drugInfo);
        return Result.success();
    }

    /**
    * 根据id查询
    * @param id
    * @return
    */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(drugInfoService.info(id));
    }

    /**
    * 根据id删除
    * @param id
    * @return
    */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Long id){
        return Result.success(drugInfoService.removeById(id));
    }


    /**
    * 批量删除
    * @param ids
    * @return
    */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Long[] ids){
        return Result.success(drugInfoService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
    * 分页查询
    * @param req
    * @return
    */
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestBody DrugInfoReq req){
        PageUtils page = drugInfoService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 加载所有
    * @return
    */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(drugInfoService.list());
    }




}

