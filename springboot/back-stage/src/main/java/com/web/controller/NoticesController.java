package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.NoticesReq;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

import com.web.service.NoticesService;
import com.web.domain.Notices;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 公告 前端控制器
 * </p>
 *
 * 
 * 
 */
@RestController
@RequestMapping("/notices")
public class NoticesController {

    @Resource
    private NoticesService noticesService;

    /**
     * 后台分页查询
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestBody NoticesReq req){
        PageUtils page = noticesService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 新增
     * @param notices
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Notices notices ) {
        noticesService.save(notices);
        return Result.success();
    }

    /**
      * 修改
      * @param notices
      * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody Notices notices) {
        noticesService.updateById(notices);
        return Result.success();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(noticesService.info(id));
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(noticesService.removeById(id));
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Integer[] ids){
        return Result.success(noticesService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
     * 分页查询
     * @param req
     * @return
     */
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestBody NoticesReq req){
        PageUtils page = noticesService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 加载所有
     * @return
     */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(noticesService.list());
    }




}

