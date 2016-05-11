package com.fun90.validation.validators;

import com.fun90.validation.IValidator;
import com.fun90.validation.config.Rule;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 最小匹配
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public class MinValidator implements IValidator {
    private static final Logger logger = Logger.getLogger(MinValidator.class);

    @Override
    public boolean execute(Object data, Class<?> type, Object value, Rule rule) {
        if (value == null) return false;
        int length;
        if (ClassUtils.isAssignable(type, Object[].class)) {
            length = ((Object[]) value).length;
        } else if(StringUtils.isNumeric((String) value)) {
            length = Integer.parseInt((String) value);
        } else {
            length = ((String) value).trim().length();
        }
        String minString = rule.getParameter("value");
        int min = StringUtils.isNumeric(minString) ? Integer.parseInt(minString) : -1;
        if (min == -1) {
            logger.warn("Invalid Parameter for minimun or maximun.");
            return false;
        }
        return length >= min;
    }

}
