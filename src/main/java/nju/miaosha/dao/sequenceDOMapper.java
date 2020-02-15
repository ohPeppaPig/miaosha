package nju.miaosha.dao;

import java.util.List;
import nju.miaosha.dataobject.sequenceDO;
import nju.miaosha.dataobject.sequenceDOExample;
import org.apache.ibatis.annotations.Param;

public interface sequenceDOMapper {
    long countByExample(sequenceDOExample example);

    int deleteByExample(sequenceDOExample example);

    int deleteByPrimaryKey(String name);

    int insert(sequenceDO record);

    int insertSelective(sequenceDO record);

    List<sequenceDO> selectByExample(sequenceDOExample example);

    sequenceDO selectByPrimaryKey(String name);
    sequenceDO getSequenceByName(String name);

    int updateByExampleSelective(@Param("record") sequenceDO record, @Param("example") sequenceDOExample example);

    int updateByExample(@Param("record") sequenceDO record, @Param("example") sequenceDOExample example);

    int updateByPrimaryKeySelective(sequenceDO record);

    int updateByPrimaryKey(sequenceDO record);
}