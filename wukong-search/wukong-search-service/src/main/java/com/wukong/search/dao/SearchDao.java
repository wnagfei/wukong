package com.wukong.search.dao;

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
public class SearchDao {
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

	public SearchResult search(QueryBuilder queryBuilder, int page, int rows) {

		// 获取客户端对象
		preBuiltTransportClient = new PreBuiltTransportClient(Settings.EMPTY);

		try {
			client = preBuiltTransportClient
					.addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 设置高亮
		HighlightBuilder hBuilder = new HighlightBuilder();
		hBuilder.preTags("<font color='red'>");
		hBuilder.postTags("</font>");
		hBuilder.field("item_title");

		// 根据query进行查询
		int start = (page - 1 ) * 60;
		SearchResponse response = client.prepareSearch(INDEX)
										.setTypes(TYPE).highlighter(hBuilder)//搜索时高亮
										.setQuery(queryBuilder)//根据字段查询
										.setFrom(start)//从第几条开始
										.setSize(rows)//一共查询多少
										.get();
	
		SearchHit[] hits = response.getHits().getHits();

		long numFound = hits.length;

		SearchResult result = new SearchResult();
		result.setRecordCount(numFound);
		List<SearchItem> items = new ArrayList<>();
		for (SearchHit hit : hits) {
			SearchItem item = new SearchItem();
			item.setCategory_name((String) hit.getSourceAsMap().get("item_category_name"));
			item.setId(hit.getId());
			String image = (String) hit.getSourceAsMap().get("item_image");
			if (StringUtils.isNotBlank(image)) {
				image = image.split(",")[0];
			}
			item.setImage(image);
			item.setPrice((Integer) hit.getSourceAsMap().get("item_price"));
			item.setSell_point((String) hit.getSourceAsMap().get("item_sell_point"));

			// 获取高亮显示

			Text[] text = null;
			String title = "";
			if (hit.getHighlightFields().get("item_title").getFragments() != null) {
				text = hit.getHighlightFields().get("item_title").getFragments();
				for (Text str : text) {
					title += str;
				}
			} else {
				title = (String) hit.getSourceAsMap().get("item_title");
			}
			
			item.setTitle(title);
			items.add(item);
		}
		client.close();
		long pages = numFound / rows;
		if (numFound % rows > 0) {
			pages += 1;
		}
		result.setTotalPages(pages);
		result.setItemList(items);
		result.setRecordCount(numFound);
		return result;

	}
}
