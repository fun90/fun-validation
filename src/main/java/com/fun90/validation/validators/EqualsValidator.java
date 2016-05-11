package com.fun90.validation.validators;

import com.fun90.validation.IValidator;
import com.fun90.validation.config.Rule;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 相等匹配
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public class EqualsValidator implements IValidator {
    private static final Logger logger = Logger.getLogger(EqualsValidator.class);

    @Override
    public boolean execute(Object data, Class<?> type, Object value, Rule rule) {
        if (value == null) return false;
        String toName = rule.getParameter("target");
        if (StringUtils.isBlank(toName)) {
            logger.warn("Equals target parameter missed");
            return false;
        }

        Object toValue = null;
        try {
            toValue = PropertyUtils.getProperty(data, toName);
        } catch (Exception e) {
            logger.warn("Equals target value missed , " + toName);
        }
        return value.equals(toValue);
    }

}
