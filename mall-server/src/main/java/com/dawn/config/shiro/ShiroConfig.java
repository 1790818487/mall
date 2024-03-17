package com.dawn.config.shiro;
import com.dawn.controller.realm.AdminRealm;
import com.dawn.controller.realm.CustomCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

//配置密码匹配
    @Bean
    public AdminRealm userRealm() {
        AdminRealm adminRealm = new AdminRealm();
        adminRealm.setCredentialsMatcher(customCredentialsMatcher());
        return adminRealm;
    }

    //配置返回自定义的匹配器，给spring管理
    @Bean
    public CustomCredentialsMatcher customCredentialsMatcher(){
        return new CustomCredentialsMatcher();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置密码匹配器
        securityManager.setRealm(userRealm());

        return securityManager;
    }

    /**
     * 路径过滤规则
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");
        Map<String, String> map = new LinkedHashMap<>();
        // 有先后顺序
        map.put("/admin/login", "anon");      // 允许匿名访问
        map.put("/admin/register", "anon");      // 允许匿名访问

        map.put("/**", "authc");        // 进行身份认证后才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启Shiro注解模式，可以在Controller中的方法上添加注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
