package com.ming.exception;
public class CommonException extends  RuntimeException  {
    private boolean track ;
    private  String code ;
    private  String msg;
    private Object data ;


    public CommonException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CommonException(String code, String msg,Object data) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.data =data ;
    }

    public CommonException(String code, String msg,Throwable throwable) {
        super(msg,throwable);
        this.track =true ;
        this.code = code;
        this.msg = msg;
    }

    public String getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }
    public Object getData (){
        return this.data ;
    }

    public boolean getTrack() {
        return  this.track;
    }
}
