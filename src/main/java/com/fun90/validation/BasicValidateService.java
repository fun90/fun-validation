package com.fun90.validation;

import com.fun90.validation.config.Configuration;
import com.fun90.validation.config.Field;
import com.fun90.validation.config.IValidateConfig;
import com.fun90.validation.config.Rule;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础验证服务
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public class BasicValidateService implements IValidateService {
    private static final Logger logger = LoggerFactory.getLogger(BasicValidateService.class);

    private final Configuration configuration;
    private final boolean checkAll;

    public BasicValidateService(IValidateConfig config) {
        configuration = config.readConfiguration();
        checkAll = Boolean.parseBoolean(this.configuration.getProperties().getProperty("checkAll", "false"));
    }

    @Override
    public Map<String, String> validate(Object object, String groupId) {
        Map<String, String> results = new LinkedHashMap<String, String>();        // 存放验证结果信息的Map
        List<Field> fields = this.configuration.getGroup(groupId);   // 根据验证组ID在Configuration中获取字段信息
        if (fields == null || fields.isEmpty()) return results;

        Map<String, IValidator> validators = this.configuration.getValidators();
        if (validators == null || validators.isEmpty()) return results;

        for (Field field : fields)
        {
            String fname = field.getName();
            List<Rule> rules = field.getRules();

            if (rules == null || rules.isEmpty())
                continue;

            Object value = null;
            Class<?> type = null;
            try
            {
                value = PropertyUtils.getProperty(object, fname);
                type = value == null ? null : value.getClass();
            }
            catch (Exception e)
            {
                logger.warn(e.getMessage(), e);
            }

            for (Rule rule : rules)
            {
                String rname = rule.getName();
                IValidator validator = validators.get(rname);
                if (validator == null)
                {
                    logger.warn("the validator isn't exsit. name: " + rname);
                    continue;
                }
                logger.debug(fname + "=" + value + ", validator=" + rname);
                if (!validator.execute(object, type, value, rule))
                {
                    results.put(fname, rule.getMessage());
                    if (!checkAll)
                        return results;
                }
            }
        }
        return results;
    }

    private boolean doValidate(Map<String, IValidator> validators, Object object, String fname, List<Rule> rules, Map<String, String> results) {
        Object value = null;
        Class<?> type = null;
        try {
            value = PropertyUtils.getProperty(object, fname);
            type = value == null ? null : value.getClass();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }

        for (Rule rule : rules) {
            String rname = rule.getName();
            IValidator validator = validators.get(rname);
            if (validator == null) {
                logger.warn("the validator isn't exsit. name: " + rname);
                continue;
            }
            logger.debug(fname + "=" + value + ", validator=" + rname);
            if (!validator.execute(object, type, value, rule)) {
                results.put(fname, rule.getMessage());
                if(!checkAll) return false;
            }
        }

        return true;
    }
}
