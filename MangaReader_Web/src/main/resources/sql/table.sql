CREATE DATABASE IF NOT EXISTS manga_reader DEFAULT CHARSET utf8mb4;
USE manga_reader;

-- 漫画表
CREATE TABLE manga (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(100) DEFAULT '',
    description TEXT,
    cover_path  VARCHAR(500) DEFAULT '',
    status      TINYINT DEFAULT 1 COMMENT '1:正常 0:删除',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_title (title),
    INDEX idx_author (author)
) ENGINE=InnoDB;

-- 章节表
CREATE TABLE chapter (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    manga_id      BIGINT NOT NULL,
    chapter_num   INT NOT NULL COMMENT '章节序号',
    title         VARCHAR(255) DEFAULT '',
    image_dir_path VARCHAR(500) NOT NULL COMMENT '本地存储目录',
    image_count   INT DEFAULT 0,
    download_url  VARCHAR(1000) DEFAULT '' COMMENT '章节网络下载源地址',
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_manga_chapter (manga_id, chapter_num),
    FOREIGN KEY (manga_id) REFERENCES manga(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 阅读进度表
CREATE TABLE reading_progress (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    manga_id      BIGINT NOT NULL,
    chapter_id    BIGINT NOT NULL,
    page_index    INT DEFAULT 0,
    read_position DOUBLE DEFAULT 0 COMMENT '像素偏移',
    last_read_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_manga_chapter (manga_id, chapter_id),
    FOREIGN KEY (manga_id) REFERENCES manga(id) ON DELETE CASCADE,
    FOREIGN KEY (chapter_id) REFERENCES chapter(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 书签表
CREATE TABLE bookmark (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    manga_id    BIGINT NOT NULL,
    chapter_id  BIGINT NOT NULL,
    page_index  INT NOT NULL,
    note        VARCHAR(500) DEFAULT '',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (manga_id) REFERENCES manga(id) ON DELETE CASCADE,
    FOREIGN KEY (chapter_id) REFERENCES chapter(id) ON DELETE CASCADE
) ENGINE=InnoDB;