package com.mangareader.model.vo;

import com.mangareader.annotation.EncryptedField;
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

    @EncryptedField
    private String email;

    @EncryptedField
    private String nickname;

    private String avatarUrl;

    private LocalDateTime createdAt;
}
