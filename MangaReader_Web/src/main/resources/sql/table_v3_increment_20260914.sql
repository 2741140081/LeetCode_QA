-- ============================================================
-- 增量脚本: chapter 表增加 chapter_status 字段
-- 日期: 2026-09-14
-- ============================================================

USE manga_reader;

-- chapter 表增加状态字段
ALTER TABLE chapter ADD COLUMN chapter_status TINYINT NOT NULL DEFAULT 0
  COMMENT '状态: 0-待处理, 1-处理中, 2-已完成, 3-处理失败';

-- ============================================================
-- 新增邮件发送记录表 (mail_send_record)
-- ============================================================
CREATE TABLE IF NOT EXISTS mail_send_record (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    to_email      VARCHAR(100) NOT NULL COMMENT '收件人邮箱',
    subject       VARCHAR(255) NOT NULL COMMENT '邮件主题',
    content       TEXT         NOT NULL COMMENT '邮件内容(HTML)',
    mail_type     TINYINT      NOT NULL DEFAULT 1 COMMENT '类型: 1-密码重置',
    send_status   TINYINT      NOT NULL DEFAULT 0 COMMENT '0-待发送, 1-发送中, 2-已发送, 3-发送失败',
    retry_count   INT          NOT NULL DEFAULT 0 COMMENT '重试次数',
    error_msg     VARCHAR(512) DEFAULT NULL COMMENT '错误信息',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_send_status (send_status),
    INDEX idx_to_email (to_email)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='邮件发送记录表';
