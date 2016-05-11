package com.fun90.validation.config;

import com.fun90.validation.IValidator;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 配置类
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public class Configuration {
    /**
     * 组映射
     */
    private Map<String, List<Field>> groups;
    /**
     * 验证器映射
     */
    private Map<String, IValidator> validators;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private Properties properties;

    /**
     * 添加验证组
     *
     * @param name  组名
     * @param rules 组对象
     */
    public void addGroup(String name, List<Field> rules) {
        this.groups.put(name, rules);
    }

    /**
     * 获得验证组
     *
     * @param name 验证组名称
     * @return 验证组对象
     */
    public List<Field> getGroup(String name) {
        return this.groups.get(name);
    }

    /**
     * 添加验证器
     *
     * @param name      验证器名称
     * @param validator 验证器对象
     */
    public void addValidator(String name, IValidator validator) {
        this.validators.put(name, validator);
    }

    /**
     * 添加验证器组
     *
     * @param validators
     */
    public void addValidators(Map<String, IValidator> validators) {
        this.validators.putAll(validators);
    }

    /**
     * 获得验证器对象
     *
     * @param name 验证器名称
     * @return 验证器对象
     */
    public IValidator getValidator(String name) {
        return this.validators.get(name);
    }

    public Map<String, IValidator> getValidators() {
        return validators;
    }

    public void setValidators(Map<String, IValidator> validators) {
        this.validators = validators;
    }

    public Map<String, List<Field>> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, List<Field>> groups) {
        this.groups = groups;
    }
}
