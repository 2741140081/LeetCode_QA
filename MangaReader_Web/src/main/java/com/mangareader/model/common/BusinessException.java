package com.mangareader.model.common;

import lombok.Getter;

/**
 * 自定义业务异常
 *
 * @author marks
 * @version v1.0
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
