package com.mangareader.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要 AES 加密的字段。
 * <p>
 * 被标记的字段在响应返回时自动加密，前端接收后自动解密。
 * </p>
 *
 * @author marks
 * @version v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptedField {
}
