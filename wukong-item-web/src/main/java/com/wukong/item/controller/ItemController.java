package com.wukong.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wukong.item.pojo.Item;
import com.wukong.pojo.TbItem;
import com.wukong.pojo.TbItemCat;
import com.wukong.pojo.TbItemDesc;
import com.wukong.service.ItemCatService;
import com.wukong.service.ItemService;

/**
 * 商品详情页面
 *
 */
@Controller
public class ItemController {

	@Resource
	private ItemService itemService;
	@Resource
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable Long itemId,Model model){
		//获取商品基本信息
		TbItem tbItem = itemService.geItemById(itemId);
		Item item = new Item(tbItem);
		//取商品详情
		TbItemDesc getTbItemDescById = itemService.getTbItemDescById(itemId);
		model.addAttribute("item",item);
		model.addAttribute("itemDesc",getTbItemDescById);
		return "item";
	}
	@RequestMapping("/item/cat/{cid}")
	@ResponseBody
	public TbItemCat getItemCat(@PathVariable Long cid) {
		TbItemCat itemCat = itemCatService.getItemCat(cid);
		return itemCat;
	}
}
