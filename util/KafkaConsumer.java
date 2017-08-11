package com.gome.wa.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;


public class KafkaConsumer {

    private final ConsumerConnector consumer;

    private String topic;

    public KafkaConsumer(String groupId,String topic,String url) {
    	this.topic=topic;
        Properties properties = new Properties();
        properties.put("zookeeper.connect", url);
        properties.put("zookeeper.session.timeout.ms","4000");
        properties.put("zookeeper.sync.time.ms", "200");
        properties.put("group.id", groupId);
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("auto.offset.reset", "smallest"); 
        ConsumerConfig consumerConfig = new ConsumerConfig(properties);
        consumer = Consumer.createJavaConsumerConnector(consumerConfig);

    }

    public ConsumerIterator<String, String> consume() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap =
                consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
        KafkaStream<String, String> stream = consumerMap.get(topic).get(0);
        ConsumerIterator<String, String> it = stream.iterator();
        return it;
    }



}