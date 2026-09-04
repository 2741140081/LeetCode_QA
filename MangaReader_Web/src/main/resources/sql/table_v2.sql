-- ============================================================
-- MangaReader v2 数据库扩展脚本
-- 新增: 用户表、书架文件夹表、书架漫画关联表
-- 改造: reading_progress 表增加 user_id / page_index 字段
-- ============================================================

USE manga_reader;

-- ============================================================
-- 1. 用户表 (user)
-- ============================================================
CREATE TABLE IF NOT EXISTS `user` (
    user_id       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    username      VARCHAR(50)  NOT NULL COMMENT '用户名',
    password_hash VARCHAR(255) NOT NULL COMMENT 'BCrypt加密密码',
    email         VARCHAR(100) DEFAULT '' COMMENT '邮箱',
    nickname      VARCHAR(50)  DEFAULT '' COMMENT '昵称',
    avatar_url    VARCHAR(500) DEFAULT '' COMMENT '头像路径',
    status        TINYINT      NOT NULL DEFAULT 1 COMMENT '1-正常 0-禁用',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (user_id),
    UNIQUE KEY uk_username (username),
    INDEX idx_email (email)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ============================================================
-- 2. 书架文件夹表 (shelf_folder)
-- ============================================================
CREATE TABLE IF NOT EXISTS shelf_folder (
    folder_id   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id     BIGINT       NOT NULL COMMENT '所属用户ID',
    folder_name VARCHAR(100) NOT NULL COMMENT '文件夹名称',
    sort_order  INT          NOT NULL DEFAULT 0 COMMENT '排序序号',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (folder_id),
    INDEX idx_user_id (user_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='书架文件夹表';

-- ============================================================
-- 3. 书架漫画关联表 (shelf_manga)
-- ============================================================
CREATE TABLE IF NOT EXISTS shelf_manga (
    id        BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id   BIGINT   NOT NULL COMMENT '用户ID',
    manga_id  BIGINT   NOT NULL COMMENT '漫画ID',
    folder_id BIGINT   DEFAULT NULL COMMENT '所属文件夹ID, NULL表示未分类',
    added_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_manga (user_id, manga_id),
    INDEX idx_user_folder (user_id, folder_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='书架漫画关联表';

-- ============================================================
-- 4. 改造 reading_progress 表
--    增加 user_id 和 page_index 字段
-- ============================================================
-- 先删除旧表结构（如果存在），重建为新版
-- 注意: 生产环境应使用 ALTER TABLE，此处为初始化脚本使用安全方式
ALTER TABLE reading_progress
    ADD COLUMN user_id BIGINT DEFAULT NULL COMMENT '用户ID' AFTER id,
    ADD COLUMN page_index INT DEFAULT 0 COMMENT '分页页码(从0开始)' AFTER image_index;

-- 为 user_id 添加索引
ALTER TABLE reading_progress
    ADD INDEX idx_user_id (user_id),
    ADD INDEX idx_user_manga (user_id, manga_id);
