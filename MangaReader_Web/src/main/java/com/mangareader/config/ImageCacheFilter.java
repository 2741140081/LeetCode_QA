package com.mangareader.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 图片资源缓存过滤器
 * 为 /images/** 和 /covers/** 路径下的静态资源添加 HTTP 缓存头，
 * 利用浏览器缓存减少重复请求，大幅提升图片加载速度。
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@Component
@Order(1)
public class ImageCacheFilter implements Filter {

    /** 图片缓存有效期：7 天（秒） */
    private static final int CACHE_MAX_AGE = 7 * 24 * 60 * 60;

    /** ETag 使用的日期格式 */
    private static final DateTimeFormatter HTTP_DATE_FORMAT =
            DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
                    .withZone(ZoneId.of("GMT"));

    @Value("${manga.storage.image-path}")
    private String imagePath;

    @Value("${manga.storage.root}")
    private String storageRoot;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();

        // 仅对图片和封面资源添加缓存头
        if (uri.startsWith("/images/") || uri.startsWith("/covers/")) {
            // 设置强缓存
            httpResponse.setHeader("Cache-Control", "public, max-age=" + CACHE_MAX_AGE);
            httpResponse.setHeader("Pragma", "cache");

            // 根据文件最后修改时间生成 ETag / Last-Modified
            String filePath = resolveFilePath(uri);
            if (filePath != null) {
                File file = new File(filePath);
                if (file.exists() && file.isFile()) {
                    long lastModified = file.lastModified();
                    long contentLength = file.length();

                    // Last-Modified
                    String lastModifiedStr = HTTP_DATE_FORMAT.format(
                            java.time.Instant.ofEpochMilli(lastModified).atZone(ZoneId.of("GMT")));
                    httpResponse.setHeader("Last-Modified", lastModifiedStr);

                    // ETag 基于文件大小和修改时间
                    String etag = "\"" + Long.toHexString(lastModified) + "-" + Long.toHexString(contentLength) + "\"";
                    httpResponse.setHeader("ETag", etag);

                    // 处理 If-None-Match（304 协商缓存）
                    String ifNoneMatch = httpRequest.getHeader("If-None-Match");
                    if (etag.equals(ifNoneMatch)) {
                        httpResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                        return;
                    }

                    // 处理 If-Modified-Since（304 协商缓存）
                    String ifModifiedSince = httpRequest.getHeader("If-Modified-Since");
                    if (ifModifiedSince != null && ifModifiedSince.equals(lastModifiedStr)) {
                        httpResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                        return;
                    }
                }
            }

            // 添加 Vary 头，支持压缩协商
            httpResponse.setHeader("Vary", "Accept-Encoding");
        }

        chain.doFilter(request, response);
    }

    /**
     * 将请求 URI 映射到本地文件路径
     */
    private String resolveFilePath(String uri) {
        try {
            if (uri.startsWith("/images/")) {
                String relative = uri.substring("/images/".length());
                return imagePath + File.separator + relative.replace("/", File.separator);
            } else if (uri.startsWith("/covers/")) {
                String relative = uri.substring("/covers/".length());
                return storageRoot + File.separator + relative.replace("/", File.separator);
            }
        } catch (Exception e) {
            log.debug("解析文件路径失败: {}", uri, e);
        }
        return null;
    }
}
