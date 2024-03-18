package com.dawn.exception;

import com.dawn.enums.login.ResponseEnum;

//@RestControllerAdvice
public class ShiroException extends BaseException{

    public ShiroException(ResponseEnum responseEnum) {
        super(responseEnum);
    }

}
