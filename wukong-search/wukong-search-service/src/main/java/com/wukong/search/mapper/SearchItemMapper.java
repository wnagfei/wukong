package com.wukong.search.mapper;

import java.util.List;

import com.wukong.common.pojo.SearchItem;

public interface SearchItemMapper {

	 List<SearchItem> getItemList();
	 SearchItem getItemById(long itemId);
}
