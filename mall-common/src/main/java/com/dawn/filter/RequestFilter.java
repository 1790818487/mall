package com.dawn.filter;

import com.alibaba.fastjson.JSON;
import com.dawn.enums.login.ResponseEnum;
import com.dawn.exception.RequestException;
import com.dawn.util.ResponseVO;
import com.dawn.util.TokenUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "RequestFilter")
public class RequestFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        PrintWriter writer = httpServletResponse.getWriter();
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");

        //设置不需要token也能访问的地址
        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.equals("/admin/login") || requestURI.equals("/admin/register")) {
            chain.doFilter(request, response);
            return;
        }

        //从响应头得到token值
        String token = httpServletRequest.getHeader("token");

        //未得到token就抛出异常
        if (token == null)
        {
            writer.append(JSON.toJSONString(ResponseVO.error(ResponseEnum.TOKEN_NOT_EXISTS)));
            writer.close();
            return ;

        }

        //对得到的token验证
        if (!TokenUtil.check_token(token))
            throw new RequestException(ResponseEnum.TOKEN_NOT_EXISTS);
        String new_token = TokenUtil.upd_token(token);
        httpServletResponse.setHeader("new_token", new_token);

        //过期需要重新登录
        if (new_token.equals("expired"))
            throw new RequestException(ResponseEnum.TOKEN_EXPIRED);

        chain.doFilter(request, response);
    }
}
