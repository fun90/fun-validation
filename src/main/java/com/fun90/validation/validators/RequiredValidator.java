package com.fun90.validation.validators;

import com.fun90.validation.IValidator;
import com.fun90.validation.config.Rule;
import org.apache.commons.lang3.ClassUtils;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 必填
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public class RequiredValidator implements IValidator {

    @Override
    public boolean execute(Object data, Class<?> type, Object value, Rule rule) {
        if (value == null) return false;
        if (ClassUtils.isAssignable(type, Object[].class))
            return ((Object[]) value).length > 0;
        else if (type == String.class)
            return ((String) value).trim().length() > 0;
        else if (type == Timestamp.class || type == Date.class) return true;
        return false;
    }

}
