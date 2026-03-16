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
    private static final int[] ARCHIVER_NUMBERS = {1, 2, 3, 4, 5, 6};

    // 坐标微调参数
    private static final int POINT_OFFSET = -30;  // X 坐标向左微调 150px
    private static final int SELECTION_SIZE = 30;  // 圈选区域大小 100x100px
    private static final int SELECTION_TIME = 1000;  // 圈选耗时 1s
    private static final int WAIT_AFTER_WIN = 1000;  // 胜利后等待 3s

    public ArchiverChallengeController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 依次挑战所有存档 BOSS
     * 1. 提高健壮性, 找到什么点什么, 没找到的不处理
     * 2. 修改返回值, 程序不需要返回值
     */
    public void challengeAllArchivers() {
        LogUtil.info("=== 开始挑战所有存档 BOSS ===");

        try {
            // 等待 3 秒，让游戏自动切换到存档区域视角
            LogUtil.info("等待 3 秒，游戏自动切换到存档区域视角...");
            automation.delay(WAIT_AFTER_WIN);

            // 1. 找到并圈选所有存档建筑, 返回成功列表
            List<Integer> successNumbers = selectAndNumberAllArchivers();

            // 2. 依次挑战每个存档 BOSS, 挑战 successNumbers 列表的 BOSS
            LogUtil.info("开始挑战 BOSS");
            for (int number : successNumbers) {
                LogUtil.info("开始挑战第{}个存档 BOSS", number);
                String bossFolder = ARCHIVER_FOLDER_BASE + number;
                challengeArchiver(number, bossFolder);
                // 每个存档挑战boss 间隔 10s
                automation.delay(10000);
            }

        } catch (Exception e) {
            LogUtil.error("挑战存档 BOSS 异常：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 找到并圈选所有存档建筑，然后编号
     * 1. 返回成功的编号列表
     * @return 是否成功
     */
    private List<Integer> selectAndNumberAllArchivers() {
        LogUtil.info("=== 开始圈选所有存档建筑 ===");
        List<Integer> successNumbers = new ArrayList<>();
        try {
            int maxTimeout = 30000;
            for (int number : ARCHIVER_NUMBERS) {
                String buildingImage = ARCHIVER_BUILDING_BASE + number;
                Point buildingPoint = findAndAdjustPoint(buildingImage, maxTimeout);

                if (buildingPoint != null && circleSelectAndNumber(buildingPoint, number)) {
                    LogUtil.info("第{}个存档建筑圈选并编号成功", number);
                    successNumbers.add(number);
                } else {
                    LogUtil.error("第{}个存档建筑处理失败", number);
                    // 不需要处理, 记录日志即可,继续处理下一个
                }
            }

            LogUtil.info("所有存档建筑圈选并编号完成");
        } catch (Exception e) {
            LogUtil.error("圈选存档建筑异常：{}", e.getMessage());
            e.printStackTrace();
            // 返回空集合
            return new ArrayList<>();
        }
        return successNumbers;
    }

    /**
     * 找到建筑图片并调整坐标
     * 1. 修改流程, 由于之前使用 waitForImage 方法，导致图片一开始找到, 但是由于图片是动态变换图,
     * 后续使用 findImage 方法无法找到, 导致程序报错.
     * 2. 解决方案改为使用 getPointByWait 方法, 直接返回坐标点, 判定坐标点是 null 来确定是否成功
     * @param buildingImage 建筑图片名称
     * @return 调整后的坐标点
     */
    private Point findAndAdjustPoint(String buildingImage, int maxTimeout) {
        LogUtil.info("正在查找建筑图片：{}", buildingImage);
        Point originalPoint = getPointByWait(buildingImage, maxTimeout, 500);
        if (originalPoint == null ) {
            LogUtil.error("未找到建筑图片：{}", buildingImage);
            return null;
        }
        // 微调坐标 TODO: INC20260316001
        offsetPoint(originalPoint, POINT_OFFSET, POINT_OFFSET);
        return originalPoint;
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
            // 计算圈选终点：对角线方向 (startPoint.x + 100, startPoint.y + 100) TODO: INC20260316001
            Point endPoint = getPointByOffset(startPoint, SELECTION_SIZE, SELECTION_SIZE);
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
            LogUtil.info("已选择存档建筑编号：{}", archiverNumber);

            // 2. 获取文件夹中的所有 BOSS 图片
            List<ImageInfo> bossImages = getBossImagesFromFolder(bossFolder);
            if (bossImages.isEmpty()) {
                LogUtil.error("存档{}文件夹中没有 BOSS 图片", bossFolder);
            }

            LogUtil.info("存档{}共有{}个 BOSS 需要挑战", bossFolder, bossImages.size());

            // 3. 依次挑战每个 BOSS
            for (ImageInfo bossImage : bossImages) {
                if (!defeatBoss(bossImage)) {
                    LogUtil.error("挑战 BOSS {} 失败", bossImage);
                }
                automation.delay(CLICK_DELAY);
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
        // 点击 BOSS
        automation.click(bossPoint.x, bossPoint.y);

        LogUtil.info("已点击 BOSS: {}", bossInfo.getImageName());
        return true;
    }
}
