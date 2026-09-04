package com.mangareader.mapper;

import com.mangareader.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper 接口
 *
 * @author marks
 * @version v1.0
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     */
    User findByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     */
    User findByEmail(@Param("email") String email);

    /**
     * 根据ID查询用户
     */
    User findByUserId(@Param("userId") Long userId);

    /**
     * 新增用户
     */
    int insert(User user);

    /**
     * 更新用户信息（昵称、头像、邮箱）
     */
    int updateProfile(User user);

    /**
     * 更新密码
     */
    int updatePassword(@Param("userId") Long userId, @Param("passwordHash") String passwordHash);
}
