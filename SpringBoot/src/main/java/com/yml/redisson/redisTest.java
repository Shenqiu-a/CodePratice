package com.yml.redisson;

import org.redisson.Redisson;
import org.redisson.RedissonReactive;
import org.redisson.api.*;
import org.redisson.config.Config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-3013:25
 */

@Configuration
@ComponentScan
public class redisTest {
    public void test() throws IOException {
        Config config = new Config();
        config = Config.fromYAML(new File("application.yml"));

        RedissonClient redissonClient = Redisson.create(config);
        RedissonRxClient rxClient = redissonClient.rxJava();

        RMapRx<String, String> map = rxClient.getMap("testMap");

        map.put("key1","key2");
        map.get("key1").subscribe(System.out::println);
    }
}
