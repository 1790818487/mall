//package com.dawn.config.shiro.filter;
//
//import cn.hutool.jwt.JWTUtil;
//import com.dawn.util.IPAddressUtil;
//import lombok.SneakyThrows;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class JwtFilter extends BasicHttpAuthenticationFilter {
//
//    /**
//     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
//     * 例如我们提供一个地址 GET /article
//     * 登入用户和游客看到的内容是不同的
//     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
//     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
//     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
//     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
//     */
//    @SneakyThrows
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        if (isLoginAttempt(request, response)) {
//            executeLogin(request, response);
//        }
//        return true;
//    }
//
//    /**
//     * 判断用户是否想要登入。
//     * 检测header里面是否包含Authorization字段即可
//     * 并且验证token
//     */
//    @SneakyThrows
//    @Override
//    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
//        HttpServletRequest req = (HttpServletRequest) request;
//        String authorization = req.getHeader("access_token");
//
//        return authorization!=null;
//    }
//
//
//    @Override
//    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String access_token = httpServletRequest.getHeader("access_token");
//
//        String username = JWTUtil.parseToken(access_token).getPayload("username").toString();
//        String password = JWTUtil.parseToken(access_token).getPayload("password").toString();
//        boolean isRememberMe=Boolean.getBoolean(
//                JWTUtil.parseToken(access_token).getPayload("isRememberMe").toString());
//
//        AuthenticationToken token = new UsernamePasswordToken(
//                username,
//                password,
//                isRememberMe,
//                IPAddressUtil.getIpAddr(httpServletRequest)
//        );
//
//        getSubject(request, response).login(token);
//
//        return true;
//    }
//
//    /**
//     * 对跨域提供支持
//     */
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpServletResponse.setStatus(HttpStatus.OK.value());
//            return false;
//        }
//        return super.preHandle(request, response);
//    }
//}
