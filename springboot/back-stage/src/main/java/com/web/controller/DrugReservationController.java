package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.DrugReservationReq;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

import com.web.service.DrugReservationService;
import com.web.domain.DrugReservation;

import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
*
* 药品预约
* @author admin admin@126.com
*/
@RestController
@RequestMapping("/drugReservation")
public class DrugReservationController {

@Resource
private DrugReservationService drugReservationService;

    /**
    * 后台分页查询
    * @return
    */
    @PostMapping("/page")
    public Result page(@RequestBody DrugReservationReq req){
        PageUtils page = drugReservationService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 新增
    * @param drugReservation
    * @return
    */
    @PostMapping("/save")
    public Result save(@RequestBody DrugReservation drugReservation ) {
        drugReservationService.save(drugReservation);
        return Result.success();
    }

    /**
    * 修改
    * @param drugReservation
    * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody DrugReservation drugReservation) {
        drugReservationService.updateById(drugReservation);
        return Result.success();
    }

    /**
    * 根据id查询
    * @param id
    * @return
    */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(drugReservationService.info(id));
    }

    /**
    * 根据id删除
    * @param id
    * @return
    */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Long id){
        return Result.success(drugReservationService.removeById(id));
    }


    /**
    * 批量删除
    * @param ids
    * @return
    */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Long[] ids){
        return Result.success(drugReservationService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
    * 分页查询
    * @param req
    * @return
    */
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestBody DrugReservationReq req){
        PageUtils page = drugReservationService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 加载所有
    * @return
    */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(drugReservationService.list());
    }




}

