package com.mangareader.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 密码重置请求 DTO（通过邮箱验证码）
 *
 * @author marks
 * @version v1.0
 */
@Data
public class PasswordResetRequest {

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
