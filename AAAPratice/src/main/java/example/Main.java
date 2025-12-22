package example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0815:58
 */

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        Object student = context.getBean("student");
        System.out.println(student);
    }
}
