package com.marks.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p>项目名称: spring_demo </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/29 9:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull(message = "userId cannot be null")
    @NotEmpty(message = "userId must be provided")
    @Size(max = 8, min = 8, message = "userId must contain 8 charters")
    @Pattern(regexp = "[0-9]+", message = "userId should be numeric")
    private String userId;
    private String userName;
    private String userAge;
    private String userGender;
    private String nickName;
    private String userAddress;
}
