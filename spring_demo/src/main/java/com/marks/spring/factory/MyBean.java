package com.marks.spring.factory;

import com.marks.spring.entity.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * <p>项目名称: 工厂bean </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/13 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MyBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        User user = new User();
        user.setUserId("80020355");
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
