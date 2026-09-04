package com.mangareader.controller;

import com.mangareader.model.common.Result;
import com.mangareader.model.dto.LoginRequest;
import com.mangareader.model.dto.RegisterRequest;
import com.mangareader.model.vo.LoginVO;
import com.mangareader.model.vo.UserVO;
import com.mangareader.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 认证模块控制器：注册 / 登录 / 登出 / 获取当前用户
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<UserVO> register(@Valid @RequestBody RegisterRequest request) {
        UserVO user = userService.register(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getNickname()
        );
        return Result.ok("注册成功", user);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        LoginVO loginVO = userService.login(request.getUsername(), request.getPassword());
        return Result.ok("登录成功", loginVO);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        String token = resolveToken(request);
        userService.logout(token);
        return Result.ok("登出成功", null);
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/me")
    public Result<UserVO> me(HttpServletRequest request) {
        String token = resolveToken(request);
        UserVO user = userService.getCurrentUser(token);
        return Result.ok(user);
    }

    /**
     * 从请求头提取 Bearer Token
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
