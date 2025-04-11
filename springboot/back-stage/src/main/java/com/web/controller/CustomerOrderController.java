package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.domain.Req.CustomerOrderReq;
import com.web.utils.PageUtils;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;

import com.web.service.CustomerOrderService;
import com.web.domain.CustomerOrder;

import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
*
* 购买订单
* @author admin admin@126.com
*/
@RestController
@RequestMapping("/customerOrder")
public class CustomerOrderController {

@Resource
private CustomerOrderService customerOrderService;

    /**
    * 后台分页查询
    * @return
    */
    @PostMapping("/page")
    public Result page(@RequestBody CustomerOrderReq req){
        PageUtils page = customerOrderService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 新增
    * @param customerOrder
    * @return
    */
    @PostMapping("/save")
    public Result save(@RequestBody CustomerOrder customerOrder ) {
        customerOrderService.save(customerOrder);
        return Result.success();
    }

    /**
    * 修改
    * @param customerOrder
    * @return
    */
    @PostMapping("/update")
    public Result update(@RequestBody CustomerOrder customerOrder) {
        customerOrderService.updateById(customerOrder);
        return Result.success();
    }

    /**
    * 根据id查询
    * @param id
    * @return
    */
    @GetMapping("/info/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(customerOrderService.info(id));
    }

    /**
    * 根据id删除
    * @param id
    * @return
    */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Long id){
        return Result.success(customerOrderService.removeById(id));
    }


    /**
    * 批量删除
    * @param ids
    * @return
    */
    @RequestMapping("/del/batch")
    public Result deleteBatch(@RequestBody Long[] ids){
        return Result.success(customerOrderService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
    * 分页查询
    * @param req
    * @return
    */
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestBody CustomerOrderReq req){
        PageUtils page = customerOrderService.selectPage(req);
        return Result.success(page);
    }

    /**
    * 加载所有
    * @return
    */
    @GetMapping("/loadAll")
    public Result loadAll(){
        return Result.success(customerOrderService.list());
    }




}

