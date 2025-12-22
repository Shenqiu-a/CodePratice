package com.yml.service;

import com.yml.api.Order;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0915:26
 */

@DubboService
public class OrderService implements Order {
    @Override
    public void createOrder() {
        System.out.println("创建订单成功!");
    }
}
