package com.wukong.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
/**
 * 导入索引库
 * @author yuanbao
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wukong.common.pojo.WukongResult;
import com.wukong.search.service.SearchItemService;
@Controller
public class IndexManagerController {

	@Resource
	private SearchItemService searchItemService;
	@RequestMapping("/index/import")
	@ResponseBody
	public WukongResult importIndex(){
		WukongResult importItemsToIndex = searchItemService.importItemsToIndex();
		return importItemsToIndex;
	}
}
