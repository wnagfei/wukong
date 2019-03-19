package com.wukong.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.wukong.common.pojo.WukongResult;
import com.wukong.mapper.TbCartMapper;
import com.wukong.pojo.TbCart;
import com.wukong.pojo.TbCartExample;
import com.wukong.pojo.TbCartExample.Criteria;
import com.wukong.service.CartService;

@Service
public class CartServiceImpl implements CartService{
	@Resource
	private TbCartMapper tbCartMapper;
	
	@Override
	public TbCart getCartByUidAndItemId(long userId, long itemId) {
		TbCartExample example = new TbCartExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andItemIdEqualTo(itemId);
		
		List<TbCart> list = tbCartMapper.selectByExample(example);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public WukongResult addCart(TbCart tbCart) {
		tbCartMapper.insert(tbCart);
		return WukongResult.ok();
	}

	@Override
	public void updateCart(TbCart tbCart) {
		tbCartMapper.updateByPrimaryKeySelective(tbCart);
	}

}
