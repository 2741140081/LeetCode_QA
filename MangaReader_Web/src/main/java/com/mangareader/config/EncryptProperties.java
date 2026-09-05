package com.mangareader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 加密配置属性
 *
 * @author marks
 * @version v1.0
 */
@Component
@ConfigurationProperties(prefix = "manga.encrypt")
@Data
public class EncryptProperties {

    /** AES 密钥（建议 16 字节） */
    private String secretKey = "MangaReader2026A";

    /** 是否启用响应加密 */
    private boolean enabled = true;

    /** 需要加密的字段名列表 */
    private List<String> fields = new ArrayList<>();
}
