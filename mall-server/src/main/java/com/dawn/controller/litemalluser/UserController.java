package com.dawn.controller.litemalluser;

import com.dawn.pojo.User;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
    @RequiresRoles("admin")
    public String userLogin(@RequestBody User user){
        return "success";
    }
}
