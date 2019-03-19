package com.wukong.search.service.impl;

import javax.annotation.Resource;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.stereotype.Service;

import com.wukong.common.pojo.SearchResult;
import com.wukong.search.dao.SearchDao;
import com.wukong.search.service.SearchService;
/***
 * 
 *搜索功能实现
 *
 */
@Service
public class SearchServiceImpl implements SearchService {
	
	@Resource
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) {
		// TODO Auto-generated method stub
		
		
		//创建QueryBuild
		//QueryBuilder queryBuilder = QueryBuilders.termQuery("item_title", queryString);
		//QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("item_title", queryString);
		//QueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("item_title", queryString);
		//可以匹配分词
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("item_title", queryString);
		//设置分页条件
		if(page < 1) page = 1;
		if(rows < 1) rows = 60;

		
		SearchResult result = searchDao.search(queryBuilder, page, rows);
		//返回结果
		return result;
	}

}
