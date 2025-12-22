package com.yml.sendMessage;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 功能：顺序消息
 *     全局消息 -发送和消费参与的队列只有一个
 * 作者：yml
 * 日期：2024-12-1114:07
 */

public class SequentialMessageTest {

    @Test
    public void producer() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        producer.setNamesrvAddr("192.168.66.200:9876");
        producer.start();
        // 4 发送消息
        // 第二个参数 消息队列选择器
        // 第三个参数 消息将要进入的队列的下标 指定消息都发送到下标为 1 的队列
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("global_order_topic","",("全局有序消息"+i).getBytes(StandardCharsets.UTF_8));
            SendResult result = producer.send(msg, new MessageQueueSelector() {
                // 参数一 -topic下有的队列的集合
                // 参数二 -消息体
                // 参数三 -消息将要进入的队列的下标
                @Override
                public MessageQueue select(List<MessageQueue> list, Message messmage, Object o) {
                    return list.get((Integer) o);
                }
            }, 1);
            System.out.println("结果" + result);
        }
        producer.shutdown();
    }



    /**
     * 消息消费者
     */
    @Test
    public void consumer() throws MQClientException, RemotingException, InterruptedException{
        // 1 初始化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-group-test");
        // 2 rocketmq位置
        consumer.setNamesrvAddr("192.168.66.100:9876");
        // 3 订阅主题
        consumer.subscribe("global_order_topic","*");
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
