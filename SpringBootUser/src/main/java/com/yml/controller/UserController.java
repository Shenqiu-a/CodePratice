package com.yml.controller;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-1117:18
 */

public class UserController {

    @GetMapping("test")
    public String test(){
        return "1234";
    }

}
