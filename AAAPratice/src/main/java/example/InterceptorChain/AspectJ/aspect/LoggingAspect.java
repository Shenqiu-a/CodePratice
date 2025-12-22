package example.InterceptorChain.AspectJ.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-2820:11
 */

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* example.InterceptorChain.AspectJ.service.*.*(..))")
    public void logBefore(){
        System.out.println("Method is about to be called");
    }
}
