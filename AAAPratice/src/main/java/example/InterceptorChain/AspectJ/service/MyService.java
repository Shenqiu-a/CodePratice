package example.InterceptorChain.AspectJ.service;

import org.springframework.stereotype.Service;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-2820:13
 */

@Service
public class MyService {
    public void doSomething() {
        System.out.println("MyService.doSomething()");
    }
}
