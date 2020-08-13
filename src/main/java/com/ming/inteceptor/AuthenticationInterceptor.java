package com.ming.inteceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ming.ResultBody;
import com.ming.exception.ExceptionManager;
import com.ming.pojo.User;
import com.ming.service.UserService;
import com.ming.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截器，处理JWT登录方式的请求
 */
@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;
    @Autowired
    ExceptionManager exceptionManager;
    @Resource
    Environment environment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        //拿到当前请求路径
        log.info("AuthenticationInterceptor-->"+request.getRequestURI());
        if(!verifyLogin(request,response)){
            return false;
        }
        return true;
    }

    private Boolean verifyLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("token");
        if (token==null){
            setErrorResponse("EC01004",response);
            return false;
        }
        Long uid;
        try {
            uid = JWTUtil.getUid(token);
        }catch (JWTDecodeException e){
            setErrorResponse("EC01003",response);
            return false;
        }
        if (uid!=null){
            User user = userService.findUserById(uid);
            if (user==null||!(JWTUtil.verify(token, user))){
                setErrorResponse("EC01005",response);
                return false;
            }
            request.getSession().setAttribute("uid",uid);
        }
        return true;
    }
    private void  setErrorResponse (String code , HttpServletResponse response ) throws IOException {
        ResultBody resultBody = new ResultBody(code,environment.getProperty(code)) ;
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().println( new ObjectMapper().writeValueAsString(resultBody));
    }

}
