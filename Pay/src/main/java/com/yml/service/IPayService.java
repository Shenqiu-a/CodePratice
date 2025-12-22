package com.yml.service;

import com.yml.api.IPay;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0915:35
 */

@DubboService
public class IPayService implements IPay {
    @Override
    public int pay() {
        int flag = 0;
        System.out.println("支付成功");
        flag = 1;
        return flag;
    }
}
