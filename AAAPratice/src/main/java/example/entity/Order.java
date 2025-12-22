package example.entity;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0813:57
 */


public class Order {

    private Customer customer;
    private Address address;

    @Autowired
    public Order(Customer customer, Address address){
        this.customer = customer;
        this.address = address;
    }
    public Order(){}


    public Customer getCustomer(){
        return customer;
    }
    public Address getAddress(){
        return address;
    }
}
