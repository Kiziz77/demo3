package com.web.controller;

import com.web.domain.Req.LoginInfoReq;
import com.web.domain.Users;
import com.web.service.UsersService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.service.LoginInfoService;
import com.web.domain.LoginInfo;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 登录日志 前端控制器
 * </p>
 *
 * 
 * 
 */
@RestController
@RequestMapping("/login-info")
public class LoginInfoController {

    @Resource
    private LoginInfoService loginInfoService;
    @Resource
    private UsersService usersService;



    /**
     * 后台分页查询
     *
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestBody LoginInfoReq req) {
        PageUtils page = loginInfoService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 新增
     *
     * @param loginInfo
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody LoginInfo loginInfo) {
        loginInfoService.save(loginInfo);
        return Result.success();
    }

    /**
     * 修改
     *
     * @param loginInfo
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody LoginInfo loginInfo) {
        loginInfoService.updateById(loginInfo);
        return Result.success();
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(loginInfoService.info(id));
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(loginInfoService.removeById(id));
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(loginInfoService.removeBatchByIds(ids));
    }

    /**
     * 分页查询
     *
     * @param req
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody LoginInfoReq req) {
        PageUtils page = loginInfoService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 加载所有
     *
     * @return
     */
    @GetMapping("/loadAll")
    public Result loadAll() {
        return Result.success(loginInfoService.list());
    }


    @RequestMapping(value = "/getData1")
    public Result getData1() {
        List<Map<String, Object>> list = loginInfoService.getData1();
        List key = new ArrayList();
        List val = new ArrayList();
        Map returnMap = new HashMap<String, List>();
        for (Map<String, Object> m : list) {
            key.add(m.get("name"));
            val.add(m.get("value"));
        }
        // 获取用户统计数据
        long count = usersService.count();

        returnMap.put("userCount", count);
        returnMap.put("key", key);
        returnMap.put("val", val);
        return Result.success(returnMap);
    }

}

