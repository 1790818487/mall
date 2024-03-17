package com.dawn.util;

import cn.hutool.jwt.JWTUtil;

import java.util.Date;

public class TokenUtil {
    private TokenUtil() {
    }

    public static boolean check_token(String token) {
        return JWTUtil.verify(token, "mall".getBytes());
    }

    public static String upd_token(String token) {

        String expired_time = JWTUtil.parseToken(token).getPayload("expired_time").toString();
        long time = Integer.parseInt(expired_time);
        if (time - new Date().getTime() < 0)
            return "expired";
        else {
            JWTUtil.parseToken(token).setPayload("expired_time", new Date().getTime() + 60 * 60 * 1000);
            JWTUtil.parseToken(token).setPayload("created_time", new Date());
        }
        return token;
    }
}
