package com.fsnteam.fsnweb.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsnteam.fsnweb.util.Result;
import com.fsnteam.fsnweb.util.ReturnCode;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    ObjectMapper objectMapping = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        if (e instanceof AccountExpiredException) {
            //账号过期
            httpServletResponse.getWriter()
                    .write(objectMapping.writeValueAsString(Result.error().isSuccess(false)
                            .code(ReturnCode.USER_ACCOUNT_EXPIRED.getCode())
                            .message(ReturnCode.USER_ACCOUNT_EXPIRED.getMessage())));
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            httpServletResponse.getWriter()
                    .write(objectMapping.writeValueAsString(Result.error().isSuccess(false)
                            .code(ReturnCode.USER_CREDENTIALS_ERROR.getCode())
                            .message(ReturnCode.USER_CREDENTIALS_ERROR.getMessage())));
        } else if (e instanceof LockedException) {
            //账号锁定
            httpServletResponse.getWriter()
                    .write(objectMapping.writeValueAsString(Result.error().isSuccess(false)
                            .code(ReturnCode.USER_ACCOUNT_LOCKED.getCode())
                            .message(ReturnCode.USER_ACCOUNT_LOCKED.getMessage())));
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            httpServletResponse.getWriter()
                    .write(objectMapping.writeValueAsString(Result.error().isSuccess(false)
                            .code(ReturnCode.USER_ACCOUNT_NOT_EXIST.getCode())
                            .message(ReturnCode.USER_ACCOUNT_NOT_EXIST.getMessage())));
        } else {
            //其他错误
            httpServletResponse.getWriter()
                    .write(objectMapping.writeValueAsString(Result.error().isSuccess(false)
                            .code(ReturnCode.COMMON_FAIL.getCode())
                            .message(ReturnCode.COMMON_FAIL.getMessage())));
        }
//        //塞到HttpServletResponse中返回给前台
//        httpServletResponse.getWriter().write(JSON.toJSONString(Result.error()));
    }
}
