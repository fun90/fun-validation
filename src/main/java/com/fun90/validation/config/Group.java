/*
 * 文件名：JsonPlugin.java
 * 版权：Copyright 2015-2018 Kurrent Tech. Co. Ltd. All Rights Reserved. 
 * 描述：
 */
package com.fun90.validation.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证组
 *
 * @author xionglingcong
 * @version V1.00 2015-7-20
 */
public class Group {
    /**
     * 验证组名称
     */
    private String name;
    /**
     * 验证字段
     */
    private List<Field> fields;
    /**
     * 允许验证数据组为空
     */
    private boolean allowNull;

    /**
     * 添加字段，暂时不处理去重
     *
     * @param field 字段对象
     */
    public void addField(Field field) {
        this.fields.add(field);
    }

    public Group() {
        // TODO Auto-generated constructor stub
        this.fields = new ArrayList<Field>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "[ name=" + name + ",fields=" + fields + "]";
    }

    public boolean isAllowNull() {
        return allowNull;
    }

    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }
}
