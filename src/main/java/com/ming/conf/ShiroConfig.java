package com.ming.conf;

import com.ming.pojo.Permission;
import com.ming.service.PermissionService;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * Shiro内置过滤器，可以实现权限相关的拦截
 *  常用的过滤器：
 *      anon: 无需认证（登录）可以访问
 *      authc: 必须认证才可以访问
 *      user: 如果使用rememberMe的功能可以直接访问
 *      perms: 该资源必须得到资源权限才可以访问
 *      role: 该资源必须得到角色权限才可以访问
 */
@Configuration
public class ShiroConfig {
    @Resource
    private UserAuthRealm userAuthRealm;

    @Resource
    private PermissionService permissionService;
    /**
     * 配置 资源访问策略 web应用程序 shiro核心过滤器
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(defaultWebSecurityManager);
        factoryBean.setLoginUrl("/user/login");//设置登录页
        factoryBean.setSuccessUrl("/success");//设置成功页
        factoryBean.setUnauthorizedUrl("/unauthorized");//未授权界面
        factoryBean.setFilterChainDefinitionMap(setFilterChainDefinitionMap());//配置拦截过滤器
        return factoryBean;
    }
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userAuthRealm);
        return defaultWebSecurityManager;
    }

    /**
     * 开启shiro 注解支持. 使以下注解能够生效 :
     * 需要认证 {@link org.apache.shiro.authz.annotation.RequiresAuthentication RequiresAuthentication}
     * 需要用户 {@link org.apache.shiro.authz.annotation.RequiresUser RequiresUser}
     * 需要访客 {@link org.apache.shiro.authz.annotation.RequiresGuest RequiresGuest}
     * 需要角色 {@link org.apache.shiro.authz.annotation.RequiresRoles RequiresRoles}
     * 需要权限 {@link org.apache.shiro.authz.annotation.RequiresPermissions RequiresPermissions}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * 配置 拦截过滤器链.  map的键 : 资源地址 ;  map的值 : 所有默认Shiro过滤器实例名
     * 默认Shiro过滤器实例 参考 : {@link org.apache.shiro.web.filter.mgt.DefaultFilter}
     */
    private Map<String,String> setFilterChainDefinitionMap(){
        Map<String,String> filterMap = new LinkedHashMap<>();
        //注册数据库的所有权限，及其对应的url
        List<Permission> allPermissions = permissionService.findPermissions();
        allPermissions.forEach(permission ->  filterMap.put(permission.getUrl(),"perms["+ permission.getName()+"]"));

        //公开swagger-ui
        filterMap.put("/swagger-resources/**","anon");
        filterMap.put("/webjars/**","anon");
        filterMap.put("/v2/**","anon");
        filterMap.put("/swagger-ui.html/**","anon");

        filterMap.put("/user/login","anon"); //公开登录接口
        filterMap.put("/open/api/sayHello","anon");//所有人都可以访问的接口
        filterMap.put("/user/logout","user");//不用anon
        filterMap.put("/**","authc");//所有资源都需要验证

        return filterMap;
    }
}
