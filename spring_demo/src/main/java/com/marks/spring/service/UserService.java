package com.marks.spring.service;

import com.marks.spring.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/20 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
//@Component(value = "userService") // value可以不写, 默认value = 类名首字母小写, 即userService
@Service
public interface UserService {
    User getUserById(String id);
}
