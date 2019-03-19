package com.wukong.search.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wukong.common.pojo.SearchResult;
import com.wukong.search.service.SearchService;

/**
 * 搜索服务
 *
 */
@Controller
public class SearchController {
	
	@Resource
	SearchService searchService;
	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString,@RequestParam(defaultValue="1")Integer page,Model model){
		try {
			queryString = new String(queryString.getBytes("ISO-8859-1"),"UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SearchResult searchResult = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);
		return "search";
	}
}
