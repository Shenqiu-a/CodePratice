package example.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0813:57
 */
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private String city;

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void printStreet(){
        System.out.println("Address street :" + street);
    }
    public void printCity(){
        System.out.println("Address city :" + city);
    }
}
