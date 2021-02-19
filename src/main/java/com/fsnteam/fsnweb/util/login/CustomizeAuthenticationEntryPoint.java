package com.fsnteam.fsnweb.util.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsnteam.fsnweb.util.Result;
import com.fsnteam.fsnweb.util.ReturnCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        ObjectMapper objectMapping=new ObjectMapper();
        httpServletResponse.getWriter().write(objectMapping.writeValueAsString(Result.error().isSuccess(false)
                .code(ReturnCode.AUTHORITY_ERROR.getCode())
                .message(ReturnCode.AUTHORITY_ERROR.getMessage())));
    }
}
