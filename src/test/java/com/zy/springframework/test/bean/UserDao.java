package com.zy.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zy
 * @since 2022/7/23  21:37
 */
public class UserDao {
    private static Map<String, String> hashMap = new HashMap<>();
    public void initDataMethod() {
        System.out.println("执行 ： init-method");
        hashMap.put("10001", "zy");
        hashMap.put("10002", "wyw");
        hashMap.put("10003", "mkl");
    }

    public void destroyDataMethod() {
        System.out.println("执行 ： destroy-method");
        hashMap.clear();
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}
