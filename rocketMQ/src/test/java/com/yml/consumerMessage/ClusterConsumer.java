package com.yml.consumerMessage;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 功能：集群消费
 *   cluster 在同一个组里，每个consumer消费  所订阅Topic的部分消息
 *   广播 在同一个组里，每个consumer都能消费到 所订阅Topic的全部消息
 * 作者：yml
 * 日期：2024-12-1316:19
 */

public class ClusterConsumer {
    @Test
    public void producer() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("default-Producer-Group");
        producer.setNamesrvAddr("192.168.66.100:9876");
        producer.start();
        Message message = new Message("FilterOrder","tag","123".getBytes(StandardCharsets.UTF_8));
        producer.send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                return null;
            }
        },1);
        producer.shutdown();
    }

    //cluster
    @Test
    public void clusterConsumer() throws MQClientException, InterruptedException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("default-Consumer-Group");
        consumer.setNamesrvAddr("192.168.66.100:9876");
        consumer.subscribe("FilterOrder","*");
        /**
         *    设置消费模式
         *       MessageModel.BROADCASTING 广播
         *       MessageModel.CLUSTERING 集群
         */
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt message : list) {
                    System.out.println(new String(message.getBody(),StandardCharsets.UTF_8));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void nextClusterConsumer() throws MQClientException, InterruptedException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("default-Consumer-Group");
        consumer.setNamesrvAddr("192.168.66.100:9876");
        consumer.subscribe("FilterOrder","*");
        /**
         *    设置消费模式
         *       MessageModel.BROADCASTING 广播
         *       MessageModel.CLUSTERING 集群
         */
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt message : list) {
                    System.out.println(new String(message.getBody(),StandardCharsets.UTF_8));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        Thread.sleep(Long.MAX_VALUE);
    }

}
