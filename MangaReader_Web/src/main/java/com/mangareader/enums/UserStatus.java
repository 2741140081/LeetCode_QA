package com.mangareader.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 *
 * @author marks
 * @version v1.0
 */
@Getter
public enum UserStatus {

    DISABLED(0, "禁用"),
    ACTIVE(1, "正常");

    private final int code;
    private final String desc;

    private static final Map<Integer, UserStatus> CODE_MAP = new HashMap<>();

    static {
        for (UserStatus status : values()) {
            CODE_MAP.put(status.getCode(), status);
        }
    }

    UserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据状态码获取枚举
     *
     * @param code 状态码
     * @return 对应的枚举值
     * @throws IllegalArgumentException 如果状态码无效
     */
    public static UserStatus fromCode(int code) {
        UserStatus status = CODE_MAP.get(code);
        if (status == null) {
            throw new IllegalArgumentException("无效的用户状态码: " + code);
        }
        return status;
    }
}
