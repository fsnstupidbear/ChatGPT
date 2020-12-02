package com.fsnteam.fsnweb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsnteam.fsnweb.util.Result;
import com.fsnteam.fsnweb.util.ReturnCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        ObjectMapper objectMapping=new ObjectMapper();
        httpServletResponse.getWriter().write(objectMapping.writeValueAsString(Result.error().isSuccess(false)
                .code(ReturnCode.NOT_LOGIN.getCode())
                .message(ReturnCode.NOT_LOGIN.getMessage())));
    }
}
