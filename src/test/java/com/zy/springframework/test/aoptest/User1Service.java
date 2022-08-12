package com.zy.springframework.test.aoptest;

import com.zy.springframework.test.bean.UserService;

import java.util.Random;

public class User1Service implements IUserService{

    private String userName;
    public User1Service() {}

    public User1Service(String userName) {
        this.userName = userName;
    }
    @Override
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return "zy, 10001, nanjin";
    }

    @Override
    public String register() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return "register user :" + userName + "success";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
