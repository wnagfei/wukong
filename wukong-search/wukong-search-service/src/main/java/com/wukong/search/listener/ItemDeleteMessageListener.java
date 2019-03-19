package com.wukong.search.listener;

import java.net.InetAddress;
import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import com.wukong.search.mapper.SearchItemMapper;

/**
 * 监听商品删除事件，同步索引库
 * 
 */
public class ItemDeleteMessageListener implements MessageListener {
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
			// 从消息中取商品id
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			//long itemId = Long.parseLong(text);
			
			//根据id删除索引信息
			DeleteResponse dResponse =
					client.prepareDelete(INDEX, TYPE, text).execute().actionGet();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
