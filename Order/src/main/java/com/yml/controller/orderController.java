package com.yml.controller;

import com.yml.service.IPayService;
import com.yml.service.OrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0915:33
 */

public class orderController {

    @DubboReference
    private IPayService iPayService;

    @GetMapping("/getPayment")
    public String order(){
        int flag = iPayService.pay();
        if (flag == 1){
            System.out.println("支付成功");
            return "支付成功";
        }else {
            System.out.println("支付失败");
            return "支付失败";
        }
    }
}
