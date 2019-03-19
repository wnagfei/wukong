package com.wukong.content.service;

import java.util.List;

import com.wukong.common.pojo.EasyUITreeNode;
import com.wukong.common.pojo.WukongResult;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCategoryList(long parentId);
	WukongResult addContentCateGory(long parentId,String name);
	WukongResult updateContentCateGory(long id,String name);
	WukongResult deleteContentCategory(Long id);
	WukongResult getContentCategoryById(long id);
}
