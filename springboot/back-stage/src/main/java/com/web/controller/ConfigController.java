package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.ConfigReq;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.web.service.ConfigService;
import com.web.domain.Config;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 配置文件 前端控制器
 * </p>
 *
 * 
 * 
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    /**
     * 后台分页查询
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestBody ConfigReq req){
        PageUtils page = configService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 新增
     * @param config
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Config config ) {
        configService.save(config);
        return Result.success();
    }

    /**
      * 修改
      * @param config
      * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody Config config) {
        configService.updateById(config);
        return Result.success();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(configService.info(id));
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(configService.removeById(id));
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(configService.removeBatchByIds(ids));
    }

    /**
     * 分页查询
     * @param req
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody ConfigReq req){
        PageUtils page = configService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 加载所有
     * @return
     */
    @IgnoreAuth
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(configService.list());
    }




}

