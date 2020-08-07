package com.ming.conf;

import com.ming.pojo.Permission;
import com.ming.pojo.Role;
import com.ming.pojo.User;
import com.ming.service.PermissionService;
import com.ming.service.RoleService;
import com.ming.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基于角色访问控制权限的核心Realm
 * @author zm
 */
@Component
@Slf4j
public class UserAuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    /**
     * 进行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权逻辑
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        log.info("========="+principalCollection.getPrimaryPrincipal());
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByUsername(username);
        List<Role> roles = roleService.findRoles(user.getId());
        roles.forEach(role -> {
            List<Permission> permissions = permissionService.findPermissionsByRoleId(role.getId());
            role.setPermissions(permissions);
        });

        for (Role role:roles){
            //添加角色
            authorizationInfo.addRole(role.getName());
            for (Permission permission:role.getPermissions()){
                //添加权限
                authorizationInfo.addStringPermission(permission.getName());
            }
        }
        return authorizationInfo;
    }

    /**
     * 进行认证逻辑
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //当执行用户登录就会跳转到这里
        String username = (String)token.getPrincipal();
        //在这里进行验证
        User user = userService.findUserByUsername(username);
        //String credentials = "salt"+username+"salt";
        if (user==null){
            return null;
        }
        //将用户信息进行存储绑定
        return new SimpleAuthenticationInfo(
                user.getUsername(),//用户名
                user.getPassword(),//密码
                //ByteSource.Util.bytes(credentials),//加密
                getName());
    }
}
