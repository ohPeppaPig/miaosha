package nju.miaosha.dao;

import java.util.List;
import nju.miaosha.dataobject.ItemStockDO;
import nju.miaosha.dataobject.ItemStockDOExample;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;

public interface ItemStockDOMapper {
    long countByExample(ItemStockDOExample example);

    int deleteByExample(ItemStockDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    List<ItemStockDO> selectByExample(ItemStockDOExample example);

    ItemStockDO selectByPrimaryKey(Integer id);

    ItemStockDO selectByItemId(Integer id);

    int decreaseStock(@Param("itemId")Integer itemId,@Param("amount")Integer amount );

    int updateByExampleSelective(@Param("record") ItemStockDO record, @Param("example") ItemStockDOExample example);

    int updateByExample(@Param("record") ItemStockDO record, @Param("example") ItemStockDOExample example);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);
}