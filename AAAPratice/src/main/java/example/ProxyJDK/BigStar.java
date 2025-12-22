package example.ProxyJDK;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0818:56
 */

@AllArgsConstructor
@NoArgsConstructor
public class BigStar implements Star{
    private String name;

    @Override
    public void sing(String song) {
        System.out.println("开始唱歌");
    }

    @Override
    public void dance() {
        System.out.println("开始跳舞");
    }
}
