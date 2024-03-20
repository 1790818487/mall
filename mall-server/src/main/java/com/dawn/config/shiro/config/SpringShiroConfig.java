package com.dawn.config.shiro.config;

import com.dawn.config.shiro.realm.AdminRealm;
import com.dawn.config.shiro.realm.CustomCredentialsMatcher;
import com.dawn.config.shiro.realm.RedisSessionDAO;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class SpringShiroConfig {

    private RedisTemplate redisTemplate;

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        this.redisTemplate=redisTemplate;
        return redisTemplate;
    }

    //通过调用Initializable.init()和Destroyable.destroy()方法,从而去管理shiro bean生命周期
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public SessionDAO redisSessionDAO() {
        return new RedisSessionDAO(redisTemplate);
    }


    //配置密码匹配
    @Bean
    public AdminRealm userRealm() {
        AdminRealm adminRealm = new AdminRealm();
        adminRealm.setCredentialsMatcher(customCredentialsMatcher());
        return adminRealm;
    }

    //配置返回自定义的匹配器，给spring管理
    @Bean
    public CustomCredentialsMatcher customCredentialsMatcher() {
        return new CustomCredentialsMatcher();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //使用自定义的缓存方式,即使用redis存储认证的信息
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(redisSessionDAO());
        securityManager.setSessionManager(defaultWebSessionManager);
        //使用自定义的session管理器
//        securityManager.setSessionManager(customWebSessionManager());

        //设置密码匹配器
        securityManager.setRealm(userRealm());
        return securityManager;
    }


    /**
     * 路径过滤规则
     *
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

//        //设置自定义过滤
//        HashMap<String, Filter> stringFilterHashMap = new HashMap<>();
//        stringFilterHashMap.put("jwt", new JwtFilter());
//        shiroFilterFactoryBean.setFilters(stringFilterHashMap);

        Map<String, String> map = new LinkedHashMap<>();
        // 有先后顺序
        map.put("/admin/login", "anon");      // 允许匿名访问
        map.put("/admin/register", "anon");      // 允许匿名访问
        map.put("/admin/index", "anon");      // 允许匿名访问
        map.put("/admin/401", "anon");      // 允许匿名访问
        map.put("/admin/403", "anon");      // 允许匿名访问

        map.put("/**", "authc");        // 访问我自定义的过滤器

        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/403");
        shiroFilterFactoryBean.setLoginUrl("/admin/401");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启Shiro注解模式，可以在Controller中的方法上添加注解
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


}
