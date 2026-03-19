package com.marks.kkPlatformGameAuto.service.impl;

import com.marks.kkPlatformGameAuto.config.PrepareRoomConfig;
import com.marks.kkPlatformGameAuto.config.properties.GameAutoProperties;
import com.marks.kkPlatformGameAuto.config.properties.ThiefGameProperties;
import com.marks.kkPlatformGameAuto.entity.ThiefGameEntity;
import com.marks.kkPlatformGameAuto.handler.commonHandler.NumberingHandler;
import com.marks.kkPlatformGameAuto.service.*;
import com.marks.kkPlatformGameAuto.util.ImagePathUtils;
import com.marks.kkPlatformGameAuto.util.InputController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ThiefGameServiceImpl </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Service
public class ThiefGameServiceImpl implements ThiefGameService {
    @Autowired
    private GameAutoProperties gameAutoProperties;

    @Autowired
    private ThiefGameProperties thiefGameProperties;

    @Autowired
    private WindowSwitcherService windowSwitcherService;

    @Autowired
    private PrepareRoomService prepareRoomService;

    @Autowired
    private ImageRecognitionService imageRecognitionService;

    @Autowired
    private PrepareRoomConfig prepareRoomConfig;

    @Autowired
    private InputController input;

    @Autowired
    private NumberingHandler numberingHandler;

    @Autowired
    private ImagePathUtils imagePathUtils;

    @Autowired
    private HeroOperationService heroOperationService;

    // 游戏开始时间
    private long gameStartTime;

    private int thiefNumber;
    private int lockerNumber;
    private int finalBossBuildingNumber;
    private int attributeEnhanceBuildingNumber;


    @Override
    public boolean executeThiefGameFlow(ThiefGameEntity thiefGameEntity) {
        log.info("=== 开始执行小偷游戏流程 ===");
        log.info("游戏难度：{}, 游戏模式：{}, 挑战数量：{}, 存档建筑数量：{}",
                thiefGameEntity.getDifficulty(),
                thiefGameEntity.getGameMode(),
                thiefGameEntity.getChallenges().size(),
                thiefGameEntity.getArchiverBuildings().size());
        // 准备房间服务开启游戏
        if (!prepareRoomService.clickStartGameButton()) {
            log.error("准备房间服务开启游戏失败");
            return false;
        }
        // 从配置文件获取长等待时间
        int longWaitTime = gameAutoProperties.getTimeout().getLongTimeout();
        // 等待游戏开始
        input.delay(longWaitTime);
        // 执行难度选择
        if (!selectDifficultyAndChallenges(thiefGameEntity.getDifficulty(), thiefGameEntity.getGameMode(), thiefGameEntity.getChallenges())) {
            log.error("难度选择失败");
            return false;
        }
        // 记录游戏开始时间
        gameStartTime = System.currentTimeMillis();
        // 执行游戏主流程

        return false;
    }

    @Override
    public boolean selectDifficultyAndChallenges(int difficulty, int gameMode, List<Integer> challenges) {
        return false;
    }

    @Override
    public boolean challengeArchiverBosses(List<Integer> archiverBuildings) {
        return false;
    }

    /**
     * 步骤 4: 执行游戏主流程
     */
    private boolean executeMainGame() {
        log.info("=== 步骤 4: 游戏主体流程开始 ===");
        String commonImageDir = thiefGameProperties.getCommonImageDir();
        int defaultTimeout = gameAutoProperties.getDefaultTimeout();
        int delayTime = gameAutoProperties.getInterval();
        // 给人物和建筑物编号
        if (!numberingThiefAndBuildings(commonImageDir)) {
            log.error("给小偷和建筑编号失败");
            return false;
        }
        // 切换到小偷
        input.pressNumber(thiefNumber);
        // 获取属性强化建筑物坐标
        String attributeEnhanceBuildingImagePath = imagePathUtils.buildImagePathWithExtension(commonImageDir, String.valueOf(attributeEnhanceBuildingNumber));
        // 获取中心点坐标
        Point attributeEnhanceBuildingCenter = imageRecognitionService.findImageCenter(attributeEnhanceBuildingImagePath, defaultTimeout, delayTime);
        if (attributeEnhanceBuildingCenter == null) {
            log.error("属性强化建筑物坐标获取失败");
            return false;
        }
        // 获取A键 keyCode
        int attackKeyCode = input.getLetterKeyCode('A');
        if (!heroOperationService.useTargetedSkillByKey(attackKeyCode, attributeEnhanceBuildingCenter)) {
            log.error("攻击金矿失败");
            return false;
        }

        return true;
    }

    private boolean numberingThiefAndBuildings(String commonImageDir) {
        // 1. 给小偷编号, 获取小偷编号数字
        this.thiefNumber = thiefGameProperties.getThiefNumber();
        // 调用handler
        if (!numberingHandler.numbering(thiefNumber)) {
            log.error("给小偷编号失败");
            return false;
        }
        // 2. 给最终boss挑战编号, 获取最终boss挑战编号数字
        this.finalBossBuildingNumber = thiefGameProperties.getFinalBossBuildingNumber();
        // 获取完整路径, commonImageDir + imageName + .png

        String finalBossBuildingNumberImagePath = imagePathUtils.buildImagePathWithExtension(commonImageDir, String.valueOf(finalBossBuildingNumber));
        if (!numberingHandler.numbering(finalBossBuildingNumberImagePath, finalBossBuildingNumber)) {
            log.error("给最终boss挑战编号失败");
            return false;
        }
        // 3. 给属性强化编号编号, 获取属性强化编号数字
        this.attributeEnhanceBuildingNumber = thiefGameProperties.getAttributeEnhanceBuildingNumber();
        String attributeEnhanceBuildingNumberImagePath = imagePathUtils.buildImagePathWithExtension(commonImageDir, String.valueOf(attributeEnhanceBuildingNumber));
        if (!numberingHandler.numbering(attributeEnhanceBuildingNumberImagePath, attributeEnhanceBuildingNumber)) {
            log.error("给属性强化编号编号失败");
            return false;
        }
        return true;
    }
}
