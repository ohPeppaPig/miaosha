package nju.miaosha.service;

import nju.miaosha.error.BusinessException;
import nju.miaosha.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telphone , String encrptPassword)throws BusinessException;

}
