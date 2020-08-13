package com.ming.conf;

import com.ming.pojo.Permission;
import com.ming.pojo.Role;
import com.ming.pojo.User;
import com.ming.service.PermissionService;
import com.ming.service.RoleService;
import com.ming.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
        //获取当前用户
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByUsername(username);

        //获取当前用户所属角色
        List<Role> roles = roleService.findRoles(user.getId());
        roles.forEach(role -> {
            //获取每个角色所拥有的权限
            List<Permission> permissions = permissionService.findPermissions(role.getId());
            role.setPermissions(permissions);
        });

        //存储该用户授权信息
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
        if (user==null){
            throw new UnknownAccountException();
        }
        String credentials = user.getPasswordSalt();
        //将用户信息进行存储绑定
        return new SimpleAuthenticationInfo(
                user.getUsername(),//用户名
                user.getPassword(),//获取到的是数据库加密后的密码
                ByteSource.Util.bytes(credentials),//加密
                getName());
    }

    /**
     * 设置 realm的 HashedCredentialsMatcher
     */
    @PostConstruct
    public void setHashedCredentialsMatcher() {
        this.setCredentialsMatcher(hashedCredentialsMatcher());
    }
    /**
     * 凭证匹配 : 指定 加密算法,散列次数
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }
}
