package com.mangareader.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Web MVC 配置：静态资源映射 + SPA fallback
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${manga.storage.image-path}")
    private String imagePath;

    @Value("${manga.storage.root}")
    private String storageRoot;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 漫画图片：/images/** -> 本地磁盘目录
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imagePath + "/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        // 安全校验：防止路径穿越
                        Path normalizedPath = location.createRelative(resourcePath).getFile().toPath().normalize();
                        if (!normalizedPath.startsWith(location.getFile().toPath().normalize())) {
                            log.warn("检测到路径穿越尝试: {}", resourcePath);
                            return null;
                        }
                        Resource resource = location.createRelative(resourcePath);
                        return resource.exists() && resource.isReadable() ? resource : null;
                    }
                });

        // 封面/书库：/covers/** -> 本地磁盘根目录
        registry.addResourceHandler("/covers/**")
                .addResourceLocations("file:" + storageRoot + "/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Path normalizedPath = location.createRelative(resourcePath).getFile().toPath().normalize();
                        if (!normalizedPath.startsWith(location.getFile().toPath().normalize())) {
                            log.warn("检测到路径穿越尝试: {}", resourcePath);
                            return null;
                        }
                        Resource resource = location.createRelative(resourcePath);
                        return resource.exists() && resource.isReadable() ? resource : null;
                    }
                });

        // 前端静态资源（生产环境 SPA）
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource resource = location.createRelative(resourcePath);
                        // SPA fallback：非 API/图片路径返回 index.html
                        if (!resource.exists() || !resource.isReadable()) {
                            if (!resourcePath.startsWith("api/") && !resourcePath.startsWith("images/")
                                    && !resourcePath.startsWith("covers/")) {
                                return new ClassPathResource("/static/index.html");
                            }
                            return null;
                        }
                        return resource;
                    }
                });
    }
}
