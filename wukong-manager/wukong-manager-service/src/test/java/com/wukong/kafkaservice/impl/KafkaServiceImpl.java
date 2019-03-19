package com.wukong.kafkaservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import com.wukong.kafkaservice.KafkaService;

public class KafkaServiceImpl implements KafkaService {
	@Autowired
	@Qualifier("kafkaTopicTest")
	MessageChannel channel;

	@Override
	public void sendUserInfo(String topic, Object obj) {
		// TODO Auto-generated method stub
	}

}
