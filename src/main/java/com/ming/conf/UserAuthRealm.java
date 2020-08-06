package com.ming.conf;

import com.ming.pojo.Permission;
import com.ming.pojo.Role;
import com.ming.pojo.User;
import com.ming.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 基于角色访问控制权限的核心Realm
 * @author zm
 */
@Component
public class UserAuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /**
     * 进行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权逻辑
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User)principalCollection.getPrimaryPrincipal();
        for (Role role:user.getRoles()){
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
        User user = userService.findUserByUsername(username);
        //String credentials = "salt"+username+"salt";
        if (user==null){
            return null;
        }
        return new SimpleAuthenticationInfo(
                user.getUsername(),//用户名
                user.getPassword(),//密码
                //ByteSource.Util.bytes(credentials),//加密
                getName());
    }
}
