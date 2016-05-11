package com.fun90.validation.config;


/**
 * 验证框架的配置器
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public interface IValidateConfig {
    /**
     * 读取配置
     *
     * @return 配置对象
     */
    Configuration readConfiguration();
}
