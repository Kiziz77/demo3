package com.web.controller;

import com.web.annotation.IgnoreAuth;
import com.web.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: TestController
 * Description:
 *
 * @Author Mr.hu
 * @Create 2025-01-22 13:52
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/test")
    public Result test(){
        return Result.success("你好");
    }
}
