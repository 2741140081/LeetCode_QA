package com.marks.tools.kkplatform;

import com.marks.tools.kkplatform.common.GameOperationCommon;
import com.marks.utils.LogUtil;

import java.awt.Point;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: PrepareRoom </p>
 * <p>描述: 游戏的准备房间页面
 * 1. 通过图片识别(最佳缩放和迭代二分查找最佳), 找到按钮"开始游戏"
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/5 11:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class PrepareRoom extends GameOperationCommon {
    private static final String START_GAME_BUTTON = "start_game_button";

    public PrepareRoom(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 在准备房间页面点击"开始游戏"按钮
     *
     * @return 是否成功
     */
    public boolean startGame() {
        LogUtil.info("=== 准备房间：寻找开始游戏按钮 ===");

        if (findAndClickImage(START_GAME_BUTTON)) {
            LogUtil.info("已点击开始游戏按钮，等待游戏加载...");
            automation.delay(60000);
            LogUtil.info("游戏加载完成");
            return true;
        }

        LogUtil.error("未找到开始游戏按钮");
        return false;
    }

    /**
     * 使用最佳缩放比例查找图片
     *
     * @param imageName 图片名称
     * @return 找到的坐标
     */
    public Point findImageWithBestScale(String imageName) {
        double[] scales = {0.8, 0.9, 1.0, 1.1, 1.2};

        for (double scale : scales) {
            Point point = automation.findImage(imageName);
            if (point != null) {
                LogUtil.info("在缩放比例 " + scale + " 下找到图片：" + imageName);
                return point;
            }
        }

        return null;
    }

    /**
     * 迭代二分查找最佳匹配
     *
     * @param imageName 图片名称
     * @return 找到的坐标
     */
    public Point findImageWithBinarySearch(String imageName) {
        int minScale = 80;
        int maxScale = 120;

        while (minScale <= maxScale) {
            int midScale = (minScale + maxScale) / 2;
            Point point = automation.findImage(imageName);

            if (point != null) {
                LogUtil.info("二分查找成功，缩放比例：" + midScale + "%");
                return point;
            }

            if (midScale < 100) {
                minScale = midScale + 1;
            } else {
                maxScale = midScale - 1;
            }
        }

        return null;
    }
}
