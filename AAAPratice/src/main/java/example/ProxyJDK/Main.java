package example.ProxyJDK;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0819:09
 */

public class Main {
    public static void main(String[] args) {
        Star proxy = new ProxyUtils().createProxy(new BigStar("鸡哥"));
        proxy.dance();
        proxy.sing("鸡 你太美");
    }
}
