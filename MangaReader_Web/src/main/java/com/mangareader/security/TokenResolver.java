package com.mangareader.security;

import com.mangareader.constant.ResultCode;
import com.mangareader.model.common.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * Token 解析工具类：从 HttpServletRequest 中提取 Bearer Token
 * <p>
 * 统一各 Controller 中重复的 Token 提取逻辑，消除魔法字符串
 * </p>
 *
 * @author marks
 * @version v1.0
 */
public final class TokenResolver {

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private TokenResolver() {}

    /**
     * 从请求头中提取 Bearer Token（去除 "Bearer " 前缀）
     *
     * @param request HTTP 请求
     * @return 纯 Token 字符串，若不存在则返回 null
     */
    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    /**
     * 从请求头中提取 Token 并通过 JwtUtils 解析出用户 ID。
     * 若 Token 缺失则抛出 401 异常。
     *
     * @param request  HTTP 请求
     * @param jwtUtils JWT 工具类实例
     * @return 当前登录用户的 ID
     * @throws BusinessException 未登录时抛出 401
     */
    public static Long getCurrentUserId(HttpServletRequest request, JwtUtils jwtUtils) {
        String token = resolveToken(request);
        if (token == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "未登录");
        }
        return jwtUtils.getUserIdFromToken(token);
    }
}
