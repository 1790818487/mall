package com.dawn.config.shiro.realm;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dawn.enums.login.ResponseEnum;
import com.dawn.exception.ShiroException;
import com.dawn.pojo.Admin;
import com.dawn.pojo.Permission;
import com.dawn.pojo.Role;
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
import org.springframework.stereotype.Component;

import java.util.*;


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

        String username = principalCollection.getPrimaryPrincipal().toString();

        //这个是验证的实例
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Admin admin = adminService.getOne(Wrappers.lambdaQuery(Admin.class).eq(
                Admin::getUsername, username
        ));

        String stringRoleIds = admin.getRoleIds();
        List<Integer> roleIds = new ArrayList<>();
        Arrays.stream(
                stringRoleIds.replace("[", "")
                        .replace("]", "")
                        .replaceAll(" ", "")
                        .split(",")).forEach(
                data -> roleIds.add(Integer.valueOf(data))
        );

        if (roleIds.size() == 0)
            throw new ShiroException(ResponseEnum.ACCOUNT_NOT_ROLE);


        //添加角色
        Set<String> roles = new HashSet<>();
        roleService.list(
                Wrappers.lambdaQuery(Role.class)
                        .eq(Role::getEnabled, true)
                        .eq(Role::getDeleted, false)
                        .in(Role::getId, roleIds)
                        .select(Role::getName)
        ).forEach(
                role -> roles.add(role.getName())
        );
        authorizationInfo.setRoles(roles);

        //添加权限
        Set<String> permissions = new HashSet<>();
        permissionService.list(
                Wrappers.lambdaQuery(Permission.class)
                        .in(Permission::getRoleId, roleIds)
                        .select(Permission::getPermission)
        ).forEach(
                permission -> permissions.add(permission.getPermission())
        );

        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    //自定义登录验证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException, UnauthenticatedException {
        //当用户登陆的时候,不存在token的时候和登录和权限验证的时候进行调用

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
        return null;
    }

}
