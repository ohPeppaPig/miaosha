package nju.miaosha.response;

public class CommonReturnType {
    //返回处理结果：success and fail;
    private String status;
    //success：data返回前端需要的json
    //fail：错误码格式
    private Object data;


    public static CommonReturnType creat(Object result){
        return CommonReturnType.creat(result,"success");
    }
    public static CommonReturnType creat(Object result,String status){
        CommonReturnType Type = new CommonReturnType();
        Type.setStatus(status);
        Type.setData(result);
        return Type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
