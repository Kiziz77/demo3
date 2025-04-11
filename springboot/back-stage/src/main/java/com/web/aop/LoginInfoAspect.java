package com.web.aop;

import com.web.service.LoginInfoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class LoginInfoAspect {

    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private HttpServletRequest request;

    // 定义切点，拦截所有带有 @MyAnnotation 注解的方法
    @Pointcut("@annotation(com.web.aop.LogAnnotation)")
    public void loginMethod() {}

    // 切入点：匹配所有登录方法
    @Before("loginMethod()")
    public void logBeforeLogin(JoinPoint joinPoint) {
        // 获取方法参数，假设用户名是第一个参数
        Object[] args = joinPoint.getArgs();
        // 获取 HttpSession 和登录相关的信息
        HttpSession session = request.getSession();
        String username = String.valueOf(args[0]);
        String userIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");
        String sessionId = session.getId();

        // 创建并保存登录信息
        loginInfoService.saveLoginInfo(username, userIp, userAgent, referer, sessionId);
    }

}
