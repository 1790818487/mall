package com.dawn.config.shiro.realm;

import org.apache.shiro.authc.AuthenticationToken;

public class AuthToken implements AuthenticationToken {
    String token;
 
    public AuthToken(String token){
        this.token=token;
    }
 
    @Override
    public Object getPrincipal() {
        return token;
    }
 
    @Override
    public Object getCredentials() {
        return token;
    }
}
