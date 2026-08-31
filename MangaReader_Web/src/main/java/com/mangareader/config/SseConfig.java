package com.mangareader.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * SSE 配置：SseEmitter 注册表，管理多个客户端连接的生命周期
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@Configuration
public class SseConfig {

    /**
     * SSE 连接注册表
     */
    @Bean
    public SseEmitterRegistry sseEmitterRegistry() {
        return new SseEmitterRegistry();
    }

    /**
     * SSE 心跳调度器（15 秒发送一次 ping 防止代理断开）
     */
    @Bean
    public ScheduledExecutorService sseHeartbeatScheduler(SseEmitterRegistry registry) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "sse-heartbeat");
            t.setDaemon(true);
            return t;
        });
        scheduler.scheduleAtFixedRate(() -> {
            try {
                registry.sendHeartbeat();
            } catch (Exception e) {
                log.debug("SSE 心跳发送异常: {}", e.getMessage());
            }
        }, 15, 15, TimeUnit.SECONDS);
        return scheduler;
    }

    /**
     * SseEmitter 注册表：管理所有活跃的 SSE 连接
     */
    public static class SseEmitterRegistry {

        private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

        /**
         * 注册一个新的 SSE 连接
         */
        public SseEmitter register(String clientId, long timeout) {
            SseEmitter emitter = new SseEmitter(timeout);

            emitter.onCompletion(() -> {
                emitters.remove(clientId);
                log.debug("SSE 连接完成: {}", clientId);
            });
            emitter.onTimeout(() -> {
                emitters.remove(clientId);
                log.debug("SSE 连接超时: {}", clientId);
            });
            emitter.onError(e -> {
                emitters.remove(clientId);
                log.debug("SSE 连接异常: {}", clientId);
            });

            emitters.put(clientId, emitter);
            log.info("SSE 连接注册: {}, 当前连接数: {}", clientId, emitters.size());
            return emitter;
        }

        /**
         * 向所有连接的客户端发送事件
         */
        public void sendEvent(String eventName, Object data) {
            emitters.forEach((id, emitter) -> {
                try {
                    emitter.send(SseEmitter.event()
                            .name(eventName)
                            .data(data));
                } catch (IOException e) {
                    emitters.remove(id);
                    log.debug("SSE 事件发送失败，已移除连接: {}", id);
                }
            });
        }

        /**
         * 发送心跳
         */
        public void sendHeartbeat() {
            emitters.forEach((id, emitter) -> {
                try {
                    emitter.send(SseEmitter.event().comment("ping"));
                } catch (IOException e) {
                    emitters.remove(id);
                    log.debug("SSE 心跳失败，已移除连接: {}", id);
                }
            });
        }

        public int getConnectionCount() {
            return emitters.size();
        }
    }
}
