package com.wukong.service;

import com.wukong.common.pojo.WukongResult;
import com.wukong.pojo.TbCart;

public interface CartService {
	TbCart getCartByUidAndItemId(long userId, long itemId);
	WukongResult addCart(TbCart tbCart);
	void updateCart(TbCart tbCart);
}
