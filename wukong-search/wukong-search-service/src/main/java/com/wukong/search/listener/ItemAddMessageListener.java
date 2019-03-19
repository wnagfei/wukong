
package com.wukong.search.listener;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;

import com.wukong.common.pojo.SearchItem;
import com.wukong.search.mapper.SearchItemMapper;

/**
 * 监听商品添加事件，同步索引库
 * 
 */
public class ItemAddMessageListener implements MessageListener {
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
	@Resource
	private SearchItemMapper searchItemMapper;
	private TransportClient client = null;
	private PreBuiltTransportClient preBuiltTransportClient;

	@Override
	public void onMessage(Message message) {
		try {
			preBuiltTransportClient = new PreBuiltTransportClient(Settings.EMPTY);

			client = preBuiltTransportClient
					.addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT));
			BulkRequestBuilder requestBuilder = client.prepareBulk();
			// 从消息中取商品id
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			long itemId = Long.parseLong(text);
			// 根据商品id查询数据，取商品信息
			// 等待事务提交
			Thread.sleep(1000);
			SearchItem searchItem = searchItemMapper.getItemById(itemId);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
