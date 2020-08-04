package com.ming.exception;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;

@Component
public class ExceptionManager {
    @Resource
    Environment environment;

    public CommonException create(String code)  {
        return new CommonException(code, environment.getProperty(code));
    }

    public CommonException create(String code, Throwable throwable)  {
        return new CommonException(code, environment.getProperty(code),throwable);
    }

    public CommonException create(String code, Object data)  {
        return new CommonException(code, environment.getProperty(code),data);
    }

    public  <T extends CommonException>  T create(String code , Class<T> t) {
        T result ;
        try {
            Constructor<T> constructor = t.getConstructor(String.class,String.class);
            constructor.setAccessible(true);
            result = constructor.newInstance(code, environment.getProperty(code));
        } catch (Exception e) {
            throw  new  RuntimeException(e) ;
        }
        return result ;
    }

}
