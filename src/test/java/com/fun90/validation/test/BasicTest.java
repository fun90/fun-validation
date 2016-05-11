package com.fun90.validation.test;

import com.fun90.validation.BasicValidateService;
import com.fun90.validation.IValidateService;
import com.fun90.validation.config.BasicValidateConfig;
import com.fun90.validation.config.IValidateConfig;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 最基本的测试
 *
 * @author fun90
 * @version V1.00 2015-7-20
 */
public class BasicTest {
    public static void main(String[] args) {
        // 验证器配置
        String validatorsXML = "validation.xml";
        /**
         * 实例化配置对象
         */
        IValidateConfig config = new BasicValidateConfig(validatorsXML);
        /**
         * 实例化验证服务层
         */
        IValidateService validateService = new BasicValidateService(config);

        // 实例化用户
        Map<String, Object> user = createUser();

        /**
         * 执行验证
         */
//        Map<String, String> map = validateService.validate(null, "user.validate");
//        Map<String, String> map = validateService.validate(user, "user.validate");
        Map<String, String> map = validateService.validate(user, "user.validate2");
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        list.add(user);
//        Map<String, String> map = validateService.validate(list, "user.validate2");
        // 输出结果
        if (map == null || map.size() == 0) {
            System.out.println("验证成功");
        } else {
            System.out.println("验证失败，结果如下");
            System.out.println(map);
        }

    }

    public static Map<String, Object> createUser() {
//        User user = new User();

//        user.setEmail("okbeok#163.com");
//        
//        user.setUsername("admin");
//        user.setPassword("12345");
//        user.setPasswordOne("abcde");
//        
//        Calendar date = Calendar.getInstance();
//        user.setStarttime(new Timestamp(date.getTimeInMillis()));
//        user.setEndtime(new Timestamp(date.getTimeInMillis() - 1000));

        Map<String, Object> user = new HashMap<String, Object>();

        user.put("email", "okbeok#163.com");
//        user.put("email", "okbeok@163.com");
        user.put("username", "admin");
//        user.put("username", "fun90");
        user.put("password", "12345");
//        user.put("passwordOne", "abcde");
//        user.put("passwordOne", "12345");
        user.put("id", 100);

        Calendar date = Calendar.getInstance();
        user.put("starttime", new Timestamp(date.getTimeInMillis()));
        user.put("endtime", new Timestamp(date.getTimeInMillis() - 1000));

        return user;
    }
}