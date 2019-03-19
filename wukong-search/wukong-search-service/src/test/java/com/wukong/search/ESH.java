package com.wukong.search;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.wukong.common.pojo.SearchItem;
import com.wukong.common.pojo.SearchResult;

/***
 * 查询索引库
 * 
 * @author yuanbao
 *
 */
@Repository
public class ESH {
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

	@Test
	public void search() {
		QueryBuilder queryBuilder = QueryBuilders.termQuery("item_title", "手机");
		int page = 1;
		int rows = 60;
		
		
		
		/******************************************/
		
		
		
		
		
		
		// 获取客户端对象

		preBuiltTransportClient = new PreBuiltTransportClient(Settings.EMPTY);

		TransportClient client = null;
		try {
			client = preBuiltTransportClient
					.addTransportAddress(new TransportAddress(InetAddress.getByName("39.107.109.138"), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HighlightBuilder hBuilder = new HighlightBuilder();
		hBuilder.preTags("<font color='red'>");
		hBuilder.postTags("</font>");
		hBuilder.field("item_title");
		

		// 根据query进行查询

		SearchResponse response = client.prepareSearch("forsearch").setTypes("item").highlighter(hBuilder).setQuery(queryBuilder).setFrom(page).setSize(rows).get();
		SearchHits hits = response.getHits();
		// System.out.println("数据总数 ： " + hits.totalHits);

		long numFound = hits.totalHits;

		SearchResult result = new SearchResult();
		result.setRecordCount(numFound);
		List<SearchItem> items = new ArrayList<>();
		for (SearchHit hit : hits) {

			// String user = (String) hit.getSourceAsMap().get("user");
			SearchItem item = new SearchItem();

			item.setCategory_name((String) hit.getSourceAsMap().get("item_category_name"));
			item.setId((String) hit.getSourceAsMap().get("id"));
			String image = (String) hit.getSourceAsMap().get("item_image");
			if (StringUtils.isNotBlank(image)) {
				image = image.split(",")[0];
			}
			item.setImage(image);
			item.setPrice((Integer)hit.getSourceAsMap().get("item_price"));
			item.setSell_point((String) hit.getSourceAsMap().get("item_sell_point"));
			
			
			
			/*************************/
			
			
			
			
			/**************************/
			

			// 获取高亮显示
			//Text[] text = hit.getHighlightFields().get("item_title").getFragments();
			
			
	
			
			/*String title = "";
			if (text != null) {
				title = text.toString();
			} else {
				title = (String) hit.getSourceAsMap().get("item_title");
			}*/
			 /*for(String key : map.keySet()){
			   String value = map.get(key);
			   System.out.println(key+"  "+value);
			  }*/
			
			
			item.setTitle( hit.getHighlightFields().toString());
			items.add(item);
			
			Text[] text = hit.getHighlightFields().get("item_title").getFragments();
            for (Text str : text) {
                System.out.println(str.string());
            }
			
		}
		client.close();
		long pages = numFound / rows;
		if(numFound % rows >0){
			pages +=1;
		}
		result.setTotalPages(pages);
		result.setItemList(items);
		result.setRecordCount(numFound);
	//	return result;

	}
}
