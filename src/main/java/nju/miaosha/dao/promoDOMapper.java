package nju.miaosha.dao;

import java.util.List;
import nju.miaosha.dataobject.promoDO;
import nju.miaosha.dataobject.promoDOExample;
import org.apache.ibatis.annotations.Param;

public interface promoDOMapper {
    long countByExample(promoDOExample example);

    int deleteByExample(promoDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(promoDO record);


    int insertSelective(promoDO record);

    List<promoDO> selectByExample(promoDOExample example);

    promoDO selectByPrimaryKey(Integer id);
    promoDO selectByItemId(Integer id);

    int updateByExampleSelective(@Param("record") promoDO record, @Param("example") promoDOExample example);

    int updateByExample(@Param("record") promoDO record, @Param("example") promoDOExample example);

    int updateByPrimaryKeySelective(promoDO record);

    int updateByPrimaryKey(promoDO record);
}