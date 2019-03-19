package com.wukong.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wukong.common.pojo.EasyUITreeNode;
import com.wukong.common.pojo.WukongResult;
import com.wukong.content.service.ContentCategoryService;

/**
 * 内容分类管理
 *
 */
@Controller
public class ContentCategoryController {

	@Resource
	private ContentCategoryService contentCategoryService;
	
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") long parentId) {

		List<EasyUITreeNode> contentCategoryList = contentCategoryService.getContentCategoryList(parentId);
		return contentCategoryList;
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public WukongResult addContentCategory(long parentId,String name){
		WukongResult addContentCateGory = contentCategoryService.addContentCateGory(parentId, name);
		return addContentCateGory;
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public WukongResult updateContentCateGory(long id, String name) {
		WukongResult result = contentCategoryService.updateContentCateGory(id, name);
		return result;
	}
	
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public void deleteContentCategory(Long id) {
		contentCategoryService.deleteContentCategory(id);
	}
	@RequestMapping("/content/category/isParent")
	@ResponseBody
	public WukongResult judgmentIsParent(Long id) {
		WukongResult result = contentCategoryService.getContentCategoryById(id);
		return result;
	}
}
