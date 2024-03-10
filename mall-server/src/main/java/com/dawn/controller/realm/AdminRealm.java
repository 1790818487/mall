package com.dawn.controller.realm;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dawn.pojo.Admin;
import com.dawn.service.IAdminService;

import com.dawn.service.IPermissionService;
import com.dawn.service.IRoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AdminRealm extends AuthorizingRealm {


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
        System.out.println(admin);
        //这个是验证的实例
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//
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

        return null;
    }

    //自定义登录验证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException, UnauthenticatedException {
        String username = token.getPrincipal().toString();

        //根据用户名查询用户
        Admin admin = adminService.getOne(Wrappers.lambdaQuery(new Admin())
                .eq(Admin::getUsername, username), false);

        if (admin != null) {
            SimpleAuthenticationInfo mall = new SimpleAuthenticationInfo(
                    admin.getUsername(),
                    admin.getPassword(),
                    null,
                    token.getPrincipal().toString()
            );

            return mall;
        }
        return null;
    }
}
