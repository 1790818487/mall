//package com.dawn.controller.shiro;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.dawn.pojo.Admin;
//import com.dawn.service.IAdminService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import sun.net.www.protocol.http.AuthenticationInfo;
//
////@Component
//public class AdminRealm extends AuthorizingRealm {
//
//    @Autowired
//    private IAdminService adminService;
//
//    //自定义授权的方法
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        return null;
//    }
//
//    //自定义登录验证的方法
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
//            throws AuthenticationException {
//        String username = token.getPrincipal().toString();
//        Admin admin = adminService.getOne(Wrappers.lambdaQuery(new Admin())
//                .eq(Admin::getUsername, username));
//        if (admin != null) {
//            return new SimpleAuthenticationInfo(
//                    token.getPrincipal(),
//                    admin.getPassword(),
//                    ByteSource.Util.bytes("mall"),
//                    token.getPrincipal().toString()
//            );
//        }
//        return null;
//    }
//}
