package com.web.interceptor;

import cn.hutool.core.util.StrUtil;
import com.web.annotation.IgnoreAuth;
import com.web.domain.Token;
import com.web.exception.BusinessException;
import com.web.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: LoginInterceptor
 * Description:
 *      登录拦截器 , 进行登录校验
 *
 */

public class LoginInterceptor implements HandlerInterceptor {

    public static final String LOGIN_TOKEN_KEY = "Token";
    private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 打印请求信息
        LOG.info("------------- LoginInterceptor 开始 -------------");

        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        //支持跨域请求
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,request-source,Token, Origin,imgType, Content-Type, cache-control,postman-token,Cookie, Accept,authorization");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态

        // OPTIONS请求不做校验,
        // 前后端分离的架构, 前端会发一个OPTIONS请求先做预检, 对预检请求不做校验
        if (request.getMethod().toUpperCase().equals("OPTIONS")) {
            return true;
        }
        IgnoreAuth annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }
        String path = request.getRequestURL().toString();
        LOG.info("接口登录拦截:path:{}", path);
        //获取header的token参数
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        LOG.info("登录校验开始，token：{}", token);
        /**
         * 不需要验证权限的方法直接放过
         */
        if(annotation!=null) {
            return true;
        }
        if (StrUtil.isEmpty(token)) {
            LOG.info("token为空,请求被拦截");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new BusinessException("token为空,请求被拦截", HttpStatus.UNAUTHORIZED.value());

        }
        Token selectOne = tokenService.getTokenEntity(token);
        if (selectOne == null) {
            LOG.info("token无效,请求被拦截");
            throw new BusinessException("token无效,请求被拦截",HttpStatus.UNAUTHORIZED.value());
        }
        request.getSession().setAttribute("userId", selectOne.getUserId());
        request.getSession().setAttribute("role", selectOne.getRole());
        request.getSession().setAttribute("tableName", selectOne.getTableName());
        request.getSession().setAttribute("userName", selectOne.getUserName());
        LOG.info("登录校验通过");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("requestStartTime");
        LOG.info("------------- LoginInterceptor 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
