package com.ken_xing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author ken_xing
 * @Date 2021/2/10--17:17
 * @Version 1.0
 * @Description
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello()
    {
        return "Hello SpringBoot!";
    }

}
