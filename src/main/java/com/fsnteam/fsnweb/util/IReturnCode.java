package com.fsnteam.fsnweb.util;

public interface IReturnCode {
    /**
     * 获取错误状态码
     * @return
     */
    Integer getCode();

    /**
     * 获取错误信息
     */
    String getMessage();
}
