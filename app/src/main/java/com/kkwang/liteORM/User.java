package com.kkwang.liteORM;

/**
 * Created by kw on 2016/3/8.
 */
public class User extends BaseModel {
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    private String name;
    private int age;
}
