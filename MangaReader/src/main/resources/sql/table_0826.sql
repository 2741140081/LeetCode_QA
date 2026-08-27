-- ============================================================
-- MangaReader 数据库初始化脚本
-- 数据库: MySQL 8.0+
-- 字符集: utf8mb4
-- ============================================================

CREATE DATABASE IF NOT EXISTS manga_reader DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
USE manga_reader;

-- ============================================================
-- 1. 漫画表 (manga)
-- ============================================================
DROP TABLE IF EXISTS manga;
CREATE TABLE manga (
                       manga_id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                       manga_name         VARCHAR(255) NOT NULL DEFAULT '' COMMENT '漫画名称/标题',
                       dir_id             VARCHAR(500) NOT NULL DEFAULT '' COMMENT '本地磁盘目录ID',
                       author_name        VARCHAR(100) DEFAULT '' COMMENT '作者',
                       manga_url          VARCHAR(1024) DEFAULT '' COMMENT '漫画下载地址',
                       cover_image        VARCHAR(500) DEFAULT '' COMMENT '封面图片路径',
                       manga_status       TINYINT      NOT NULL DEFAULT 0 COMMENT '状态: 0-待处理, 1-处理中, 2-已完成, 3-处理失败',
                       last_heart_beat    DATETIME     DEFAULT NULL COMMENT '最后心跳时间',
                       total_chapters     INT          DEFAULT 0 COMMENT '总章节数',
                       processed_chapters INT          DEFAULT 0 COMMENT '已处理章节数',
                       created_at         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       updated_at         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       PRIMARY KEY (manga_id),
                       INDEX idx_manga_status (manga_status),
                       INDEX idx_manga_name (manga_name),
                       INDEX idx_created_at (created_at)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='漫画表';

-- ============================================================
-- 2. 章节表 (chapter)
-- ============================================================
DROP TABLE IF EXISTS chapter;
CREATE TABLE chapter (
                         chapter_id    BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                         manga_id      BIGINT        NOT NULL COMMENT '所属漫画ID',
                         chapter_num   INT           NOT NULL DEFAULT 0 COMMENT '章节序号',
                         chapter_url   VARCHAR(1024) DEFAULT '' COMMENT '章节网址',
                         title         VARCHAR(255)  DEFAULT '' COMMENT '章节标题',
                         image_count   INT           DEFAULT 0 COMMENT '图片数量',
                         created_at    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         PRIMARY KEY (chapter_id),
                         UNIQUE KEY uk_manga_chapter (manga_id, chapter_num),
                         INDEX idx_manga_id (manga_id),
                         INDEX idx_chapter_num (manga_id, chapter_num),
                         CONSTRAINT fk_chapter_manga FOREIGN KEY (manga_id) REFERENCES manga (manga_id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='章节表';

-- ============================================================
-- 3. 漫画图片表 (manga_image)
-- ============================================================
DROP TABLE IF EXISTS manga_image;
CREATE TABLE manga_image (
                             image_id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             chapter_id        BIGINT        NOT NULL COMMENT '所属章节ID',
                             image_name        VARCHAR(255)  DEFAULT '' COMMENT '图片文件名',
                             image_type        VARCHAR(20)   DEFAULT '' COMMENT '图片类型(jpg/png/webp等)',
                             image_width       INT           DEFAULT NULL COMMENT '图片宽度(px)',
                             image_height      INT           DEFAULT NULL COMMENT '图片高度(px)',
                             image_url         VARCHAR(1024) DEFAULT '' COMMENT '图片保存地址',
                             download_url      VARCHAR(1024) DEFAULT '' COMMENT '图片下载地址',
                             download_status   TINYINT       NOT NULL DEFAULT 0 COMMENT '下载状态: 0-待处理, 1-处理中, 2-已完成, 3-处理失败',
                             sort_order        INT           NOT NULL DEFAULT 0 COMMENT '图片在章节内的排序序号',
                             file_size         BIGINT        DEFAULT NULL COMMENT '文件大小(字节)',
                             downloaded_size   BIGINT        DEFAULT 0 COMMENT '已下载大小(字节,用于断点续传)',
                             retry_count       INT           NOT NULL DEFAULT 0 COMMENT '重试次数',
                             error_msg         VARCHAR(512)  DEFAULT NULL COMMENT '错误信息',
                             last_trigger_time DATETIME      DEFAULT NULL COMMENT '最后定时任务触发时间',
                             created_at        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             updated_at        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (image_id),
                             INDEX idx_chapter_id (chapter_id),
                             INDEX idx_download_status (download_status),
                             INDEX idx_status_retry (download_status, retry_count),
                             INDEX idx_last_trigger_time (last_trigger_time),
                             CONSTRAINT fk_image_chapter FOREIGN KEY (chapter_id) REFERENCES chapter (chapter_id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='漫画图片表';

-- ============================================================
-- 4. 章节分页下载记录表 (manga_chapter_page_record)
--    用于章节多线程下载 + 页面自动重试机制
-- ============================================================
DROP TABLE IF EXISTS manga_chapter_page_record;
CREATE TABLE manga_chapter_page_record (
                                           id                BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                           chapter_id        BIGINT        NOT NULL COMMENT '关联章节ID',
                                           page_num          INT           NOT NULL DEFAULT 1 COMMENT '页码(从1开始)',
                                           page_url          VARCHAR(512)  DEFAULT '' COMMENT '当前页的访问地址',
                                           download_status   TINYINT       NOT NULL DEFAULT 0 COMMENT '下载状态: 0-待处理, 1-处理中, 2-已完成, 3-处理失败',
                                           retry_count       INT           NOT NULL DEFAULT 0 COMMENT '已重试次数',
                                           last_fail_reason  TEXT          DEFAULT NULL COMMENT '最后一次失败的异常信息',
                                           gmt_create        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           gmt_modify        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                           PRIMARY KEY (id),
                                           INDEX idx_chapter_id (chapter_id),
                                           INDEX idx_download_status (download_status),
                                           INDEX idx_chapter_page (chapter_id, page_num),
                                           INDEX idx_failed_pages (chapter_id, download_status),
                                           CONSTRAINT fk_page_record_chapter FOREIGN KEY (chapter_id) REFERENCES chapter (chapter_id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='章节分页下载记录表';

