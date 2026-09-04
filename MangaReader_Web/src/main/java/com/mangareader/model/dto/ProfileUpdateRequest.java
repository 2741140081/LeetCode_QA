package com.mangareader.model.dto;

import lombok.Data;

/**
 * 修改个人信息请求 DTO
 *
 * @author marks
 * @version v1.0
 */
@Data
public class ProfileUpdateRequest {

    private String nickname;

    private String avatarUrl;

    private String email;
}
