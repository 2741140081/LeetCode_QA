package com.marks.kkPlatformGameAuto.controller;

import com.marks.kkPlatformGameAuto.entity.ThiefGameEntity;
import com.marks.kkPlatformGameAuto.service.ThiefGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ThiefGameController </p>
 * <p>描述: 小偷游戏的控制层, 接受前端传入的参数 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/18 17:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@RestController
@RequestMapping("/api/thief-game")
public class ThiefGameController {
    @Autowired
    private ThiefGameService thiefGameService;

    /**
     * 执行小偷游戏完整流程
     * @param config 游戏配置参数
     * @return 执行结果
     */
    @PostMapping("/execute")
    public ResponseEntity<Map<String, Object>> executeThiefGame(@RequestBody ThiefGameEntity config) {
        Map<String, Object> response = new HashMap<>();

        try {
            log.info("=== 收到小偷游戏执行请求 ===");
            log.info("游戏难度：{}, 游戏模式：{}", config.getDifficulty(), config.getGameMode());

            boolean success = thiefGameService.executeThiefGameFlow(config);

            if (success) {
                response.put("code", 200);
                response.put("message", "小偷游戏流程执行成功");
                response.put("success", true);
                log.info("小偷游戏流程执行成功");
            } else {
                response.put("code", 500);
                response.put("message", "小偷游戏流程执行失败");
                response.put("success", false);
                log.error("小偷游戏流程执行失败");
            }

        } catch (Exception e) {
            log.error("执行小偷游戏异常：{}", e.getMessage(), e);
            response.put("code", 500);
            response.put("message", "执行异常：" + e.getMessage());
            response.put("success", false);
        }

        return ResponseEntity.ok(response);
    }
}
