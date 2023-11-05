package Trible;

import java.io.Serializable;

public class Contact implements Serializable {
    private int id;  // 联系人ID
    private String name;  // 姓名
    private String address;  // 住址
    private String phone_number;  // 电话号码

    // 构造函数
    public Contact() {
        // 默认构造函数
    }

    public Contact(int id, String name, String address, String phone_number) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
    }

    // Getter和Setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone_number;
    }

    public void setPhone(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone_number + '\'' +
                '}';
    }
}

