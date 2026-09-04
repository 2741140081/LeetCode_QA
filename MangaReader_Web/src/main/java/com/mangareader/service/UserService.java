package com.mangareader.service;

import com.mangareader.model.entity.User;
import com.mangareader.model.vo.LoginVO;
import com.mangareader.model.vo.UserVO;

/**
 * 用户服务接口
 *
 * @author marks
 * @version v1.0
 */
public interface UserService {

    /**
     * 用户注册
     */
    UserVO register(String username, String password, String email, String nickname);

    /**
     * 用户登录，返回含 token 的 LoginVO
     */
    LoginVO login(String username, String password);

    /**
     * 登出（删除 Redis 中的 token）
     */
    void logout(String token);

    /**
     * 根据 token 获取当前用户
     */
    UserVO getCurrentUser(String token);

    /**
     * 根据 userId 获取用户
     */
    UserVO getUserById(Long userId);

    /**
     * 修改个人信息
     */
    void updateProfile(Long userId, String nickname, String avatarUrl, String email);

    /**
     * 修改密码
     */
    void changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 发送邮箱验证码
     */
    void sendResetCode(String email);

    /**
     * 通过邮箱验证码重置密码
     */
    void resetPassword(String email, String verifyCode, String newPassword);

    /**
     * 根据用户名获取 User 实体（供 Security 使用）
     */
    User getUserByUsername(String username);
}
