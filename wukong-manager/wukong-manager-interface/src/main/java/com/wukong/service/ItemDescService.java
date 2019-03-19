package com.wukong.service;

import java.util.ArrayList;

import com.wukong.common.pojo.WukongResult;

public interface ItemDescService {
	
	WukongResult getItemDescById(long itemId);
	int deleteItemDescByItemIdList(ArrayList<Long> list);
}
