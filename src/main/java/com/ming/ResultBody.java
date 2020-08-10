package com.ming;

import com.ming.exception.CommonException;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResultBody {
    private String code ;
    private String msg;
    private Object data;

    public ResultBody(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public ResultBody(String code , String msg , Object data) {
        this.code = code;
        this.msg =msg;
        this.data =data;
    }

    public static ResultBody success (Object data) {
        return new ResultBody("200","success",data);
    }

    public static ResultBody error(CommonException exception) {
        return new ResultBody(exception.getCode(),exception.getMsg(),exception.getData());
    }

}
