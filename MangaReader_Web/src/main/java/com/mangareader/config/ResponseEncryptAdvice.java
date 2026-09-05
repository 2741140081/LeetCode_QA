package com.mangareader.config;

import com.mangareader.annotation.EncryptedField;
import com.mangareader.model.common.Result;
import com.mangareader.util.AesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;

/**
 * 响应加密通知器：对 Result 中 data 对象标注了 @EncryptedField 的字段自动 AES 加密
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ResponseEncryptAdvice implements ResponseBodyAdvice<Result<?>> {

    private final EncryptProperties encryptProperties;

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return encryptProperties.isEnabled()
                && Result.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Result<?> beforeBodyWrite(Result<?> body,
                                     MethodParameter returnType,
                                     MediaType selectedContentType,
                                     Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                     ServerHttpRequest request,
                                     ServerHttpResponse response) {
        if (body == null || body.getData() == null) {
            return body;
        }

        Object data = body.getData();
        // 仅对 VO 对象进行字段级加密
        if (shouldEncrypt(data)) {
            encryptFields(data);
        }

        return body;
    }

    /**
     * 判断对象是否需要加密（非基础类型、非集合）
     */
    private boolean shouldEncrypt(Object data) {
        if (data instanceof String || data instanceof Number || data instanceof Boolean) {
            return false;
        }
        if (data instanceof java.util.Collection || data instanceof java.util.Map) {
            return false;
        }
        return true;
    }

    /**
     * 递归加密对象中标注了 @EncryptedField 的字段
     */
    private void encryptFields(Object obj) {
        if (obj == null) return;
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(EncryptedField.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value instanceof String strValue && !strValue.isEmpty()) {
                        field.set(obj, AesUtils.encrypt(strValue, encryptProperties.getSecretKey()));
                    }
                } catch (IllegalAccessException e) {
                    log.warn("加密字段访问失败: {}.{}", clazz.getSimpleName(), field.getName(), e);
                }
            }
        }
    }
}
