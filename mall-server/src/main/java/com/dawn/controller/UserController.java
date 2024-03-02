package com.dawn.controller;

import com.dawn.pojo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 黎明
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/litemall-user")
public class UserController {

    @PostMapping("login")
    public String userLogin(User user){
        return "success";
    }
}
