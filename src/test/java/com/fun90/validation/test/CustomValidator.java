package com.fun90.validation.test;

import com.fun90.validation.IValidator;
import com.fun90.validation.config.Rule;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 构造一个判断 参数 是否能够被 指定的值 整出的验证器
 * 例如：禁用的用户名不能通过验证
 *
 * @author xionglingcong
 * @version V1.00 2015-7-21
 */
public class CustomValidator implements IValidator {

    @Override
    public boolean execute(Object data, Class<?> type, Object value, Rule rule) {
        /**
         * 这里只是演示一下context的用处
         * 可以通过context以及反射获取对象的属性值
         * 举个例子
         */
        try {
            System.out.println("USERNAME = " + PropertyUtils.getProperty(data, "username"));
        } catch (Exception e1) {
        }

        /**
         * 通过 value 实际验证的值
         */
        if (value == null)
            return true;

        /**
         * 通过 type 获取值的类型
         */
        if (type != String.class) {
            return false;
        }

        /**
         * 通过 rule 获取规则参数
         */
        String toValue = rule.getParameter("value");

        return !value.equals(toValue);
    }

}