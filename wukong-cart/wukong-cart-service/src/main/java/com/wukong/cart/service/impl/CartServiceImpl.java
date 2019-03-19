package com.wukong.cart.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wukong.cart.service.CartService;
import com.wukong.common.pojo.WukongResult;
import com.wukong.mapper.TbCartMapper;
import com.wukong.pojo.TbCart;
import com.wukong.pojo.TbCartExample;
import com.wukong.pojo.TbCartExample.Criteria;
/**
 *购物车管理
 */
@Service
public class CartServiceImpl implements CartService {
	
	@Resource
	private TbCartMapper cartMapper;

	@Override
	public WukongResult addCart(TbCart cart) {
		// TODO Auto-generated method stub
		cartMapper.insert(cart);
		return WukongResult.ok();
	}

	@Override
	public WukongResult deleteCartByUserId(long userId) {
		// TODO Auto-generated method stub
		TbCartExample example = new TbCartExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUserIdEqualTo(userId);
		cartMapper.deleteByExample(example);
		return WukongResult.ok();
	}

	@Override
	public List<TbCart> getCartByUserId(long userId) {
		// TODO Auto-generated method stub
		TbCartExample example = new TbCartExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<TbCart> selectByExample = cartMapper.selectByExample(example);
		return selectByExample;
	}

	@Override
	public WukongResult updateCart(TbCart cart) {
		// TODO Auto-generated method stub
		cart.setUpdated(new Date());
		cartMapper.updateByPrimaryKey(cart);
		return WukongResult.ok();
	}

	@Override
	public WukongResult deleteById(long id) {
		cartMapper.deleteByPrimaryKey(id);
		return WukongResult.ok();
	}

	@Override
	public TbCart geTbCartByUserIdANDItemId(long userId, long itemId) {
		// TODO Auto-generated method stub
		TbCartExample example = new TbCartExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUserIdEqualTo(userId);
		createCriteria.andItemIdEqualTo(itemId);
		List<TbCart> selectByExample = cartMapper.selectByExample(example);
		return selectByExample.get(0);
	}

	@Override
	public WukongResult deleteCartByUserIdANDItemId(long userId, long itemId) {
		// TODO Auto-generated method stub
		TbCartExample example = new TbCartExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUserIdEqualTo(userId);
		createCriteria.andItemIdEqualTo(itemId);
		cartMapper.deleteByExample(example);
		return WukongResult.ok();
	}



}
