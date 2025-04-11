package com.web.controller;

import com.web.domain.Address;
import com.web.service.AddressService;
import com.web.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService service;

    @PostMapping("/add")
    public Result add(@RequestBody Address Address) {
        service.add(Address);
        return Result.success();
    }

    @PutMapping("/edit")
    public Result edit(@RequestBody Address Address) {
        service.edit(Address);
        return Result.success();
    }


    @GetMapping("/queryList")
    public Result queryList() {
        List<Address> list = service.queryList();
        return Result.success(list);
    }

    @DeleteMapping("/deletedById/{id}")
    public Result deletedById(@PathVariable("id")Integer id) {
        service.removeById(id);
        return Result.success();
    }

}
