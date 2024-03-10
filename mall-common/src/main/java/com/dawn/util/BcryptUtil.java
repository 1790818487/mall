package com.dawn.util;

import cn.hutool.crypto.digest.BCrypt;

public class BcryptUtil {
    public static String encode(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());             //对明文密码进行加密,并返回加密后的密码
    }

    public static boolean match(String password, String encodePassword){          //将明文密码跟加密后的密码进行匹配，如果一致返回true,否则返回false
        return BCrypt.checkpw(password,encodePassword);
    }

//    public static void main(String[] args){
//        String password = "123456";     //明文密码
//        String psd = BcryptUtil.encode(password);       //加密后的密码
//        System.out.println(BcryptUtil.match(password, psd));            //如果一致，返回true，否则返回false
//    }
}
