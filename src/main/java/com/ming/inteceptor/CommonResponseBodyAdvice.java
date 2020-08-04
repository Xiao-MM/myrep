package com.ming.inteceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ming.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.ming.controller")
public class CommonResponseBodyAdvice implements ResponseBodyAdvice {

    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public CommonResponseBodyAdvice(ObjectMapper jacksonObjectMapper) {
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if ( !(o instanceof ResultBody)) {
           if(o instanceof String) {
               try {
                   return jacksonObjectMapper.writeValueAsString( ResultBody.success(o));
               } catch (JsonProcessingException e) {
                   throw  new RuntimeException(e) ;
               }
           }
           return ResultBody.success(o);
       }
        return o;
    }
}
