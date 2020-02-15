package nju.miaosha.service;

import nju.miaosha.error.BusinessException;
import nju.miaosha.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;

}
