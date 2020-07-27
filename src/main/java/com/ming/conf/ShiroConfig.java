package com.ming.conf;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 配置ShiroFilterFactoryBean
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactorBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截
         *  常用的过滤器：
         *      anon: 无需认证（登录）可以访问
         *      authc: 必须认证才可以访问
         *      user: 如果使用rememberMe的功能可以直接访问
         *      perms: 该资源必须得到资源权限才可以访问
         *      role: 该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
        //放行该链接(一定要写前面)
        filterMap.put("/employee/add","perms[employee:add]");
        //拦截成功默认跳转login.jsp
        filterMap.put("/employee/**","authc");
        //filterMap.put("/employee/view","authc");
        filterMap.put("/employee/login/**","anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //设置拦截成功转向的url
        shiroFilterFactoryBean.setLoginUrl("/employee/to_login");
        //设置未授权时转向的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/employee/unAuth");
        return shiroFilterFactoryBean;
    }
    /**
     * DefaultWebSecurityManager
     */
    @Bean(value = "securityManager")
    public DefaultWebSecurityManager getDefaultManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }
    /**
     * 创建Realm
     */
    @Bean(value = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
}
