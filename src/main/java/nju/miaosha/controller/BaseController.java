package nju.miaosha.controller;

import nju.miaosha.error.BusinessException;
import nju.miaosha.error.EmBusinessError;
import nju.miaosha.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    /**
     * 后端要消费的前端的数据类型
     */
    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

    // 定义exceptionhandler解决未被controller吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerEXception(HttpServletRequest Request, Exception ex){
        Map<String,Object> responseData = new HashMap<>();
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException) ex;

            responseData.put("errorCode",businessException.getErrCode());
            responseData.put("errorMsg",businessException.getErrMsg());
            return CommonReturnType.creat(responseData,"fail");
        }else {
            responseData.put("errorCode", EmBusinessError.UNKNOWN_CODE.getErrCode());
            responseData.put("errorMsg",EmBusinessError.UNKNOWN_CODE.getErrMsg());
            return CommonReturnType.creat(responseData,"fail");
        }
    }
}
