//package com.dawn.config;
//
//import com.dawn.controller.shiro.AdminRealm;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
////@Component
//public class ShiroConfig {
//
//    @Autowired
//    private AdminRealm realm;
//
//    @Bean
//    public DefaultWebSecurityManager webSecurityManager() {
//        //1、创建defaultWebSecurityManager对象
//        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
//        //2、创建加密对象设置相关属性
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//        realm.setCredentialsMatcher(matcher);
//        return defaultWebSecurityManager;
//    }
//
//}
