package com.wukong.search.service;

import com.wukong.common.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String queryString,int page,int rows);
}
