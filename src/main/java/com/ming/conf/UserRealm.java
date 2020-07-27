package com.ming.conf;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm {
    /**
     * 进行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进行授权逻辑");
        return null;
    }

    /**
     * 进行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //当执行用户登录就会跳转到这里
        System.out.println("进行认证逻辑");
        String username = "zs";
        String password = "123456";

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        if (!token.getUsername().equals(username)){
            return null;//shiro底层会抛出UnknownAccountException
        }
        return new SimpleAuthenticationInfo("",password,"");
    }
}
