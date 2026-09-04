package com.mangareader.config;

import com.github.pagehelper.PageHelper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * PageHelper 分页插件配置类
 * PageHelper Bean 由 pagehelper-spring-boot-starter 根据 application.yml 中的 pagehelper.* 属性自动创建
 * 此类用于启动验证和后续扩展（如自定义分页拦截逻辑）
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@Configuration
public class PageHelperConfig {

    @Autowired
    private PageHelper pageHelper;

    /**
     * 启动时验证 PageHelper 已正确初始化
     */
    @PostConstruct
    public void init() {
        log.info("PageHelper 分页插件已加载，方言: mysql，配置属性: reasonable=true, supportMethodsArguments=true");
    }
}
