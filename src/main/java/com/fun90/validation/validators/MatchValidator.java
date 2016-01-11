/*
 * 文件名：MatchValidator.java
 * 版权：Copyright 2011-2018 Kurrent Tech. Co. Ltd. All Rights Reserved.
 *
 */
package com.fun90.validation.validators;

import com.fun90.validation.IValidator;
import com.fun90.validation.config.Rule;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 正则匹配
 *
 * @author xionglingcong
 * @version V1.00 2015-7-20
 */
public class MatchValidator implements IValidator {

    @Override
    public boolean execute(Object data, Class<?> type, Object value, Rule rule) {
        if (value == null) return false;
        String regex = rule.getParameter("regex");
        return StringUtils.isBlank(regex) || Pattern.matches(regex, value.toString());
    }
}
