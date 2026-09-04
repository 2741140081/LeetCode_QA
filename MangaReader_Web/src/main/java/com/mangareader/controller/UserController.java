package com.mangareader.controller;

import com.mangareader.model.common.BusinessException;
import com.mangareader.model.common.Result;
import com.mangareader.model.dto.PasswordChangeRequest;
import com.mangareader.model.dto.PasswordResetRequest;
import com.mangareader.model.dto.ProfileUpdateRequest;
import com.mangareader.model.vo.UserVO;
import com.mangareader.security.JwtUtils;
import com.mangareader.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户信息控制器：修改个人资料 / 修改密码 / 找回密码
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    /**
     * 修改个人信息（昵称、头像、邮箱）
     */
    @PutMapping("/profile")
    public Result<UserVO> updateProfile(@RequestBody ProfileUpdateRequest request,
                                        HttpServletRequest httpRequest) {
        Long userId = getCurrentUserId(httpRequest);
        userService.updateProfile(userId, request.getNickname(), request.getAvatarUrl(), request.getEmail());
        UserVO user = userService.getUserById(userId);
        return Result.ok("信息更新成功", user);
    }

    /**
     * 修改密码（需验证旧密码）
     */
    @PutMapping("/password")
    public Result<Void> changePassword(@Valid @RequestBody PasswordChangeRequest request,
                                       HttpServletRequest httpRequest) {
        Long userId = getCurrentUserId(httpRequest);
        userService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
        return Result.ok("密码修改成功", null);
    }

    /**
     * 发送密码重置验证码到邮箱
     */
    @PostMapping("/password/reset/code")
    public Result<Void> sendResetCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (!StringUtils.hasText(email)) {
            throw new BusinessException(400, "邮箱不能为空");
        }
        userService.sendResetCode(email);
        return Result.ok("验证码已发送", null);
    }

    /**
     * 通过邮箱验证码重置密码
     */
    @PostMapping("/password/reset")
    public Result<Void> resetPassword(@Valid @RequestBody PasswordResetRequest request) {
        userService.resetPassword(request.getEmail(), request.getVerifyCode(), request.getNewPassword());
        return Result.ok("密码重置成功", null);
    }

    /**
     * 从请求头 Token 中提取当前用户 ID
     */
    private Long getCurrentUserId(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ")) {
            throw new BusinessException(401, "未登录");
        }
        String token = bearerToken.substring(7);
        return jwtUtils.getUserIdFromToken(token);
    }
}
