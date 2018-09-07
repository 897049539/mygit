package com.neuedu.exception;

import com.neuedu.string_const.Const;

import javax.servlet.http.HttpSession;

/**
 * 自定义异常类
 */
public class BusinessException extends RuntimeException{
    private String msg; //异常信息
    private String warn; //异常提示
    private String url;  //跳转的url

    public BusinessException(){}

    private BusinessException(String msg, String warn, String url) {
        this.msg = msg;
        this.warn = warn;
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public String getWarn() {
        return warn;
    }

    public String getUrl() {
        return url;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public static  BusinessException createException(HttpSession httpSession,String msg,String warn,String url){
        BusinessException businessException = new BusinessException(msg,warn,url);
        httpSession.setAttribute(Const.EXCEPTION,businessException);
        return businessException;
    }
}
