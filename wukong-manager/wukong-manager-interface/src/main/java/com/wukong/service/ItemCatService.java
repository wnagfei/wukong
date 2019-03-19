package com.wukong.service;

import java.util.List;

import com.wukong.common.pojo.EasyUITreeNode;
import com.wukong.pojo.TbItemCat;

public interface ItemCatService {

	List<EasyUITreeNode> getItemCatList(long parentId);
	TbItemCat getItemCat(long cid);
}
