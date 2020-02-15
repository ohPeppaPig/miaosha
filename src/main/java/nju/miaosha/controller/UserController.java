package nju.miaosha.controller;

import nju.miaosha.controller.viewObject.UserVO;
import nju.miaosha.error.BusinessException;
import nju.miaosha.error.EmBusinessError;
import nju.miaosha.response.CommonReturnType;
import nju.miaosha.service.UserService;
import nju.miaosha.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Transactional
    @PostMapping(value = "/register", consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    //用户注册接口
    public CommonReturnType register(@RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name = "otpCode")String otpCode,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "gender")Integer gender,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otpcode
//        String insessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
//        if (com.alibaba.druid.util.StringUtils.equals(otpCode,insessionOtpCode)){//内部做了判断空的处理
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码出错");
//
//        }
//        //用户的注册流程
//        UserModel userModel = new UserModel();
//        userModel.setName(name);
//        userModel.setAge(age);
//        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
//        userModel.setTelphone(telphone);
//        userModel.setRegisterMode("byPhone");
//        userModel.setEncrptPassword(this.encodeByMD5(password));
//        userService.register(userModel);
//        return CommonReturnType.creat(null);
        //验证手机号和对应的otp验证码一致
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if (!com.alibaba.druid.util.StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "验证码输入有误");
        }
        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byPhone");
        userModel.setEncrptPassword(this.encodeByMD5(password));
        userService.register(userModel);
        return CommonReturnType.creat(null);

    }

    @ResponseBody
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone){
        Random random = new Random();
        int rand = random.nextInt(99999);
        rand += 10000;
        String otpCode = String.valueOf(rand);

        //将otp验证码同对应用户的手机号关联，使用httpsession方式绑定
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        System.out.println(otpCode);
        return CommonReturnType.creat(null);
    }


    @PostMapping(value = "/login", consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone")String telphone,
                                  @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if (com.alibaba.druid.util.StringUtils.isEmpty(telphone)|| com.alibaba.druid.util.StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //调用登录服务
        UserModel userModel = userService.validateLogin(telphone,this.encodeByMD5(password));
        //将登录凭证加入到用户登录成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.creat(null);

    }

    @ResponseBody
    @RequestMapping("/get")
    public CommonReturnType getUser(@RequestParam(name="id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id);
        //核心领域模型对象组转化为VO
        //若获取的对应信息不存在
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = convertFromModel(userModel);
        return CommonReturnType.creat(userVO);
    }

    private UserVO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

    private String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // 加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }
}
