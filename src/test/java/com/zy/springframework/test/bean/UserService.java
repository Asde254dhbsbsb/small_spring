package com.zy.springframework.test.bean;

/**
 * @author zy
 * @since 2022/7/23  16:42
 */
public class UserService {
    private String uId;
    private UserDao userDao;

    public String queryUserInfo() {
        System.out.println("查询用户信息" +
                ":" + userDao.queryUserName(uId));
        return userDao.queryUserName(uId);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
