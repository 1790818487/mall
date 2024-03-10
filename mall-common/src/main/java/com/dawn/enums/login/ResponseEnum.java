package com.dawn.enums.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseEnum {

    //这里是账号相关的返回枚举
    SUCCESS(200,"操作成功"),
    LOGIN_SUCCESS(200,"登录成功"),
    SUCCESS_REGISTER(200,"注册成功"),
    USER_UN_LOGIN(401,"用户未登录"),
    USER_PASSWORD_ERROR(402,"账号或密码错误,请重试"),
    USER_UN_PERMISSION(403,"用户无权限,请联系管理员"),
    ACCOUNT_NOT_ENABLE(405,"账号未激活"),
    ACCOUNT_EXISTS(406,"账号已经存在"),


    //用户访问的时候的权限的控制返回枚举
    TOKEN_EXPIRED(401,"登录状态失效"),
    TOKEN_NOT_EXISTS(401,"签名验证未通过"),



    //参数异常
    BAD_PARAMETER(501,"参数错误"),


    //返回一个最大的未知异常
    UNKNOW_EXCEPTION(9999,"服务器发生未知异常");


    private int code;
    private String message;
}
