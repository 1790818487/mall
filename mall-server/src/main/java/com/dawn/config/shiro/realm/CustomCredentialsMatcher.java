package com.dawn.config.shiro.realm;

import com.dawn.util.BcryptUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获得前台传过来的密码
        String tokenCredentials = new String((char[]) token.getCredentials());
        //这是数据库里查出来的密码
        Object credentials = info.getCredentials();

        return BcryptUtil.match(tokenCredentials.toString(),credentials.toString());
    }
}
