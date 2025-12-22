package example.InterceptorChain.AspectJ.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-2820:15
 */

@Configuration
@ComponentScan(basePackages = "example.InterceptorChain.AspectJ")
@EnableAspectJAutoProxy
public class AppConfig {
    public AppConfig() {
        System.out.println("AppConfig");
    }
}
