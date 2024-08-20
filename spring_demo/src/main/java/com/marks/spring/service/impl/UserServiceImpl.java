package com.marks.spring.service.impl;

import com.marks.spring.dao.UserDao;
import com.marks.spring.entity.User;
import com.marks.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/20 15:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User getUserById(String id) {
        return new User();
    }
}
