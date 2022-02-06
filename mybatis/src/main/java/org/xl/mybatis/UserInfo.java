package org.xl.mybatis;

import org.xl.mybatis.type.IntEnum;

import java.util.Date;

/**
 * @author xulei
 */
public class UserInfo {

    private int id;

    private String name;

    private IntEnum age;

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

    public IntEnum getAge() {
        return age;
    }

    public UserInfo setAge(IntEnum age) {
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
