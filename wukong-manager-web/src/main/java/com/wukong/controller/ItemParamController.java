package com.wukong.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.service.ItemParamService;
/**
 * 显示商品
 *
 */
@Controller
public class ItemParamController {
	
	@Resource
	private ItemParamService itemParamService;
	
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDataGridResult getParamList(Integer page,Integer rows){
		
		EasyUIDataGridResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}
	
	@RequestMapping("/item/param/item/query/{itemId}")
	@ResponseBody
	public WukongResult getItemParam(@PathVariable Long itemId) {
		WukongResult result = itemParamService.getItemParamItemByItemId(itemId);
		return result;
	}
}
