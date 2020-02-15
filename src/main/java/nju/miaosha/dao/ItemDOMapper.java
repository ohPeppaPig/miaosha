package nju.miaosha.dao;

import java.util.List;
import nju.miaosha.dataobject.ItemDO;
import nju.miaosha.dataobject.ItemDOExample;
import org.apache.ibatis.annotations.Param;

public interface ItemDOMapper {
    long countByExample(ItemDOExample example);

    int deleteByExample(ItemDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    List<ItemDO> selectByExample(ItemDOExample example);

    ItemDO selectByPrimaryKey(Integer id);

    List<ItemDO> listItem();
    int updateByExampleSelective(@Param("record") ItemDO record, @Param("example") ItemDOExample example);

    int updateByExample(@Param("record") ItemDO record, @Param("example") ItemDOExample example);

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);

    int increaseSales(@Param("id")Integer id,@Param("amount") Integer amount );
}