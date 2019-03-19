package com.wukong.pagehelper;


import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import com.wukong.mapper.TbItemCatMapper;
import com.wukong.pojo.TbItemCat;
import com.wukong.pojo.TbItemCatExample;
import com.wukong.pojo.TbItemCatExample.Criteria;

public class TestKafka {
	@Resource TbItemCatMapper catMapper;
	@Test
	public void testQueueProdecer()throws Exception{
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.95.202.32:61616");
		
		//创建连接对象
		Connection connection = connectionFactory.createConnection();
		
		//开启连接，调用connection 对象的start方法
		connection.start();
		//使用connection常见session对象
		//第一个参数是否开始事务，一般不使用事务。保证数据最终一致，可以用消息队列实现
		//如果第一个参数为true，第二个自动忽略，如果不开启事务，第二个为自动应答就可以
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//使用session对象创建一个destination对象，
		Queue queue = session .createQueue("test-queue");
		//使用session创建producer对象
		MessageProducer producer = session.createProducer(queue);
		//创建一个textMessage对象
		TextMessage textMessage = session.createTextMessage("hello acticemp");
		//发送消息
		producer.send(textMessage);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	@Test
	public void testQueueConsumer() throws Exception {
		//创建一个连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.95.202.32:61616");
		//使用连接工厂对象创建一个连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//使用连接对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//使用Session创建一个Destination，Destination应该和消息的发送端一致。
		Queue queue = session.createQueue("test-queue");
		//使用Session创建一个Consumer对象
		MessageConsumer consumer = session.createConsumer(queue);
		//向Consumer对象中设置一个MessageListener对象，用来接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//取消息的内容
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						String text = textMessage.getText();
						//打印消息内容
						System.out.println(text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		//系统等待接收消息
		/*while(true) {
			Thread.sleep(100);
		}*/
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	
	@Test
	public void testTopicProdecer()throws Exception{
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.95.202.32:61616");
		
		//创建连接对象
		Connection connection = connectionFactory.createConnection();
		
		//开启连接，调用connection 对象的start方法
		connection.start();
		//使用connection常见session对象
		//第一个参数是否开始事务，一般不使用事务。保证数据最终一致，可以用消息队列实现
		//如果第一个参数为true，第二个自动忽略，如果不开启事务，第二个为自动应答就可以
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//使用session对象创建一个destination对象，
		Topic topic = session .createTopic("test-queue");
		//使用session创建producer对象
		MessageProducer producer = session.createProducer(topic);
		//创建一个textMessage对象
		TextMessage textMessage = session.createTextMessage("hello acticemp");
		//发送消息
		producer.send(textMessage);
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	
	@Test
	public void testTopicConsumser() throws Exception {
		//创建一个连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.95.202.32:61616");
		//使用连接工厂对象创建一个连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		//使用连接对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//使用Session创建一个Destination，Destination应该和消息的发送端一致。
		Topic topic = session.createTopic("test-queue");
		//使用Session创建一个Consumer对象
		MessageConsumer consumer = session.createConsumer(topic);
		//向Consumer对象中设置一个MessageListener对象，用来接收消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				//取消息的内容
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						String text = textMessage.getText();
						//打印消息内容
						System.out.println(text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		//系统等待接收消息
		/*while(true) {
			Thread.sleep(100);
		}*/
		System.out.println("3");
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
}
