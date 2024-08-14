package com.marks.spring;

import com.marks.spring.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/29 10:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class UserTest {

    @Test
    void testSpringCreateUserObj() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        User user = context.getBean("user", User.class); // 使用无参的构造方法创建User对象
        user.setUserId("12345678");
        System.out.println(user.getUserId());

        User myBean = context.getBean("myBean", User.class);
        System.out.println(myBean.getUserId());
    }
}