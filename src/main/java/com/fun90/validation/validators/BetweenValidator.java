/*
 * 文件名：BetweenValidator.java
 * 版权：Copyright 2011-2018 Kurrent Tech. Co. Ltd. All Rights Reserved.
 *
 */
package com.fun90.validation.validators;

import com.fun90.validation.IValidator;
import com.fun90.validation.config.Rule;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 字符长度区间验证器
 *
 * @author xionglingcong
 * @version V1.00 2015-7-20
 */
public class BetweenValidator implements IValidator {
    private static final Logger logger = Logger.getLogger(BetweenValidator.class);

    @Override
    public boolean execute(Object data, Class<?> type, Object value, Rule rule) {
        if (value == null) return false;

        int length = 0;
        if (ClassUtils.isAssignable(type, Object[].class)) {
            length = ((Object[]) value).length;
        } else if (type == String.class) {
            length = ((String) value).trim().length();
        }
        String minString = rule.getParameter("min");
        int min = StringUtils.isNumeric(minString) ? Integer.parseInt(minString) : -1;
        String maxString = rule.getParameter("max");
        int max = StringUtils.isNumeric(maxString) ? Integer.parseInt(maxString) : -1;
        if (min == -1 || max == -1) {
            logger.warn("Invalid Parameter for minimun or maximun.");
            return false;
        }
        return length >= min && length <= max;
    }

}
