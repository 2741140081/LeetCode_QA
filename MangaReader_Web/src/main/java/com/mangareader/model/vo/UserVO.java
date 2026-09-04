package com.mangareader.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户信息 VO
 *
 * @author marks
 * @version v1.0
 */
@Data
public class UserVO {

    private Long userId;

    private String username;

    private String email;

    private String nickname;

    private String avatarUrl;

    private LocalDateTime createdAt;
}
