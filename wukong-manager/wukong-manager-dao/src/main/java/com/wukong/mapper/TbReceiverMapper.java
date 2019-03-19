package com.wukong.mapper;

import com.wukong.pojo.TbReceiver;
import com.wukong.pojo.TbReceiverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbReceiverMapper {
    int countByExample(TbReceiverExample example);

    int deleteByExample(TbReceiverExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbReceiver record);

    int insertSelective(TbReceiver record);

    List<TbReceiver> selectByExample(TbReceiverExample example);

    TbReceiver selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbReceiver record, @Param("example") TbReceiverExample example);

    int updateByExample(@Param("record") TbReceiver record, @Param("example") TbReceiverExample example);

    int updateByPrimaryKeySelective(TbReceiver record);

    int updateByPrimaryKey(TbReceiver record);
}