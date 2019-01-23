package cn.edu.xjtu.cad.templates.aop;

import cn.edu.xjtu.cad.templates.model.ResultCode;

public class MyException extends Throwable{

    private Integer code;

    private String msg;

    public MyException(ResultCode resultCode){
        this.code = resultCode.code();
        this.msg = resultCode.message();
    }
    public MyException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
