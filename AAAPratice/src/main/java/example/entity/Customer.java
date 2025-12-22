package example.entity;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0813:56
 */
public class Customer {
    private String name;
    private String phone;

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Printable
    public void printName() {
        System.out.println("Customer name : " + name);
    }

    public void printPhone() {
        System.out.println("Customer phone : " + phone);
    }

    public Customer (String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public Customer() {

    }
}
