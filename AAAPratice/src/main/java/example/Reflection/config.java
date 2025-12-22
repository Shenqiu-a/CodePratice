package example.Reflection;

import example.entity.Address;
import example.entity.Customer;
import org.springframework.context.annotation.Bean;


/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0813:52
 */

public class config {

    @Bean
    public Customer getCustomer() {
        return new Customer("yml", "12345678910");
    }

    @Bean
    public Address getAddress() {
        return new Address("sdut", "zibo");
    }

}
