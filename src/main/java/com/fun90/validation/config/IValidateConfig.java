/*
 * 文件名：IValidateConfig.java
 * 版权：Copyright 2015-2018 Kurrent Tech. Co. Ltd. All Rights Reserved. 
 * 描述：
 */
package com.fun90.validation.config;


/**
 * 验证框架的配置器
 *
 * @author xionglingcong
 * @version V1.00 2015-7-20
 */
public interface IValidateConfig {
    /**
     * 读取配置
     *
     * @return 配置对象
     */
    public Configuration readConfiguration();
}
