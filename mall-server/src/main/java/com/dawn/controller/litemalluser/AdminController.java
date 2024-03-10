package com.dawn.controller.litemalluser;


import cn.hutool.core.lang.UUID;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dawn.controller.litemalluser.dto.LoginDto;
import com.dawn.enums.login.ResponseEnum;
import com.dawn.pojo.Admin;
import com.dawn.service.IAdminService;
import com.dawn.util.BcryptUtil;
import com.dawn.util.ResponseVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;


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

    @PostMapping("login")
    public Object userLogin(@RequestBody @Validated LoginDto dto) throws InstantiationException, IllegalAccessException {

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
            return ResponseVO.error(ResponseEnum.USER_PASSWORD_ERROR);
        }
        //token的头部信息
        HashMap<String, Object> headers = new HashMap<>();

        //token的payload信息
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("user", dto.getUsername());
        payload.put("create_time", new Date());
        if (!dto.isRememberMe())
            //过期时间一小时
            payload.put("expired_time", new Date().getTime()+60*60*1000);
        payload.put("uuid", UUID.randomUUID().toString());

        //签名
        byte[] bytes = "mall".getBytes();

        String jwtToken = JWTUtil.createToken(headers, payload, bytes);

        //返回的数据
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", jwtToken);
        return ResponseVO.success(ResponseEnum.LOGIN_SUCCESS, hashMap);
    }

    //用户注册
    @PostMapping("register")
    public ResponseVO userRegister(@RequestBody @Validated Admin admin) {
        Admin is_exists = adminService.getOne(Wrappers.lambdaQuery(new Admin())
                .eq(Admin::getUsername, admin.getUsername()));
        if (is_exists != null)
            return ResponseVO.error(ResponseEnum.ACCOUNT_EXISTS);
        //加密密码
        String password = BcryptUtil.encode(admin.getPassword());
        admin.setPassword(password);
        adminService.save(admin);
        return ResponseVO.success();
    }
}
