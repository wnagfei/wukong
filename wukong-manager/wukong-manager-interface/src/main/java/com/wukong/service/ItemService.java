package com.wukong.service;

import java.util.List;

import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.pojo.TbItem;
import com.wukong.pojo.TbItemDesc;

public interface ItemService {
	
	TbItem geItemById(long itemId);
	EasyUIDataGridResult getItemList(int page,int rows);
	WukongResult addItem(TbItem item,String desc);
	TbItemDesc getTbItemDescById(long itemId);
	WukongResult deleteItems(List<Long> list);
	WukongResult instockItems(List<Long> list);
	WukongResult reshelfItems(List<Long> list);
	WukongResult updateItemAndDesc(TbItem item, String desc);
}
