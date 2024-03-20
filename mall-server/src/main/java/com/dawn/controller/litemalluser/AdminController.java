package com.dawn.controller.litemalluser;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dawn.controller.litemalluser.dto.LoginDto;
import com.dawn.controller.litemalluser.dto.RegisterDto;
import com.dawn.enums.login.ResponseEnum;
import com.dawn.pojo.Admin;
import com.dawn.service.IAdminService;
import com.dawn.util.BcryptUtil;
import com.dawn.util.IPAddressUtil;
import com.dawn.util.R;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author Night
 * @since 2024-03-06
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate<Serializable, Session> redisTemplate;

    @PostMapping("login")
    public R userLogin(@RequestBody @Validated LoginDto dto) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                dto.getUsername(),
                dto.getPassword(),
                dto.isRememberMe(),
                dto.getHost()
        );
        if (redisTemplate==null)
            return R.success("空值了");
//        System.out.println(token.getPassword());
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return R.error(ResponseEnum.USER_PASSWORD_ERROR);
        }
        //返回用户信息
        System.out.println( subject.getPrincipal());
        Admin admin = (Admin) subject.getPrincipal();
        //返回的数据
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("token",subject.getSession().getId());
        hashMap.put("username",admin.getUsername());
        hashMap.put("avatar",admin.getAvatar());
        return R.success(ResponseEnum.LOGIN_SUCCESS, hashMap);
    }

    //用户注册
    @PostMapping("register")
    public R userRegister(@RequestBody @Validated RegisterDto registerDto) {

        //检查账号是不是存在
        boolean is_exists = adminService.exists(Wrappers.lambdaQuery(new Admin())
                .eq(Admin::getUsername, registerDto.getUsername()));
        if (is_exists)
            return R.error(ResponseEnum.ACCOUNT_EXISTS);
        //加密密码
        String password = BcryptUtil.encode(registerDto.getPassword());

        Admin admin = new Admin();
        admin.setPassword(password);
        admin.setUsername(registerDto.getUsername());
        admin.setLastLoginIp(IPAddressUtil.getIpAddr(httpServletRequest));
        admin.setAddTime(LocalDateTime.now());
        admin.setDeleted(false);
        if (registerDto.getIds() != null && registerDto.getIds().length != 0)
            admin.setRoleIds(Arrays.toString(registerDto.getIds()));

        //保存
        adminService.save(admin);
        return R.success();
    }

    //查询所有用户
    @GetMapping("query")
    @RequiresRoles("超级管理员")
    public R findAllUser() {
        List<Admin> list = adminService.list();
        return R.success(list);
    }

//    用户未登录
    @GetMapping("401")
    public R unlogin(){
        return R.error(ResponseEnum.USER_UN_LOGIN);
    }

    //登录成功
    @GetMapping("index")
    public R success(){
        return R.success();
    }

    //用户没有权限
    @GetMapping("403")
    public R unpermission(){
        return R.error(ResponseEnum.USER_UN_PERMISSION);
    }
}
