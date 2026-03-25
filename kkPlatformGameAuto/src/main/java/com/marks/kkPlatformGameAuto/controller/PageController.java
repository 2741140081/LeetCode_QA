package com.marks.kkPlatformGameAuto.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：PageController </p>
 * <p>描述：页面控制器，用于返回前端页面 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/23
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Controller
@RequestMapping("/page")
public class PageController {

    /**
     * 打开小偷游戏管理页面
     * @return 页面视图
     */
    @GetMapping("/thief-game")
    public String openThiefGamePage() {
        log.info("访问小偷游戏管理页面");
        return "thief-game/thief-game-manager";
    }

    /**
     * 打开连点器配置页面
     * @return 页面视图
     */
    @GetMapping("/clicker")
    public String openClickerPage() {
        log.info("访问连点器配置页面");
        return "clicker/clicker-config";
    }
}
