package com.yml.test.redisson;

import io.reactivex.rxjava3.core.Maybe;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RMapRx;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-3013:25
 */

@Configuration
@ComponentScan
public class redisTest {
    @Test
    public void test() throws IOException, InterruptedException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient redissonClient = Redisson.create(config);
        RedissonRxClient rxClient = redissonClient.rxJava();

        RMapRx<String, String> map = rxClient.getMap("testMap");

        map.put("key1","value2");
        CountDownLatch latch = new CountDownLatch(1);
        map.get("key1").subscribe(
                value -> {
                    System.out.println("==========================="+value+"========================");
                    latch.countDown();
                },
                error -> {
                    System.out.println(error);
                    latch.countDown();
                }
        );
        latch.await();
        redissonClient.shutdown();

    }

    @Test
    public void testRedisConnectionAndOperations() throws IOException, InterruptedException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient redissonClient = Redisson.create(config);

        RMap<String, String> map = redissonClient.getMap("testMap");

        // 插入键值对
        map.put("key1", "value2");

        CountDownLatch latch = new CountDownLatch(1);

        // 订阅 Maybe<String> 并处理结果
        String key1 = map.get("key1");
        System.out.println("===========================" + key1 + "========================");
        Maybe<String> maybe = Maybe.create(emitter -> {
            try {
                emitter.onSuccess(key1);
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
        maybe.subscribe(
                value -> {
                    System.out.println("===========================" + value + "========================");
                    latch.countDown();
                },
                error -> {
                    System.out.println(error);
                    latch.countDown();
                }
        );

        // 等待异步操作完成
        latch.await();

        redissonClient.shutdown();
    }
}
