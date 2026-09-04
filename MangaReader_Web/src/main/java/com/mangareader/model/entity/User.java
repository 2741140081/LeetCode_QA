package com.mangareader.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author marks
 * @version v1.0
 */
@Data
public class User {
    /**
     * 主键ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * BCrypt加密密码
     */
    private String passwordHash;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像路径
     */
    private String avatarUrl;

    /**
     * 状态: 1-正常 0-禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
