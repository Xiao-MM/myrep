package com.ming.inteceptor;


import com.ming.ResultBody;
import com.ming.exception.CommonException;
import com.ming.exception.ExceptionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;


@RestControllerAdvice(basePackages = "com.ming")
@Slf4j
public class CommonExceptionAdvice {
    @Autowired
    private ExceptionManager exceptionManager ;

    // 业务异常
    @ExceptionHandler(CommonException.class)
    public ResultBody baseExceptionHandler(CommonException e){
        if (e.getTrack()) {
            log.error("业务异常 :" + e.getMsg(),e);
        }else {
            log.error("业务异常" + e.getMsg());
        }
        return ResultBody.error(e);
    }

    // 参数认证异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBody MethodArgumentNotValidException(MethodArgumentNotValidException e ) {
        List<ObjectError> errors =e.getBindingResult().getAllErrors();
        StringBuffer errorMsg=new StringBuffer();
        errors.forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
        ResultBody  resultBody = ResultBody.error(exceptionManager.create("EC00002"));
        resultBody.setData(errorMsg);
        return resultBody ;
    }

    // 参数格式异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultBody MethodArgumentNotValidException(HttpMessageNotReadableException e ) {
        log.error("参数格式异常",e);
        ResultBody resultBody = ResultBody.error(exceptionManager.create("EC00003"));
        resultBody.setData(e.getMessage());
        return resultBody ;
    }

    // 参数null 异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultBody MethodArgumentNotValidException(ConstraintViolationException e ) {
        ResultBody resultBody = ResultBody.error(exceptionManager.create("EC00002"));
        resultBody.setData(e.getMessage());
        return resultBody ;
    }
    //权限认证 异常
    @ExceptionHandler(AuthorizationException.class)
    public ResultBody MethodUnauthenticatedException(AuthorizationException e) {
        log.error("权限认证异常",e);
        ResultBody resultBody = ResultBody.error(exceptionManager.create("EC00004"));
        resultBody.setData(e.getMessage());
        return resultBody ;
    }

    // 系统内部异常
    @ExceptionHandler(Exception.class)
    public ResultBody exceptionHandler(Exception e){
        log.error("系统内部异常",e);
        return ResultBody.error(exceptionManager.create("EC00001"));
    }
}
