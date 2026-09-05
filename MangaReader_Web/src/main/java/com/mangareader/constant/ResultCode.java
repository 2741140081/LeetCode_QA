package com.mangareader.constant;

/**
 * HTTP 业务状态码常量
 *
 * @author marks
 * @version v1.0
 */
public final class ResultCode {

    private ResultCode() {}

    /** 成功 */
    public static final int SUCCESS = 200;

    /** 请求参数错误 */
    public static final int BAD_REQUEST = 400;

    /** 未认证 / 未登录 */
    public static final int UNAUTHORIZED = 401;

    /** 无权限 / 已禁用 */
    public static final int FORBIDDEN = 403;

    /** 资源不存在 */
    public static final int NOT_FOUND = 404;

    /** 服务器内部错误 */
    public static final int SERVER_ERROR = 500;
}
