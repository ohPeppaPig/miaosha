package nju.miaosha.service;

import nju.miaosha.error.BusinessException;
import nju.miaosha.service.model.ItemModel;

import java.util.List;

public interface ItemService {
    ItemModel creatItem(ItemModel itemModel) throws BusinessException;
    List<ItemModel> listItem();
    ItemModel getItemById(Integer id);
    boolean decreaseStock(Integer itemId,Integer amount) throws BusinessException;

    void increaseSales(Integer itemId,Integer amount) throws BusinessException;


}
