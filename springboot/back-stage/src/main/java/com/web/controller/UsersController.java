package com.web.controller;

import cn.hutool.captcha.CaptchaUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.UsersReq;
import com.web.service.TokenService;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.web.service.UsersService;
import com.web.domain.Users;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * 
 * 
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Resource
    private UsersService usersService;

    @Resource
    private TokenService tokenService;

    @IgnoreAuth
    @RequestMapping(value = "/login")
    public Result login(String userName, String password) {
        Users u = usersService.getOne(
                Wrappers.lambdaQuery(Users.class)
                        .eq(Users::getUserName, userName)
        );
        if (u == null || !u.getPassword().equals(password)){
            return Result.error("账号或密码错误");
        }
        String token = tokenService.generateToken(u.getId(),userName, "users", "用户");
        return Result.success(token);
    }


    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    @GetMapping("/session")
    public Result session(HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        return Result.success(usersService.getById(userId));
    }


    /**
     * 注册
     * @param users
     * @return
     */
    @IgnoreAuth
    @PostMapping("/register")
    public Result register(@RequestBody Users users){
        if (usersService.getOne(
                Wrappers.lambdaQuery(Users.class)
                        .eq(Users::getUserName, users.getUserName())
        ) != null){
            return Result.error("用户名已存在");
        }
        usersService.save(users);
        return Result.success();
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param request
     * @return
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,HttpServletRequest request){
        Users u = usersService.getById((Integer) request.getSession().getAttribute("userId"));
        if (u != null && u.getPassword().equals(oldPassword)){
            u.setPassword(newPassword);
            usersService.updateById(u);
            return Result.success();
        }else {
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
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestBody UsersReq req){
        PageUtils page = usersService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 新增
     * @param users
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Users users ) {
        users.setBalance(0.0);
        users.setPassword("123456");
        usersService.save(users);
        return Result.success();
    }

    /**
      * 修改
      * @param users
      * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody Users users) {
        usersService.updateById(users);
        return Result.success();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(usersService.info(id));
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(usersService.removeById(id));
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(usersService.removeBatchByIds(ids));
    }

    /**
     * 分页查询
     * @param req
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody UsersReq req){
        PageUtils page = usersService.selectPage(req);
        return Result.success(page);
    }

    /**
     * 加载所有
     * @return
     */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(usersService.list());
    }


}

