package com.wukong.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.content.service.ContentService;
import com.wukong.pojo.TbContent;

/**
 * 内容管理
 *
 */
@Controller
public class ContentController {
	
	@Resource
	private ContentService contentService;
	
	@RequestMapping("/content/save")
	@ResponseBody
	public WukongResult addContent(TbContent content){
		
		WukongResult addContent = contentService.addContent(content);
		return addContent;
	}
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContents(long categoryId, Integer page,Integer rows){
		EasyUIDataGridResult result;
		if(categoryId != 0){
			result = contentService.getContentsByCategoryId(categoryId, page, rows);
		}else{
			result = contentService.getContents(page, rows);
		}
		
		return result;
	}
	
	/**
	 * 前端显示要求返回一个WukongResult对象，只需要status属性所以return WukongResult.ok();就行
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	public WukongResult deleteContents(@RequestParam("ids") String ids) {
		String[] split = ids.split(",");
	    ArrayList<Long> list = new ArrayList<Long>();
	    for(String id:split) {
	    	
	    	list.add(Long.parseLong(id));
	    }
	    
	    contentService.deleteContents(list);
	   
	    return WukongResult.ok();
	}
	
	
	@RequestMapping("/content/edit")
	@ResponseBody
	public WukongResult editContent(TbContent content) {
		WukongResult result = contentService.updateContent(content);
		return result;
	}
	
}
