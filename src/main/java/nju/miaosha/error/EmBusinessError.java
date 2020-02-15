package nju.miaosha.error;

public enum EmBusinessError implements CommonError {
    //通用错误类型00001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),

    //20000开头为用户信息错误类型
    USER_NOT_EXIST(20001,"用户不存在"),

    UNKNOWN_CODE(10000,"未知信息错误"),
    LOGIN_FAIL(10001,"登录失败"),
    USER_NOT_LOGIN(20003,"用户还未登录"),
    STOCK_NOT_ENOUGH(10002,"无货存")
    ;

    private int errCode;
    private String errMsg;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    //改动错误类型
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
