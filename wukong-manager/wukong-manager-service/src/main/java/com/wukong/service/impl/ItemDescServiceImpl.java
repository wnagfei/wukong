package com.wukong.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wukong.common.pojo.WukongResult;
import com.wukong.mapper.TbItemDescMapper;
import com.wukong.pojo.TbItemDesc;
import com.wukong.pojo.TbItemDescExample;
import com.wukong.pojo.TbItemDescExample.Criteria;
import com.wukong.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService{

	@Resource
	TbItemDescMapper itemDescMapper;
	
	@Override
	public WukongResult getItemDescById(long itemId) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		return WukongResult.ok(itemDesc);
	}

	//删除商品的同时删除商品规格参数信息
	@Override
	public int deleteItemDescByItemIdList(ArrayList<Long> list) {
		TbItemDescExample example = new TbItemDescExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdIn(list);
		int count = itemDescMapper.deleteByExample(example);
		return count;
	}
}
