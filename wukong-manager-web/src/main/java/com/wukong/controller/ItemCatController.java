package com.wukong.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wukong.common.pojo.EasyUITreeNode;
import com.wukong.service.ItemCatService;

/**
 * 商品分类
 *
 */
@Controller
public class ItemCatController {
	@Resource
	private ItemCatService itemCatService;
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id",defaultValue="0") Long parentId){
		List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
		return list;
	}
}
