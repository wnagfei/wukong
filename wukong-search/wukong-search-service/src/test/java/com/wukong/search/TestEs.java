package com.wukong.search;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

public class TestEs {
	private TransportClient client = null;
	public final static String HOST = "39.107.109.138";
	public final static int PORT = 9300; // http请求的端口是9200，客户端是9300

	private static String INDEX = "forsearch";
	private static String TYPE = "item";
	private PreBuiltTransportClient preBuiltTransportClient;

	@Test
	public void testEs() throws UnknownHostException {
		
		preBuiltTransportClient = new PreBuiltTransportClient(Settings.EMPTY);
		System.out.println(HOST);
		client = preBuiltTransportClient.addTransportAddresses(new TransportAddress(InetAddress.getByName("192.168.200.131"), PORT));


		BulkRequestBuilder requestBuilder = client.prepareBulk();
		Map<String, Object> map = new HashMap<>();

		map.put("item_title", "中华人民共和国");
		map.put("item_price", 900);

		IndexRequest request = client.prepareIndex(INDEX, TYPE, "1003").setSource(map).request();
		requestBuilder.add(request);
		BulkResponse bulkResponse = requestBuilder.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.println("失败");
		}
		
		System.out.println("成功");

		/*DeleteResponse actionGet = client.prepareDelete(INDEX, TYPE, "1001").execute().actionGet();
		System.out.println(actionGet.toString());*/
		
		client.close();
	}
	
	public static void main(String[] args) {
		System.out.println(123);
	}
	
	

}
