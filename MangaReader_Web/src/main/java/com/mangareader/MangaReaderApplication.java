package com.mangareader;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * MangaReader Web 版启动类
 * 标准 Spring Boot 应用，内嵌 Tomcat，无 JavaFX 依赖
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.mangareader.mapper")
@EnableConfigurationProperties
@EnableScheduling
@EnableAsync
public class MangaReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangaReaderApplication.class, args);
        log.info("MangaReader Web 版启动成功，访问 http://localhost:8080");
    }
}
