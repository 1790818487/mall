package com.dawn.exception;

import com.dawn.enums.login.ResponseEnum;

public class BaseException extends RuntimeException{
    private static final long serialVersionUID = 1L;


    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */

    private String message;

    public BaseException(ResponseEnum responseEnum)
    {
        this.code=responseEnum.getCode();
        this.message= responseEnum.getMessage();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}