package com.dawn.controller.litemalluser;


import cn.hutool.jwt.JWTUtil;
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
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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

    @PostMapping("login")
    public R userLogin(@RequestBody @Validated LoginDto dto) {

        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(
                dto.getUsername(),
                dto.getPassword(),
                dto.isRememberMe(),
                dto.getHost()
        );
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return R.error(ResponseEnum.USER_PASSWORD_ERROR);
        }
        //token的头部信息
        HashMap<String, Object> headers = new HashMap<>();

        //token的payload信息
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("username", dto.getUsername());
        payload.put("password",dto.getPassword());
        payload.put("isRememberMe", dto.isRememberMe());

        //签名
        byte[] bytes = "mall".getBytes();

        String jwtToken = JWTUtil.createToken(headers, payload, bytes);

        //返回的数据
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("access_token", jwtToken);
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
        if (registerDto.getIds() != null && registerDto.getIds().size() != 0)
            admin.setRoleIds(registerDto.getIds().toString());

        //保存
        adminService.save(admin);
        return R.success();
    }

    //查询所有用户
    @GetMapping("query")
    @RequiresRoles("admin")
    public R findAllUser() {
        List<Admin> list = adminService.list();
        return R.success(list);
    }
}
