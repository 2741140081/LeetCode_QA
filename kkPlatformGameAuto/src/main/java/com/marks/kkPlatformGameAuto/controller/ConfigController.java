package com.marks.kkPlatformGameAuto.controller;

import com.marks.kkPlatformGameAuto.config.ClickerConfig;
import com.marks.kkPlatformGameAuto.service.ClickerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置控制器
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Slf4j
@RestController
@RequestMapping("/api/clicker")
public class ConfigController {

    @Autowired
    private ClickerService clickerService;

    @Autowired
    private ClickerConfig clickerConfig;

    /**
     * 获取连点器配置
     */
    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> getClickerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("enabled", clickerConfig.isEnabled());
        config.put("startKey", clickerConfig.getStartKey());
        config.put("interval", clickerConfig.getInterval());
        config.put("itemBarEnabled", clickerConfig.getItemBar().isEnabled());
        config.put("itemBarInterval", clickerConfig.getItemBar().getInterval());
        config.put("itemBarSlots", clickerConfig.getItemBar().getSlots());
        config.put("enabledSlots", clickerConfig.getItemBar().getEnabledSlots());

        return ResponseEntity.ok(config);
    }

    /**
     * 更新连点器配置
     */
    @PostMapping("/config")
    public ResponseEntity<Map<String, Object>> updateClickerConfig(
            @RequestBody Map<String, Object> config) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 更新连点器配置
            if (config.containsKey("interval")) {
                int interval = (int) config.get("interval");
                clickerService.setInterval(interval);
            }

            // 更新物品栏配置
            if (config.containsKey("itemBarInterval")) {
                int itemBarInterval = (int) config.get("itemBarInterval");
                clickerService.setItemBarInterval(itemBarInterval);
            }

            // 更新启用的物品槽位
            if (config.containsKey("enabledSlots")) {
                @SuppressWarnings("unchecked")
                List<Integer> enabledSlots = (List<Integer>) config.get("enabledSlots");
                clickerConfig.getItemBar().setEnabledSlots(enabledSlots);
            }

            response.put("success", true);
            response.put("message", "配置更新成功");
        } catch (Exception e) {
            log.error("更新配置失败", e);
            response.put("success", false);
            response.put("message", "更新配置失败：" + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 启动/停止连点器
     */
    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Object>> toggleClicker(
            @RequestBody Map<String, Boolean> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean enable = request.get("enable");
            if (enable) {
                clickerService.startLeftClicker();
                response.put("message", "连点器已启动");
            } else {
                clickerService.stopLeftClicker();
                response.put("message", "连点器已停止");
            }
            response.put("success", true);
        } catch (Exception e) {
            log.error("切换连点器状态失败", e);
            response.put("success", false);
            response.put("message", "操作失败：" + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 启动/停止物品栏自动使用
     */
    @PostMapping("/itembar/toggle")
    public ResponseEntity<Map<String, Object>> toggleItemBar(
            @RequestBody Map<String, Boolean> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean enable = request.get("enable");
            if (enable) {
                clickerService.startItemBarAutoUse();
                response.put("message", "物品栏自动使用已启动");
            } else {
                clickerService.stopItemBarAutoUse();
                response.put("message", "物品栏自动使用已停止");
            }
            response.put("success", true);
        } catch (Exception e) {
            log.error("切换物品栏状态失败", e);
            response.put("success", false);
            response.put("message", "操作失败：" + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 获取连点器状态
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getClickerStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("clickerRunning", clickerService.isRunning());
        status.put("itemBarRunning", clickerService.isRunning());
        status.put("interval", clickerConfig.getInterval());
        status.put("itemBarInterval", clickerConfig.getItemBar().getInterval());

        return ResponseEntity.ok(status);
    }
}
