package com.mangareader.service.impl;

import com.mangareader.model.common.BusinessException;
import com.mangareader.model.entity.User;
import com.mangareader.model.vo.LoginVO;
import com.mangareader.model.vo.UserVO;
import com.mangareader.mapper.UserMapper;
import com.mangareader.security.JwtUtils;
import com.mangareader.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String REDIS_TOKEN_PREFIX = "manga:token:";
    private static final String REDIS_CODE_PREFIX = "manga:reset_code:";

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final StringRedisTemplate redisTemplate;

    @Override
    public UserVO register(String username, String password, String email, String nickname) {
        // 检查用户名是否已存在
        if (userMapper.findByUsername(username) != null) {
            throw new BusinessException(400, "用户名已存在");
        }
        // 检查邮箱是否已注册
        if (email != null && !email.isEmpty() && userMapper.findByEmail(email) != null) {
            throw new BusinessException(400, "邮箱已被注册");
        }

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setEmail(email != null ? email : "");
        user.setNickname(nickname != null ? nickname : username);
        user.setAvatarUrl("");
        user.setStatus(1);

        userMapper.insert(user);
        log.info("用户注册成功: {}", username);
        return toUserVO(user);
    }

    @Override
    public LoginVO login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BusinessException(403, "用户已被禁用");
        }

        // 生成 JWT
        String token = jwtUtils.generateToken(user.getUserId(), user.getUsername());

        // 存入 Redis，TTL = JWT 过期时间
        redisTemplate.opsForValue().set(
                REDIS_TOKEN_PREFIX + token,
                String.valueOf(user.getUserId()),
                jwtUtils.getExpirationMinutes(),
                TimeUnit.MINUTES
        );

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUser(toUserVO(user));

        log.info("用户登录成功: {}", username);
        return loginVO;
    }

    @Override
    public void logout(String token) {
        if (token != null) {
            redisTemplate.delete(REDIS_TOKEN_PREFIX + token);
            log.info("用户登出, token 已清除");
        }
    }

    @Override
    public UserVO getCurrentUser(String token) {
        Long userId = jwtUtils.getUserIdFromToken(token);
        User user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return toUserVO(user);
    }

    @Override
    public UserVO getUserById(Long userId) {
        User user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return toUserVO(user);
    }

    @Override
    public void updateProfile(Long userId, String nickname, String avatarUrl, String email) {
        // 检查邮箱是否被其他用户使用
        if (email != null && !email.isEmpty()) {
            User existing = userMapper.findByEmail(email);
            if (existing != null && !existing.getUserId().equals(userId)) {
                throw new BusinessException(400, "邮箱已被其他用户使用");
            }
        }

        User user = new User();
        user.setUserId(userId);
        user.setNickname(nickname);
        user.setAvatarUrl(avatarUrl);
        user.setEmail(email);
        userMapper.updateProfile(user);
        log.info("用户信息更新: userId={}", userId);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new BusinessException(400, "旧密码错误");
        }
        userMapper.updatePassword(userId, passwordEncoder.encode(newPassword));
        log.info("用户密码修改: userId={}", userId);
    }

    @Override
    public void sendResetCode(String email) {
        User user = userMapper.findByEmail(email);
        if (user == null) {
            throw new BusinessException(404, "该邮箱未注册");
        }

        // 生成 6 位数字验证码
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));

        // 存入 Redis，5 分钟过期
        redisTemplate.opsForValue().set(
                REDIS_CODE_PREFIX + email,
                code,
                5,
                TimeUnit.MINUTES
        );

        // 实际项目中应发送邮件，此处仅打印日志
        log.info("密码重置验证码已发送至 {}: {}", email, code);
    }

    @Override
    public void resetPassword(String email, String verifyCode, String newPassword) {
        String storedCode = redisTemplate.opsForValue().get(REDIS_CODE_PREFIX + email);
        if (storedCode == null || !storedCode.equals(verifyCode)) {
            throw new BusinessException(400, "验证码无效或已过期");
        }

        User user = userMapper.findByEmail(email);
        if (user == null) {
            throw new BusinessException(404, "该邮箱未注册");
        }

        userMapper.updatePassword(user.getUserId(), passwordEncoder.encode(newPassword));
        redisTemplate.delete(REDIS_CODE_PREFIX + email);
        log.info("密码重置成功: email={}", email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    private UserVO toUserVO(User user) {
        UserVO vo = new UserVO();
        vo.setUserId(user.getUserId());
        vo.setUsername(user.getUsername());
        vo.setEmail(user.getEmail());
        vo.setNickname(user.getNickname());
        vo.setAvatarUrl(user.getAvatarUrl());
        vo.setCreatedAt(user.getCreatedAt());
        return vo;
    }
}
