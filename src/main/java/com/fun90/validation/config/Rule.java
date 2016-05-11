package com.fun90.validation.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证规则<br>
 * name是指验证器的名称，message是指验证失败后的提示信息
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public class Rule {
    /**
     * 验证器名称
     */
    private String name;
    /**
     * 验证失败后的消息
     */
    private String message;
    /**
     * 参数
     */
    private Map<String, String> parameters;

    public void addParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    public Rule() {
        this.parameters = new HashMap<String, String>();
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[ name=" + name + ", message=" + message + " ]";
    }
}
