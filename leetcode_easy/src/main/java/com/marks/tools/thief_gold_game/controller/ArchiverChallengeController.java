package com.marks.tools.thief_gold_game.controller;


import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.utils.LogUtil;
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
    private static final String ARCHIVER_1 = "archiver_1/archiver_building_1";
    private static final String ARCHIVER_2 = "archiver_2/archiver_building_2";
    private static final String ARCHIVER_3 = "archiver_3/archiver_building_3";
    private static final String ARCHIVER_4 = "archiver_4/archiver_building_4";

    private static final String ARCHIVER_CHALLENGE_BTN = "archiver_1/challenge_btn";

    public ArchiverChallengeController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 点击存档挑战建筑
     * @param archiverIndex 存档建筑索引（1-4）
     * @return 是否成功
     */
    public boolean clickArchiverBuilding(int archiverIndex) {
        LogUtil.info("=== 点击存档挑战建筑 " + archiverIndex + " ===");

        String imagePath;
        switch (archiverIndex) {
            case 1:
                imagePath = ARCHIVER_1;
                break;
            case 2:
                imagePath = ARCHIVER_2;
                break;
            case 3:
                imagePath = ARCHIVER_3;
                break;
            case 4:
                imagePath = ARCHIVER_4;
                break;
            default:
                LogUtil.error("无效的存档建筑索引：" + archiverIndex);
                return false;
        }

        return findAndClickImage(imagePath);
    }

    /**
     * 点击存档 BOSS 挑战按钮
     * @return 是否成功
     */
    public boolean clickArchiverChallenge() {
        LogUtil.info("=== 点击存档 BOSS 挑战按钮 ===");
        return findAndClickImage(ARCHIVER_CHALLENGE_BTN);
    }

    /**
     * 挑战单个存档 BOSS
     * @param archiverIndex 存档建筑索引
     * @return 是否成功
     */
    public boolean challengeSingleArchiver(int archiverIndex) {
        LogUtil.info("=== 挑战存档 BOSS " + archiverIndex + " ===");

        if (!clickArchiverBuilding(archiverIndex)) {
            return false;
        }

        automation.delay(500);

        return clickArchiverChallenge();
    }

    /**
     * 依次挑战所有 4 个存档 BOSS
     * @return 是否全部成功
     */
    public boolean challengeAllArchivers() {
        LogUtil.info("=== 开始挑战所有存档 BOSS ===");

        boolean allSuccess = true;

        for (int i = 1; i <= 4; i++) {
            LogUtil.info("挑战第 " + i + " 个存档 BOSS");

            if (!challengeSingleArchiver(i)) {
                allSuccess = false;
                LogUtil.error("挑战存档 BOSS " + i + " 失败");
            }

            automation.delay(1000);
        }

        return allSuccess;
    }

    /**
     * 等待 3 分钟左右结束游戏
     */
    public void waitForCompletion() {
        LogUtil.info("=== 等待 3 分钟，让小偷 kill 所有存档 BOSS ===");
        automation.delay(3 * 60 * 1000);
        LogUtil.info("等待完成");
    }

    /**
     * 执行完整的存档挑战流程
     */
    public void executeArchiverChallenge() {
        LogUtil.info("=== 执行存档挑战流程 ===");

        challengeAllArchivers();
        waitForCompletion();

        LogUtil.info("存档挑战流程完成");
    }
}
