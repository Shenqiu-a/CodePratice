package example.InterceptorChain.AspectJ;

import example.InterceptorChain.AspectJ.service.MyService;
import example.InterceptorChain.AspectJ.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-2820:15
 */

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MyService myService = context.getBean(MyService.class);
        myService.doSomething();
    }
}
