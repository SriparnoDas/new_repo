package com.userstockpreference.config;

//Importing required classes
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.userstockpreference.model.UserStockPreference;

//Annotations
@EnableKafka
@Configuration
public class KafkaConfig {
	
	@Value("${kafka.groupid}")
	private String groupId;
	@Value("${kafka.bootstrap-servers}")
	private String kafkaServer;
	@Value("${kafka.topic.offset}")
	private String offset;
	
	public Map commonKafkaProerty() {
		Map<String, Object> config = new HashMap<>();

		// Adding the Configuration
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset);
		return config;

	}

	@Bean
	public ConsumerFactory<String, UserStockPreference> stockConsumerFactory() {

		return new DefaultKafkaConsumerFactory<>(commonKafkaProerty(), new StringDeserializer(),
				new JsonDeserializer<>(UserStockPreference.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UserStockPreference> stockPreferenceListner() {
		ConcurrentKafkaListenerContainerFactory<String, UserStockPreference> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(stockConsumerFactory());
		return factory;
	}
	}
