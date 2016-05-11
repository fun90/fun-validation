package com.fun90.validation;

import com.fun90.validation.config.Rule;

/**
 * 校验框架中的执行接口
 * 该接口为重要接口，处理每个验证的处理方法
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public interface IValidator {

    /**
     * 要处理的对象
     *
     * @param data  验证的数据对象
     * @param type  验证字段的类型
     * @param value 验证字段的值
     * @param rule  验证规则
     * @return 是否成功
     */
    boolean execute(Object data, Class<?> type, Object value, Rule rule);
}
