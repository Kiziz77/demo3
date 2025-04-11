package com.web.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.web.annotation.IgnoreAuth;
import com.web.aop.LogAnnotation;
import com.web.domain.Req.ManagersReq;
import com.web.service.TokenService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.web.service.ManagersService;
import com.web.domain.Managers;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/managers")
public class ManagersController {

    @Resource
    private ManagersService managersService;

    @Resource
    private TokenService tokenService;


    @IgnoreAuth
    @LogAnnotation
    @RequestMapping(value = "/login")
    public Result login(String userName, String password, String checkCode, String codeKey) {
        Managers u = managersService.getOne(
                Wrappers.lambdaQuery(Managers.class)
                        .eq(Managers::getAccountNumber, userName)
        );
        if (u == null || !u.getPassword().equals(password)) {
            return Result.error("账号或密码错误");
        }
        String token = tokenService.generateToken(u.getId(), userName, "managers", "管理员");
        return Result.success(token);
    }


    /**
     * 获取当前登录用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/session")
    public Result session(HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        return Result.success(managersService.getById(userId));
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param request
     * @return
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, HttpServletRequest request) {
        Managers u = managersService.getById((Integer) request.getSession().getAttribute("userId"));
        if (u != null && u.getPassword().equals(oldPassword)) {
            u.setPassword(newPassword);
            managersService.updateById(u);
            return Result.success();
        } else {
            return Result.error("旧密码错误");
        }

    }

    /**
     * 退出
     */
    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return Result.success("退出成功");
    }

    /**
     * 后台分页查询
     *
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestBody ManagersReq req) {
        PageUtils page = managersService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 新增
     *
     * @param managers
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Managers managers) {
        managersService.save(managers);
        return Result.success();
    }

    /**
     * 修改
     *
     * @param managers
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Managers managers) {
        managersService.updateById(managers);
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
        return Result.success(managersService.info(id));
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(managersService.removeById(id));
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(managersService.removeBatchByIds(ids));
    }

    /**
     * 分页查询
     *
     * @param req
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody ManagersReq req) {
        PageUtils page = managersService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 加载所有
     *
     * @return
     */
    @GetMapping("/loadAll")
    public Result loadAll() {
        return Result.success(managersService.list());
    }


}

