package com.wukong.cart.service;

import java.util.List;

import com.wukong.common.pojo.WukongResult;
import com.wukong.pojo.TbCart;

public interface CartService {

	WukongResult addCart(TbCart cart);
	WukongResult deleteCartByUserId(long userId);
	List<TbCart> getCartByUserId(long userId);
	WukongResult updateCart(TbCart cart);
	WukongResult deleteById(long id);
	TbCart geTbCartByUserIdANDItemId(long userId,long itemId);
	WukongResult deleteCartByUserIdANDItemId(long userId,long itemId);
}
