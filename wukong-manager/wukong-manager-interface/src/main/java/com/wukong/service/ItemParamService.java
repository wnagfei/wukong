package com.wukong.service;


import java.util.ArrayList;

import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.pojo.TbItemParamItem;

public interface ItemParamService {
	EasyUIDataGridResult getItemParamList(int page, int rows);
	WukongResult getItemParamItemByItemId(long itemId);
	TbItemParamItem getItemParamItemByItemId2(long itemId);
	WukongResult deleteItemParamByItemIdList(ArrayList<Long> list);
}
