package example.ProxyCglib;

import org.springframework.cglib.proxy.Enhancer;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-2515:10
 */

public class ProxyExample {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Star.class);
        enhancer.setCallback(new StarInterceptor());
        Star proxy = (Star) enhancer.create();
        proxy.dance();
        proxy.sing("鸡你太美");
    }
}
