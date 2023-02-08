package com.atguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/1 13:49
 * @Version 1.0
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "frame/index";
    }

    @RequestMapping("main")
    public String main() {
        return "frame/main";    }
}
