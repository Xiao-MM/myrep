package com.ming.inteceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ming.annotation.PassToken;
import com.ming.annotation.UserLoginToken;
import com.ming.exception.ExceptionManager;
import com.ming.pojo.User;
import com.ming.service.UserService;
import com.ming.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登录拦截器
 */
@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    @Autowired
    private ExceptionManager exceptionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        //拿到当前请求路径
        log.info(request.getRequestURI());
        //拿到当前用户，从token中获取
        //获取用户角色
        //判断该角色是否有该权限
        //log.info(request.getHttpServletMapping().toString());
        //log.info(request.getPathInfo());
        String token = request.getHeader("token");
        if (!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)object;
        Method method = handlerMethod.getMethod();
        //检查@PassToken注解，有@PassToken注解的方法将直接跳过认证
        if (method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()){
                return true;
            }
        }
        //检查有没有用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()){
                if (token == null){
                    throw exceptionManager.create("EC01004");
                }
                Integer id;
                try {
                    id = Integer.parseInt(JWT.decode(token).getAudience().get(0));
                    System.out.println(id);
                }catch (JWTDecodeException e){
                    throw exceptionManager.create("EC01003");
                }
                User user = userService.findUserById(id);
                if (user == null){
                    throw exceptionManager.create("EC01000");
                }
                boolean verify = JWTUtil.verify(token, user);
                if (!verify){
                    throw exceptionManager.create("EC01002");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
