package com.fsnteam.fsnweb.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共返回结果
 */

@Data
public class Result {

    @ApiModelProperty(value = "是否成功")
    private Boolean isSuccess;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data =new HashMap<>();

    private Result(){

    }

    public static Result success(){
        Result result = new Result();
        result.setIsSuccess(true);
        result.setCode(ReturnCode.SUCCESS.getCode());
        result.setMessage(ReturnCode.SUCCESS.getMessage());
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.setIsSuccess(false);
        result.setCode(ReturnCode.SERVERERROR.getCode());
        result.setMessage(ReturnCode.SERVERERROR.getMessage());
        return result;
    }

    public static Result arithmeticException(){
        Result result = new Result();
        result.setIsSuccess(false);
        result.setCode(ReturnCode.ARITHMETIC_EXCEPTION.getCode());
        result.setMessage(ReturnCode.ARITHMETIC_EXCEPTION.getMessage());
        return result;
    }

    public Result isSuccess(Boolean isSuccess){
        this.setIsSuccess(isSuccess);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
