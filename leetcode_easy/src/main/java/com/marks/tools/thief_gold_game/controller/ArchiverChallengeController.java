package com.marks.tools.thief_gold_game.controller;


import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ArchiverChallengeController </p>
 * <p>描述: 存档 BOSS 挑战控制器，负责挑战 4 个存档 BOSS </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/12 11:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ArchiverChallengeController extends CommonController {
    private static final String ARCHIVER_BUILDING_1 = "common/archiver_building_1"; // 存档挑战1的建筑图片
    private static final String ARCHIVER_BUILDING_2 = "common/archiver_building_2";
    private static final String ARCHIVER_BUILDING_3 = "common/archiver_building_3";
    private static final String ARCHIVER_BUILDING_4 = "common/archiver_building_4";

    private static final String ARCHIVER_1_FOLDER = "archiver_1/"; // 存档挑战1的文件夹, 存放boss 图片
    private static final String ARCHIVER_2_FOLDER = "archiver_2/"; // 存档挑战2的文件夹, 存放boss 图片
    private static final String ARCHIVER_3_FOLDER = "archiver_3/"; // 存档挑战3的文件夹, 存放boss 图片
    private static final String ARCHIVER_4_FOLDER = "archiver_4/"; // 存档挑战4的文件夹, 存放boss 图片

    // 存档建筑编号（6-9）
    private static final int ARCHIVER_NUMBER_1 = 6;
    private static final int ARCHIVER_NUMBER_2 = 7;
    private static final int ARCHIVER_NUMBER_3 = 8;
    private static final int ARCHIVER_NUMBER_4 = 9;

    // 坐标微调参数
    private static final int X_OFFSET = -150;  // X 坐标向左微调 150px
    private static final int SELECTION_SIZE = 100;  // 圈选区域大小 100x100px
    private static final int SELECTION_TIME = 1000;  // 圈选耗时 1s
    private static final int WAIT_AFTER_WIN = 3000;  // 胜利后等待 3s

    public ArchiverChallengeController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 依次挑战所有存档 BOSS
     * @return 是否全部成功
     */
    public boolean challengeAllArchivers() {
        LogUtil.info("=== 开始挑战所有存档 BOSS ===");

        boolean allSuccess = true;

        try {
            // 等待 3 秒，让游戏自动切换到存档区域视角
            LogUtil.info("等待 3 秒，游戏自动切换到存档区域视角...");
            automation.delay(WAIT_AFTER_WIN);

            // 1. 找到并圈选所有存档建筑
            if (!selectAndNumberAllArchivers()) {
                LogUtil.error("圈选存档建筑失败");
                return false;
            }

            // 2. 依次挑战每个存档 BOSS
            LogUtil.info("开始挑战第 1 个存档 BOSS");
            if (!challengeArchiver(ARCHIVER_NUMBER_1, ARCHIVER_1_FOLDER)) {
                LogUtil.error("挑战第 1 个存档 BOSS 失败");
                allSuccess = false;
            }

            LogUtil.info("开始挑战第 2 个存档 BOSS");
            if (!challengeArchiver(ARCHIVER_NUMBER_2, ARCHIVER_2_FOLDER)) {
                LogUtil.error("挑战第 2 个存档 BOSS 失败");
                allSuccess = false;
            }

            LogUtil.info("开始挑战第 3 个存档 BOSS");
            if (!challengeArchiver(ARCHIVER_NUMBER_3, ARCHIVER_3_FOLDER)) {
                LogUtil.error("挑战第 3 个存档 BOSS 失败");
                allSuccess = false;
            }

            LogUtil.info("开始挑战第 4 个存档 BOSS");
            if (!challengeArchiver(ARCHIVER_NUMBER_4, ARCHIVER_4_FOLDER)) {
                LogUtil.error("挑战第 4 个存档 BOSS 失败");
                allSuccess = false;
            }

            if (allSuccess) {
                LogUtil.info("=== 所有存档 BOSS 挑战成功 ===");
            } else {
                LogUtil.warn("部分存档 BOSS 挑战失败");
            }

        } catch (Exception e) {
            LogUtil.error("挑战存档 BOSS 异常：{}", e.getMessage());
            e.printStackTrace();
            allSuccess = false;
        }

        return allSuccess;
    }

    /**
     * 找到并圈选所有存档建筑，然后编号
     * @return 是否成功
     */
    private boolean selectAndNumberAllArchivers() {
        LogUtil.info("=== 开始圈选所有存档建筑 ===");

        try {
            // 处理第 1 个存档建筑
            Point building1Point = findAndAdjustPoint(ARCHIVER_BUILDING_1);
            if (building1Point != null && circleSelectAndNumber(building1Point, ARCHIVER_NUMBER_1)) {
                LogUtil.info("第 1 个存档建筑圈选并编号成功");
            } else {
                LogUtil.error("第 1 个存档建筑处理失败");
                return false;
            }

            // 处理第 2 个存档建筑
            Point building2Point = findAndAdjustPoint(ARCHIVER_BUILDING_2);
            if (building2Point != null && circleSelectAndNumber(building2Point, ARCHIVER_NUMBER_2)) {
                LogUtil.info("第 2 个存档建筑圈选并编号成功");
            } else {
                LogUtil.error("第 2 个存档建筑处理失败");
                return false;
            }

            // 处理第 3 个存档建筑
            Point building3Point = findAndAdjustPoint(ARCHIVER_BUILDING_3);
            if (building3Point != null && circleSelectAndNumber(building3Point, ARCHIVER_NUMBER_3)) {
                LogUtil.info("第 3 个存档建筑圈选并编号成功");
            } else {
                LogUtil.error("第 3 个存档建筑处理失败");
                return false;
            }

            // 处理第 4 个存档建筑
            Point building4Point = findAndAdjustPoint(ARCHIVER_BUILDING_4);
            if (building4Point != null && circleSelectAndNumber(building4Point, ARCHIVER_NUMBER_4)) {
                LogUtil.info("第 4 个存档建筑圈选并编号成功");
            } else {
                LogUtil.error("第 4 个存档建筑处理失败");
                return false;
            }

            LogUtil.info("所有存档建筑圈选并编号完成");
            return true;

        } catch (Exception e) {
            LogUtil.error("圈选存档建筑异常：{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 找到建筑图片并调整坐标
     * @param buildingImage 建筑图片名称
     * @return 调整后的坐标点
     */
    private Point findAndAdjustPoint(String buildingImage) {
        LogUtil.info("正在查找建筑图片：{}", buildingImage);

        Point originalPoint = findImage(buildingImage);
        if (originalPoint == null) {
            LogUtil.error("未找到建筑图片：{}", buildingImage);
            return null;
        }

        LogUtil.info("找到建筑原始坐标：({}, {})", originalPoint.x, originalPoint.y);

        // 微调坐标：X 不变，Y 减少 150px
        Point adjustedPoint = new Point(originalPoint.x + X_OFFSET, originalPoint.y );
        LogUtil.info("调整后坐标：({}, {})", adjustedPoint.x, adjustedPoint.y);

        return adjustedPoint;
    }

    /**
     * 圈选建筑并编号
     * @param startPoint 起点坐标
     * @param number 编号
     * @return 是否成功
     */
    private boolean circleSelectAndNumber(Point startPoint, int number) {
        LogUtil.info("开始圈选建筑，起点：({}, {}), 编号：{}", startPoint.x, startPoint.y, number);

        try {
            // 计算圈选终点：对角线方向 (startPoint.x + 100, startPoint.y + 100)
            Point endPoint = new Point(startPoint.x + SELECTION_SIZE, startPoint.y + SELECTION_SIZE);
            LogUtil.info("圈选终点：({}, {})", endPoint.x, endPoint.y);

            // 执行圈选操作
            automation.moveMouseWithLeftUp(startPoint, endPoint, SELECTION_TIME);

            // 延迟 1s
            automation.delay(1000);

            // 给建筑编号
            selectNumber(number);
            LogUtil.info("建筑编号完成：{}", number);

            return true;

        } catch (Exception e) {
            LogUtil.error("圈选建筑异常：{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 挑战指定存档 BOSS
     * @param archiverNumber 存档建筑编号
     * @param bossFolder 存放 BOSS 图片的文件夹
     * @return 是否成功
     */
    private boolean challengeArchiver(int archiverNumber, String bossFolder) {
        LogUtil.info("=== 开始挑战存档{}的 BOSS ===", archiverNumber);

        try {
            // 1. 选择对应的存档建筑
            pressKey(archiverNumber);
            automation.delay(500);
            LogUtil.info("已选择存档建筑编号：{}", archiverNumber);

            // 2. 获取文件夹中的所有 BOSS 图片
            List<String> bossImages = getBossImagesFromFolder(bossFolder);
            if (bossImages.isEmpty()) {
                LogUtil.error("存档{}文件夹中没有 BOSS 图片", bossFolder);
                return false;
            }

            LogUtil.info("存档{}共有{}个 BOSS 需要挑战", bossFolder, bossImages.size());

            // 3. 依次挑战每个 BOSS
            boolean allBossDefeated = true;
            for (String bossImage : bossImages) {
                LogUtil.info("正在挑战 BOSS: {}", bossImage);

                if (!defeatBoss(bossImage)) {
                    LogUtil.error("挑战 BOSS {} 失败", bossImage);
                    allBossDefeated = false;
                } else {
                    LogUtil.info("BOSS {} 挑战成功", bossImage);
                }

                automation.delay(500);
            }

            if (allBossDefeated) {
                LogUtil.info("存档{}的所有 BOSS 挑战成功", bossFolder);
                return true;
            } else {
                LogUtil.warn("存档{}的部分 BOSS 挑战失败", bossFolder);
                return false;
            }

        } catch (Exception e) {
            LogUtil.error("挑战存档 BOSS 异常：{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从文件夹中获取所有 BOSS 图片
     * @param folderName 文件夹名称
     * @return BOSS 图片列表
     */
    private List<String> getBossImagesFromFolder(String folderName) {
        String fullPath = TEMPLATE_DIR + "/" + folderName;
        File folder = new File(fullPath);

        if (!folder.exists() || !folder.isDirectory()) {
            LogUtil.error("文件夹不存在：{}", fullPath);
            return Arrays.asList();
        }

        // 过滤出所有.png 图片文件
        String[] imageFiles = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".png");
            }
        });

        if (imageFiles == null) {
            LogUtil.error("无法读取文件夹内容：{}", fullPath);
            return Arrays.asList();
        }

        // 去除扩展名，返回图片名称列表
        return Arrays.stream(imageFiles)
                .map(name -> folderName + name.replace(".png", ""))
                .toList();
    }

    /**
     * 击败单个 BOSS
     * @param bossImage BOSS 图片名称
     * @return 是否成功
     */
    private boolean defeatBoss(String bossImage) {
        LogUtil.info("正在查找并点击 BOSS: {}", bossImage);

        // 查找并点击 BOSS 图片
        Point bossPoint = findImage(bossImage);
        if (bossPoint == null) {
            LogUtil.error("未找到 BOSS 图片：{}", bossImage);
            return false;
        }

        LogUtil.info("找到 BOSS 坐标：({}, {})", bossPoint.x, bossPoint.y);

        // 点击 BOSS
        automation.click(bossPoint.x, bossPoint.y);
        automation.delay(CLICK_DELAY);

        LogUtil.info("已点击 BOSS: {}", bossImage);
        return true;
    }
}
