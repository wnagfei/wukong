package com.wukong.search.service.impl;


import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wukong.common.pojo.SearchItem;
import com.wukong.common.pojo.WukongResult;
import com.wukong.search.mapper.SearchItemMapper;
import com.wukong.search.service.SearchItemService;

/**
 * 将商品数据导入索引
 * 
 * @author yuanbao
 *
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Resource
	private SearchItemMapper searchItemMapper;
	// es地址
	@Value("${HOST}")
	private String HOST;
	// 端口
	@Value("${PORT}")
	private int PORT;
	// 索引
	@Value("${INDEX}")
	private String INDEX;
	// 类型
	@Value("${TYPE}")
	private String TYPE;

	private TransportClient client = null;
	private PreBuiltTransportClient preBuiltTransportClient;
	@Override
	public WukongResult importItemsToIndex() {
		// TODO Auto-generated method stub
		
		// 遍历商品添加到索引库
		// 准备创建客户端 没有集群用Settings.EMPTY
		preBuiltTransportClient = new PreBuiltTransportClient(Settings.EMPTY);
		try {
			// 创建客户端对象
			client = preBuiltTransportClient
					.addTransportAddresses(new TransportAddress(InetAddress.getByName(HOST), PORT));
			
			BulkRequestBuilder requestBuilder = client.prepareBulk();
			// 查询所有商品数据
			List<SearchItem> itemList = searchItemMapper.getItemList();
			for (SearchItem searchItem : itemList) {

				Map<String, Object> map = new HashMap<>();

				map.put("item_title", searchItem.getTitle());
				map.put("item_sell_point", searchItem.getSell_point());
				map.put("item_price", searchItem.getPrice());
				map.put("item_image", searchItem.getImage());
				map.put("item_category_name", searchItem.getCategory_name());
				map.put("item_desc", searchItem.getItem_desc());
				IndexRequest request = client.prepareIndex(INDEX, TYPE, searchItem.getId()).setSource(map).request();
				requestBuilder.add(request);

				// 提交数据

				BulkResponse bulkResponse = requestBuilder.execute().actionGet();
				if (bulkResponse.hasFailures()) {
					return WukongResult.build(500, "导入失败");
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return WukongResult.build(500, "导入失败");
		}
		return WukongResult.ok();
	}

}
