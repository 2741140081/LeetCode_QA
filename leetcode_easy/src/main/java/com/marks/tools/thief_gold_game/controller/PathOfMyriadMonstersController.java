package com.marks.tools.thief_gold_game.controller;


import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.kkplatform.WindowSwitcherUtils;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.util.List;

import static com.marks.tools.thief_gold_game.controller.CommonController.COMMON_FOLDER;

public class PathOfMyriadMonstersController {
    private ImageRecognitionAutomation automation;
    private WindowSwitcherUtils windowSwitcher;
    private ModifierController modifierController;

    public PathOfMyriadMonstersController(ImageRecognitionAutomation automation) {
        LogUtil.info("初始化 PathOfMyriadMonstersController");
        this.automation = automation;
        initializeControllers();
    }

    /**
     * 初始化所有控制器
     */
    private void initializeControllers() {
        this.windowSwitcher = WindowSwitcherUtils.getInstance(); // 获取窗口单例
        this.modifierController = new ModifierController(automation);
    }

    /**
     * 完整的游戏流程入口
     * 1. 不要准备房间, 不需要开始游戏和选择难度
     * 2. 主要功能是将物品进行修改, 流程如下
     * 3. 切换到游戏窗口, 通过图片识别, 找到仓库中待修改的物品, 点击物品, 物品会转移到人物的装备栏
     * 4. 切换到修改器窗口, 进行修改操作, 修改后的名称为 modifiedName.get(i); 修改操作完成后, 切换到游戏窗口
     * 5. 修改后的物品在人物的装备栏, 找到物品并且右键双击, 将物品转移到仓库
     * 6. 重复上述过程3~5, 知道 originalName 遍历完成
     * 7. 还是需要优化一下, 添加数量处理
     */
    public void startGame(List<String> originalName, List<String> modifiedName, String subFolder) {
        LogUtil.info("=== 开始游戏流程 ===");
        
        if (originalName.size() != modifiedName.size()) {
            LogUtil.error("原始名称列表和修改名称列表大小不匹配");
            return;
        }
        
        try {
            for (int i = 0; i < originalName.size(); i++) {
                String originalItem = originalName.get(i);
                String modifiedItem = modifiedName.get(i);
                
                // 1. 切换到游戏窗口，找到仓库中的物品并转移到装备栏
                modifierController.switchToGameWindow();
                Point itemPoint = findAndClickImageInLocker(originalItem, subFolder);
                if (itemPoint == null) {
                    LogUtil.error("未找到物品：{}", originalItem);
                    continue;
                }
                
                // 2. 切换到修改器窗口进行修改操作
                modifierController.modifyItems(List.of(modifiedItem));

                automation.delay(500);
                
                // 3. 切换回游戏窗口，将修改后的物品转移回仓库
                modifierController.switchToGameWindow();
                Point modifiedPoint = findAndClickImageInLocker(modifiedItem, subFolder);
                if (modifiedPoint == null) {
                    LogUtil.error("未找到物品：{}", modifiedPoint);
                    continue;
                }
                rightDoubleClick(modifiedPoint.x, modifiedPoint.y);
            }
            
            LogUtil.info("=== 游戏流程完成 ===");
        } catch (Exception e) {
            LogUtil.error("游戏流程异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 在仓库中查找并点击指定物品
     * @param itemName 物品名称
     * @return 物品坐标点，未找到返回null
     */
    private Point findAndClickImageInLocker(String itemName, String subFolder) {
        LogUtil.info("在仓库中查找物品：{}", itemName);
        
        // 构建图片路径
        String imagePath = COMMON_FOLDER + subFolder + itemName;
        Point point = null;
        for (int i = 0; i < 5; i++) {
            point = automation.findImage(imagePath, false);
            // 5次重试次数
            if (point != null) {
                break;
            }
        }
        if (point == null) {
            LogUtil.error("未找到物品：{}", itemName);
            return null;
        }
        
        // 点击物品，转移到装备栏
        automation.click(point.x, point.y);
        automation.delay(200);
        return point;
    }
    
    /**
     * 右键双击指定坐标
     * @param x X坐标
     * @param y Y坐标
     */
    private void rightDoubleClick(int x, int y) {
        // 执行右键双击
        automation.rightClick(x, y);
        automation.rightClick(x, y);
    }
}
