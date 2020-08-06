package com.ming.inteceptor;

import com.ming.exception.ExceptionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    ExceptionManager exceptionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("PermissionInterceptor-->"+request.getRequestURI());
        return true;
    }
}
