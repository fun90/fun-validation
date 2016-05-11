package com.fun90.validation;

import java.util.Map;

/**
 * 验证处理管理器
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public interface IValidateService {
    /**
     * 对某个对象执行按组验证操作
     *
     * @param object  验证的数据对象
     * @param groupId 验证组
     * @return 返回验证结果，如果数量为0，则表示正确无误
     */
    Map<String, String> validate(Object object, String groupId);
}
