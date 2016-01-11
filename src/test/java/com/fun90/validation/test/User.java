package com.fun90.validation.test;

import java.sql.Timestamp;

/**
 * 用户对象
 *
 * @author xionglingcong
 * @version V1.00 2015-7-20
 */
public class User {
    // 用户名
    private String username;

    // 密码
    private String password;
    // 再次输入密码
    private String passwordOne;

    // 邮箱
    private String email;

    // 开始与结束时间
    private Timestamp starttime;
    private Timestamp endtime;

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public String getPasswordOne() {
        return passwordOne;
    }

    public void setPasswordOne(String passwordOne) {
        this.passwordOne = passwordOne;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}