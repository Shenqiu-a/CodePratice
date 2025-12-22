package example.ProxyJDK;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0819:00
 */

public class ProxyUtils {
    /**
     * 给一个需要被代理的对象，创建一个代理
     *
     * 返回给其创建的代理
     *
     * 需求：
     *   外面的人想要大明星唱首歌
     *     1 获取代理的对象
     *       代理对象 = ProxyUtil.creatProxy(大明星对象)
     *     2 再调用代理的唱歌方法
     *       代理对象.唱歌的方法("只因你太美")
     */
    public Star createProxy(BigStar proxiedObject){
        /**
         * 参数一：用于指定用哪个类加载器，去加载生成的代理类
         * 参数二：指定接口，这些接口，用于指定生成的代理长什么样，也就是有哪些方法
         * 参数三：用来指定生成的代理对象要干什么事情
         */
        Star star = (Star) Proxy.newProxyInstance(ProxyUtils.class.getClassLoader(),
                new Class[]{Star.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        /**
                         * 参数一：代理对象
                         * 参数二：要运行的方法，sing
                         * 参数三：调用sing时，传递的实参
                         */
                        if("sing".equals(method.getName())){
                            System.out.println("唱歌 准备工作，收钱");
                        }else if("dance".equals(method.getName())){
                            System.out.println("跳舞的准备工作，收钱");
                        }
                        /**
                         * 去找大明星开始唱歌或者跳舞
                         * 代码的表现形式：调用大明星里面的唱歌或者跳舞方法
                         */
                        return method.invoke(proxiedObject,args);
                    }
                }
        );
        return star;
    }

}
