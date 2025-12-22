package example.InterceptorChain.HandlerInterceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-2819:39
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor((HandlerInterceptor) new WebConfig())
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/static/**", "/css/**", "/js/**"); // 排除静态资源
    }
}
