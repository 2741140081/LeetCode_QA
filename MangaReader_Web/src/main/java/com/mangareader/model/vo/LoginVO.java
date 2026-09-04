package com.mangareader.model.vo;

import lombok.Data;

/**
 * 登录响应 VO（包含 JWT token）
 *
 * @author marks
 * @version v1.0
 */
@Data
public class LoginVO {

    private String token;

    private UserVO user;
}
