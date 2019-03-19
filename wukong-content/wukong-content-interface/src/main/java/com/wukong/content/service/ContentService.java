package com.wukong.content.service;

import java.util.List;

import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.pojo.TbContent;

public interface ContentService {

	WukongResult addContent(TbContent content);
	EasyUIDataGridResult getContents(int page,int rows);
	EasyUIDataGridResult getContentsByCategoryId(long categoryId,int page,int rows);
	List<TbContent> getContentsByCId(long cid);
	
	WukongResult  deleteContents(List<Long> list);
	
	WukongResult updateContent(TbContent content);
	
	WukongResult deleteContentByCid(long cid);
	
}
