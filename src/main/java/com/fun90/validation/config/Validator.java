/*
 * 文件名：Validator.java
 * 版权：Copyright 2011-2018 Kurrent Tech. Co. Ltd. All Rights Reserved.
 *
 */
package com.fun90.validation.config;

/**
 * 验证器配置<br>
 * name是指验证器名称(全局唯一), className是指验证器class全名
 *
 * @author xionglingcong
 * @version V1.00 2015-7-20
 */
public class Validator {
    private String name;
    private String className;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
