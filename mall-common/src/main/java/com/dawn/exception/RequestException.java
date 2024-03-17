package com.dawn.exception;

import com.dawn.enums.login.ResponseEnum;

public class RequestException extends BaseException{


    public RequestException(ResponseEnum responseEnum) {
        super(null, responseEnum.getCode(), null, responseEnum.getMessage());
    }

}
