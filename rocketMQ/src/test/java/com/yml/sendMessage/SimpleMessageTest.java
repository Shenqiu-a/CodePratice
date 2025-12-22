package com.yml.sendMessage;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 功能：普通消息
 *  也叫并发消息 发送效率最高 使用最多的一种
 * 作者：yml
 * 日期：2024-12-0916:54
 */

public class SimpleMessageTest {

    /**
     * 消息生产者
     */
    @Test
    public void producer() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 1 初始化生产者
        DefaultMQProducer producer = new DefaultMQProducer("producer");
        // 2 rocketmq位置
        producer.setNamesrvAddr("192.168.66.100:9876");
        // 3 启动生产者
        producer.start();
        // 4 初始化消息对象
        Message message = new Message("SimpleMessageTest","tags","hello rocketmq".getBytes(StandardCharsets.UTF_8));
        // 5 发送消息
        SendResult sendResult = producer.send(message);
        System.out.println("消息发送成功: "+sendResult);

        producer.shutdown();

    }

    /**
     * 消息消费者
     */
    @Test
    public void consumer() throws MQClientException, RemotingException, InterruptedException{
        // 1 初始化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer");
        // 2 rocketmq位置
        consumer.setNamesrvAddr("192.168.66.100:9876");
        // 3 订阅主题
        consumer.subscribe("SimpleMessageTest","*");
        // 4 监听消息
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (Message message : list){
                    System.out.println(message);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 5 启动消费者
        consumer.start();
        // 6 永远的运行下去
        Thread.sleep(Long.MAX_VALUE);
    }

}
