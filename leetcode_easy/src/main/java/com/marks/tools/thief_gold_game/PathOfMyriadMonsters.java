package com.marks.tools.thief_gold_game;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.kkplatform.entity.ItemInfo;
import com.marks.tools.thief_gold_game.controller.PathOfMyriadMonstersController;
import com.marks.utils.LogUtil;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

public class PathOfMyriadMonsters {
    public static void main(String[] args) throws AWTException {
        LogUtil.info("=== 启动万物之怪路径游戏流程 ===");
        long startTime = System.currentTimeMillis();
        // 初始化自动化工具
        ImageRecognitionAutomation automation = new ImageRecognitionAutomation();
        
        // 创建游戏控制器
        PathOfMyriadMonstersController controller = new PathOfMyriadMonstersController(automation);
        
        // 定义s1子文件夹的物品处理
        List<ItemInfo> originalItemInfos = new ArrayList<>();
        List<ItemInfo> modifiedItemInfos = new ArrayList<>();
        int n = 28;
        for (int i = 0; i < n; i++) {
            ItemInfo k118 = new ItemInfo("k118", 4);
            originalItemInfos.add(k118);
        }
        for (int i = 0; i < n/2; i++) {
            ItemInfo k228 = new ItemInfo("k228", 70);
            modifiedItemInfos.add(k228);
        }
        for (int i = 0; i < (n + 1) / 2; i++) {
            // 另外一半的物品处理
            ItemInfo k228 = new ItemInfo("k228", 74);
            modifiedItemInfos.add(k228);
        }
        // 执行s1子文件夹的物品修改流程
        LogUtil.info("开始处理s0子文件夹物品, 将 k118 转为 kXxx");
        controller.startGame(originalItemInfos, modifiedItemInfos, "s0/");

        // 执行s0子文件夹的物品修改流程
        LogUtil.info("开始处理s1子文件夹物品, 将 kXxx 转为 k118");
//        controller.startGame(modifiedItemInfos, originalItemInfos, "s1/");



        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        LogUtil.info("=== 万物之怪路径游戏流程执行完毕, 总耗时: {} s ===", spendTime/1000);
    }
}