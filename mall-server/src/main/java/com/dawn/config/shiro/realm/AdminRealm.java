package com.dawn.config.shiro.realm;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dawn.pojo.Admin;
import com.dawn.service.IAdminService;
import com.dawn.service.IPermissionService;
import com.dawn.service.IRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    StringRedisTemplate redisTemplate;

    private static IAdminService adminService;


    private IPermissionService permissionService;


    private IRoleService roleService;

    @Autowired
    public void setAdminService(IAdminService adminService) {
        AdminRealm.adminService = adminService;
    }

    @Autowired
    public void setPermissionService(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    //自定义授权的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Admin admin = (Admin) principalCollection.getPrimaryPrincipal();

        System.out.println(admin.getRoleIds());
        //这个是验证的实例
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

//        List<Role> roleList = roleService.getById(admin.getId());
//        Set<String> roleSet = new HashSet<>();
//        List<Integer> roleIds = new ArrayList<>();
//        for (Role role : roleList) {
//            roleSet.add(role.getRole());
//            roleIds.add(role.getId());
//        }
//        // 放入角色信息
//        authorizationInfo.setRoles(roleSet);
//        // 放入权限信息
//        List<String> permissionList = permissionService.findByRoleId(roleIds);
//        authorizationInfo.setStringPermissions(new HashSet<>(permissionList));

        return authorizationInfo;
    }

    //自定义登录验证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException, UnauthenticatedException {
        //当用户登陆的时候,不存在token的时候进行调用

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();

        //根据用户名查询用户
        Admin admin = adminService.getOne(Wrappers.lambdaQuery(new Admin())
                .eq(Admin::getUsername, username), false);

        if (admin != null) {
            return new SimpleAuthenticationInfo(
                    admin.getUsername(),
                    admin.getPassword(),
                    null,
                    token.getPrincipal().toString()
            );
        }


        //这是只有token的情况
//        if (token instanceof AuthToken) {
//            AuthToken authToken = (AuthToken) token;
//            String username = authToken.getCredentials().toString();
//            //根据用户名查询用户
//            Admin admin = adminService.getOne(Wrappers.lambdaQuery(new Admin())
//                    .eq(Admin::getUsername, username), false);
//
//            //查询不到,说明用户不存在,直接抛出异常,让全局处理器捕获
//            if (admin == null)
//                throw new ShiroException(ResponseEnum.ACCOUNT_NOT_EXISTS);
//
//            return new SimpleAuthenticationInfo(
//                    admin.getUsername(),
//                    admin.getPassword(),
//                    null,
//                    token.getPrincipal().toString()
//            );
//
//        }

        return null;
    }

//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof AuthToken;
//    }
}
