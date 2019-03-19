package com.wukong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wukong.common.pojo.WukongResult;
import com.wukong.service.ItemDescService;

@Controller
public class ItemDescController {

	@Autowired
	ItemDescService itemDescService;
	
	@RequestMapping("/item/query/item/desc/{itemId}")
	@ResponseBody
	public WukongResult getItemDesc(@PathVariable Long itemId) {

		WukongResult result = itemDescService.getItemDescById(itemId);
		return result;
	}
}
