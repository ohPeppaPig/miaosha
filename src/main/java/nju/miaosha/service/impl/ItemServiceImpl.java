package nju.miaosha.service.impl;

import nju.miaosha.dao.ItemDOMapper;
import nju.miaosha.dao.ItemStockDOMapper;
import nju.miaosha.dataobject.ItemDO;
import nju.miaosha.dataobject.ItemStockDO;
import nju.miaosha.error.BusinessException;
import nju.miaosha.error.EmBusinessError;
import nju.miaosha.service.ItemService;
import nju.miaosha.service.model.ItemModel;
import nju.miaosha.service.model.PromoModel;
import nju.miaosha.service.promoService;
import nju.miaosha.validator.ValidationResult;
import nju.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private ItemDOMapper itemDOMapper;
    @Autowired
    private ItemStockDOMapper itemStockMapper;
    @Autowired
    private promoService promoService;

    @Override
    @Transactional
    public ItemModel creatItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasErrors()){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());

        }
        //转化model
        ItemDO item = this.convertItemFromItemModel(itemModel);
        //写入数据库
        itemDOMapper.insertSelective(item);
        itemModel.setId(item.getId());
        ItemStockDO itemStock = this.convertItemStockFromItemModel(itemModel);
        itemStockMapper.insertSelective(itemStock);
        //返回对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemList = itemDOMapper.listItem();
        List<ItemModel> itemModelList = itemList.stream().map(item -> {
            ItemStockDO itemStock = itemStockMapper.selectByItemId(item.getId());
            ItemModel itemModel = this.convertFromDO(item, itemStock);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO item = itemDOMapper.selectByPrimaryKey(id);
        if (item == null) {
            return null;
        }
        ItemStockDO itemStock = itemStockMapper.selectByItemId(item.getId());
        ItemModel itemModel = convertFromDO(item, itemStock);

        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if (promoModel != null && promoModel.getStatus().intValue() != 3) {
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        int affectedRow = itemStockMapper.decreaseStock(itemId, amount);
        if (affectedRow > 0) {
            //更新库存成功
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDOMapper.increaseSales(itemId, amount);
    }

    private ItemDO convertItemFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemDO item = new ItemDO();
        BeanUtils.copyProperties(itemModel, item);
        //数据库中price是double类型的，ItemModel中是BigDecimal，避免类型转化时出现精度丢失
        item.setPrice(itemModel.getPrice().doubleValue());
        return item;
    }

    private ItemStockDO convertItemStockFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStockDO itemStock = new ItemStockDO();
        itemStock.setItemId(itemModel.getId());
        itemStock.setStock(itemModel.getStock());
        return itemStock;
    }

    private ItemModel convertFromDO(ItemDO item, ItemStockDO itemStock) {
        if (item == null) {
            return null;
        }
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(item, itemModel);

        //从数据库data向item model转的时候也要注意price的类型问题
        itemModel.setPrice(new BigDecimal(item.getPrice()));
        itemModel.setStock(itemStock.getStock());

        return itemModel;
    }
}



