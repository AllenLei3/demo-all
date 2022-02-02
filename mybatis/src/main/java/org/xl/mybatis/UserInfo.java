package org.xl.mybatis;

import java.util.Date;

/**
 * @author xulei
 */
public class UserInfo {

    private int id;

    private String name;

    private int age;

    private String address;

    private Date creatTime;

    public int getId() {
        return id;
    }

    public UserInfo setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserInfo setAge(int age) {
        this.age = age;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserInfo setAddress(String address) {
        this.address = address;
        return this;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public UserInfo setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
        return this;
    }
}
