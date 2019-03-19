package com.wukong.controller;


import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * @author yuanbao
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.pojo.TbItem;
import com.wukong.service.ItemDescService;
import com.wukong.service.ItemParamService;
import com.wukong.service.ItemService;
@Controller
public class ItemController {
	@Resource
	private ItemService itemService;
	@Resource
	private ItemDescService itemDescService;
	@Resource
	private ItemParamService itemParamService;
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId){
		TbItem tbItem = itemService.geItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public WukongResult addItem(TbItem item,String desc){
		WukongResult result = itemService.addItem(item, desc);
		return result;
	}
	
	/**
	 * 可以同时删除多条，同时删除对应的tb_item_param_item，tb_item_desc
	 * 前端页面要求返回WukongResult对象
	 * @param ids
	 */
	@RequestMapping("/item/delete")
	@ResponseBody
	public WukongResult deleteItems(@RequestParam("ids") String ids) {
		String[] split = ids.split(",");
	    ArrayList<Long> list = new ArrayList<Long>();
	    for(String id:split) {
	    	list.add(Long.parseLong(id));
	    }
	    
	    itemService.deleteItems(list);
	    itemDescService.deleteItemDescByItemIdList(list);
	    itemParamService.deleteItemParamByItemIdList(list);
	    return WukongResult.ok();
	}
	
	@RequestMapping("/item/instock")
	@ResponseBody
	public WukongResult instockItems(@RequestParam("ids") String ids) {
		
		String[] split = ids.split(",");
	    ArrayList<Long> list = new ArrayList<Long>();
	    for(String id:split) {
	    	list.add(Long.parseLong(id));
	    }
	    
	    itemService.instockItems(list);
		return WukongResult.ok();
	}
	
	@RequestMapping("/item/reshelf")
	@ResponseBody
	public WukongResult reshelfItems(@RequestParam("ids") String ids) {
		
		String[] split = ids.split(",");
	    ArrayList<Long> list = new ArrayList<Long>();
	    for(String id:split) {
	    	list.add(Long.parseLong(id));
	    }
	    
	    itemService.reshelfItems(list);
		return WukongResult.ok();
	}
	
	/**
	 * 这里类目、规格应该也能修改
	 * 后期再加，即多传几个参数
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("/item/update")
	@ResponseBody
	public WukongResult updateItem(TbItem item, String desc) {
		itemService.updateItemAndDesc(item, desc);
		return WukongResult.ok();
	}
}
