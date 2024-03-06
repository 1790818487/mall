//package com.dawn.controller.litemalluser;
//
//
//import com.dawn.controller.litemalluser.dto.LoginDto;
//
//import com.dawn.util.ResponseUtil;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import org.springframework.web.bind.annotation.RestController;
//import sun.net.util.IPAddressUtil;
//
//import javax.security.auth.Subject;
//
///**
// * <p>
// * 管理员表 前端控制器
// * </p>
// *
// * @author Night
// * @since 2024-03-06
// */
//@RestController
//@RequestMapping("/admin")
//public class AdminController {
//
//    @PostMapping("login")
//    public Object userLogin(@RequestBody @Validated LoginDto dto) {
//        Subject subject = SecurityUtils.getSubject();
//        AuthenticationToken token = new UsernamePasswordToken(
//                dto.getUsername(),
//                dto.getPassword(),
//                dto.isRememberMe(),
//                dto.getHost()
//        );
//        try {
//            subject.login(token);
//            return ResponseUtil.ok(
//                    token
//            );
//        }
//        catch (AuthenticationException authenticationException){
//            return ResponseUtil.unlogin();
//        }
//
//    }
//}
