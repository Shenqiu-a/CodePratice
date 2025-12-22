package example.ProxyCglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-2515:03
 */

public class StarInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = null;
        if("sing".equals(method.getName())){
            System.out.println("唱歌 准备工作，收钱");
            result = proxy.invokeSuper(obj, args);
            System.out.println("唱歌的结束工作，收钱");
        }else if ("dance".equals(method.getName())){
            System.out.println("跳舞的准备工作，收钱");
            result = proxy.invokeSuper(obj, args);
            System.out.println("跳舞的结束工作，收钱");
        }
        return result;
    }
}
