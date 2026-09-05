package com.mangareader.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 修改个人信息请求 DTO
 *
 * @author marks
 * @version v1.0
 */
@Data
public class ProfileUpdateRequest {

    @Size(max = 50, message = "昵称长度不能超过50")
    private String nickname;

    @Size(max = 500, message = "头像路径过长")
    private String avatarUrl;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100")
    private String email;
}
