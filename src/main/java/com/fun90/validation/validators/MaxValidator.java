package com.fun90.validation.validators;

import com.fun90.validation.IValidator;
import com.fun90.validation.config.Rule;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 字符长度区间验证器
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public class MaxValidator implements IValidator {
    private static final Logger logger = Logger.getLogger(MaxValidator.class);

    @Override
    public boolean execute(Object data, Class<?> type, Object value, Rule rule) {
        if (value == null) return true;
        int length;
        if (ClassUtils.isAssignable(type, Object[].class)) {
            length = ((Object[]) value).length;
        } else if(StringUtils.isNumeric((String) value)) {
            length = Integer.parseInt((String) value);
        } else {
            length = ((String) value).trim().length();
        }
        String maxString = rule.getParameter("value");
        int max = StringUtils.isNumeric(maxString) ? Integer.parseInt(maxString) : -1;
        if (max == -1) {
            logger.warn("Invalid Parameter for maximun.");
            return false;
        }
        return length <= max;
    }

}
