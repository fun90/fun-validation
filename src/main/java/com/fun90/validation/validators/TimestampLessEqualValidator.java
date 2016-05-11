package com.fun90.validation.validators;

import com.fun90.validation.IValidator;
import com.fun90.validation.config.Rule;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.Timestamp;

/**
 * 当前时间需要小于等于
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public class TimestampLessEqualValidator implements IValidator {
    private static final Logger logger = Logger.getLogger(TimestampLessEqualValidator.class);

    @Override
    public boolean execute(Object data, Class<?> type, Object value, Rule rule) {
        if (value == null || !(value instanceof Timestamp)) return false;
        String toName = rule.getParameter("target");
        if (StringUtils.isBlank(toName)) {
            logger.warn("Less And Equal target parameter missed");
            return false;
        }

        try {
            Object toValue = PropertyUtils.getProperty(data, toName);
            Timestamp timestamp = (Timestamp) value;
            Timestamp toTimestamp = (Timestamp) toValue;
            return timestamp.getTime() <= toTimestamp.getTime();
        } catch (Exception e) {
            logger.warn("Less And Equal target value missed , " + toName);
            return false;
        }
    }

}
