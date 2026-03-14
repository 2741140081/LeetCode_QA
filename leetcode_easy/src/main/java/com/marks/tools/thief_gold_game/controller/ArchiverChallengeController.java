package com.marks.tools.thief_gold_game.controller;


import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.thief_gold_game.entity.ImageInfo;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
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
    // 存档建筑图片基础路径
    private static final String ARCHIVER_BUILDING_BASE = "common/archiver_building_";

    // 存档文件夹基础路径
    private static final String ARCHIVER_FOLDER_BASE = "archiver_";

    // 存档编号数组
    private static final int[] ARCHIVER_NUMBERS = {1, 2, 3, 4, 5};

    // 坐标微调参数
    private static final int X_OFFSET = -30;  // X 坐标向左微调 150px
    private static final int SELECTION_SIZE = 30;  // 圈选区域大小 100x100px
    private static final int SELECTION_TIME = 1000;  // 圈选耗时 1s
    private static final int WAIT_AFTER_WIN = 1000;  // 胜利后等待 3s

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
            LogUtil.info("开始挑战 BOSS");
            for (int number : ARCHIVER_NUMBERS) {
                LogUtil.info("开始挑战第{}个存档 BOSS", number);
                String bossFolder = ARCHIVER_FOLDER_BASE + number;
                challengeArchiver(number, bossFolder);
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
            int maxTimeout = 30000;
            for (int number : ARCHIVER_NUMBERS) {
                String buildingImage = ARCHIVER_BUILDING_BASE + number;
                Point buildingPoint = findAndAdjustPoint(buildingImage, maxTimeout);

                if (buildingPoint != null && circleSelectAndNumber(buildingPoint, number)) {
                    LogUtil.info("第{}个存档建筑圈选并编号成功", number);
                } else {
                    LogUtil.error("第{}个存档建筑处理失败", number);
                    return false;
                }
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
    private Point findAndAdjustPoint(String buildingImage, int maxTimeout) {
        LogUtil.info("正在查找建筑图片：{}", buildingImage);

        if (!waitForImage(buildingImage, maxTimeout, 1000)) {
            LogUtil.error("未找到建筑图片：{}", buildingImage);
            return null;
        }
        Point originalPoint = findImage(buildingImage);

        LogUtil.info("找到建筑原始坐标：({}, {})", originalPoint.x, originalPoint.y);

        // 微调坐标左移：，X 减少 150px, Y 不变
        Point adjustedPoint = new Point(originalPoint.x + X_OFFSET, originalPoint.y + X_OFFSET );
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
    private void challengeArchiver(int archiverNumber, String bossFolder) {
        LogUtil.info("=== 开始挑战存档{}的 BOSS ===", archiverNumber);

        try {
            // 1. 选择对应的存档建筑
            pressNumber(archiverNumber);
            automation.delay(500);
            LogUtil.info("已选择存档建筑编号：{}", archiverNumber);

            // 2. 获取文件夹中的所有 BOSS 图片
            List<ImageInfo> bossImages = getBossImagesFromFolder(bossFolder);
            if (bossImages.isEmpty()) {
                LogUtil.error("存档{}文件夹中没有 BOSS 图片", bossFolder);
            }

            LogUtil.info("存档{}共有{}个 BOSS 需要挑战", bossFolder, bossImages.size());

            // 3. 依次挑战每个 BOSS
            for (ImageInfo bossImage : bossImages) {
                LogUtil.info("正在挑战 BOSS: {}", bossImage);

                if (!defeatBoss(bossImage)) {
                    LogUtil.error("挑战 BOSS {} 失败", bossImage);
                } else {
                    LogUtil.info("BOSS {} 挑战成功", bossImage);
                }

                automation.delay(500);
            }

        } catch (Exception e) {
            LogUtil.error("挑战存档 BOSS 异常：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 从文件夹中获取所有 BOSS 图片
     * @param folderName 文件夹名称
     * @return BOSS 图片列表
     */
    private List<ImageInfo> getBossImagesFromFolder(String folderName) {
        String fullPath = TEMPLATE_DIR + File.separator + folderName;
        File folder = new File(fullPath);

        if (!folder.exists() || !folder.isDirectory()) {
            LogUtil.error("文件夹不存在：{}", fullPath);
            return Arrays.asList();
        }

        // 过滤出所有.png 图片文件
        String[] imageFiles = folder.list((dir, name) -> name.toLowerCase().endsWith(".png"));

        if (imageFiles == null) {
            LogUtil.error("无法读取文件夹内容：{}", fullPath);
            return Arrays.asList();
        }

        // 构建 ImageInfo 列表
        List<ImageInfo> imageInfos = new ArrayList<>();
        for (String fileName : imageFiles) {
            String imageName = fileName.replace(".png", "");
            String imagePath = fullPath + File.separator + fileName;
            imageInfos.add(new ImageInfo(imageName, imagePath));
        }

        // 去除扩展名，返回图片名称列表
        return imageInfos;
    }

    /**
     * 击败单个 BOSS
     * @param bossInfo BOSS 图片名称
     * @return 是否成功
     */
    private boolean defeatBoss(ImageInfo bossInfo) {
        // 查找并点击 BOSS 图片
        Point bossPoint = findImage(bossInfo.getImageName(), bossInfo.getImagePath(), false);
        if (bossPoint == null) {
            LogUtil.error("未找到 BOSS 图片：{}", bossInfo.getImageName());
            return false;
        }

        LogUtil.info("找到 BOSS 坐标：({}, {})", bossPoint.x, bossPoint.y);

        // 点击 BOSS
        automation.click(bossPoint.x, bossPoint.y);
        automation.delay(CLICK_DELAY);

        LogUtil.info("已点击 BOSS: {}", bossInfo.getImageName());
        return true;
    }
}
