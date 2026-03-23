package com.marks.kkPlatformGameAuto.service.impl;

import com.marks.kkPlatformGameAuto.config.PrepareRoomConfig;
import com.marks.kkPlatformGameAuto.config.ThiefGameConfig;
import com.marks.kkPlatformGameAuto.config.properties.GameAutoProperties;
import com.marks.kkPlatformGameAuto.config.properties.ThiefGameProperties;
import com.marks.kkPlatformGameAuto.entity.ThiefGameEntity;
import com.marks.kkPlatformGameAuto.handler.commonHandler.NumberingHandler;
import com.marks.kkPlatformGameAuto.service.*;
import com.marks.kkPlatformGameAuto.state.GameStateManager;
import com.marks.kkPlatformGameAuto.util.FileUtils;
import com.marks.kkPlatformGameAuto.util.ImagePathUtils;
import com.marks.kkPlatformGameAuto.util.InputController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
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

    @Autowired
    private ModifierService modifierService;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private GameStateManager gameStateManager;

    // 游戏开始时间
    private long gameStartTime;

    private int thiefNumber;
    private int lockerNumber;
    private int finalBossBuildingNumber;
    private int attributeEnhanceBuildingNumber;
    private ThiefGameConfig.GameImageConfig gameImageConfig;


    @Override
    public boolean executeThiefGameFlow(ThiefGameEntity thiefGameEntity) {
        log.info("=== 开始执行小偷游戏流程 ===");
        // 设置游戏状态为运行中
        gameStateManager.startGame();
        List<Integer> archiverBuildings = thiefGameEntity.getArchiverBuildings();
        log.info("游戏难度：{}, 游戏模式：{}, 挑战数量：{}, 存档建筑数量：{}",
                thiefGameEntity.getDifficulty(),
                thiefGameEntity.getGameMode(),
                thiefGameEntity.getChallenges().size(),
                archiverBuildings.size());
        // 准备房间服务开启游戏
        if (!prepareRoomService.clickStartGameButton()) {
            log.error("准备房间服务开启游戏失败");
            return false;
        }
        // 从配置文件获取长等待时间
        int longWaitTime = gameAutoProperties.getTimeout().getLongTime();
        // 等待游戏开始
        input.delay(longWaitTime);
        // 执行难度选择
        if (!selectDifficultyAndChallenges(thiefGameEntity.getDifficulty(), thiefGameEntity.getGameMode(), thiefGameEntity.getChallenges())) {
            log.error("难度选择失败");
            return false;
        }
        // [暂停点]selectDifficultyAndChallenges() 执行成功后
        if (!gameStateManager.checkShouldPause()) {
            log.warn("游戏在难度选择完成后被终止");
            return false;
        }
        // 记录游戏开始时间
        gameStartTime = System.currentTimeMillis();
        // 执行游戏主流程
        if (!executeMainGame()) {
            log.error("游戏主流程失败");
            return false;
        }
        // 执行最后 boss 提前挑战
        if (!executeFinalBossChallenge()) {
            return false;
        }
        // [暂停点]执行最后 boss 提前挑战后
        if (!gameStateManager.checkShouldPause()) {
            log.warn("游戏在最终 BOSS 挑战前被终止");
            return false;
        }

        // 挑战存档
        return challengeArchiverBosses(archiverBuildings);
    }

    @Override
    public boolean selectDifficultyAndChallenges(int difficulty, int gameMode, List<Integer> challenges) {
        return false;
    }

    @Override
    public boolean challengeArchiverBosses(List<Integer> archiverBuildings) {
        log.info("=== 步骤 6: 挑战存档 ===");
        List<Integer> successChallenges = new ArrayList<>();
        // 获取存档的前缀名称
        String prefix = thiefGameProperties.getThiefGameConfig().getFolder().getArchiverPrefix();
        // 获取 commonImageDir
        String commonImageDir = thiefGameProperties.getCommonImageDir();
        // 获取长超时时间
        int longTime = gameAutoProperties.getTimeout().getLongTime();
        for (Integer challenge : archiverBuildings) {
            String challengeName = prefix + challenge;
            String challengeImagePath = imagePathUtils.buildImagePathWithExtension(commonImageDir, challengeName);
            if (!imageRecognitionService.findAndClickImage(challengeImagePath, longTime)) {
                log.error("点击存档建筑{}图片失败", challenge);
            } else {
                // 进行编号操作
                if (!numberingHandler.numbering(challengeImagePath, challenge)) {
                    log.error("存档建筑{}编号操作失败", challenge);
                } else {
                    successChallenges.add(challenge);
                }
            }
        }
        // 判断 successChallenges 是否为空
        int defaultTimeout = gameAutoProperties.getDefaultTimeout();
        if (!successChallenges.isEmpty()) {
            log.info("成功挑战的存档数量：{}", successChallenges.size());
            // 遍历 successChallenges, 挑战存档boss
            for (Integer challenge : successChallenges) {
                String currArchiverPath = thiefGameProperties.getArchiverImageDir(challenge);
                List<String> bossImagePath = fileUtils.getAllFilePathsInDirectory(currArchiverPath);
                for (String imagePath : bossImagePath) {
                    imageRecognitionService.findAndClickImage(imagePath, defaultTimeout);
                }
                // 每个存档挑战间隔, 等待长时间
                input.delay(longTime);
            }
            // 等待存档挑战完成
            int thiefGameArchiverTime = thiefGameProperties.getThiefGameConfig().getThiefGameArchiverTime();
            input.delay(thiefGameArchiverTime);
            return true;
        }
        return false;
    }
    /**
     * 挑战指定存档 BOSS
     * @param archiverNumber 存档建筑编号
     * @param archiverFolderPath 存放 存档BOSS 图片的文件夹
     * @return 是否成功
     */
    private void challengeArchiver(int archiverNumber, String archiverFolderPath) {
        log.info("=== 挑战指定存档建筑{} ===", archiverNumber);
        // 按下 archiverNumber
        input.pressNumber(archiverNumber);
        // 从 archiverFolderPath 文件夹提取所有文件
    }

    private boolean executeFinalBossChallenge() {
        log.info("=== 步骤 5: 最终 BOSS 挑战 ===");
        // 切换到最后boss 挑战处
        input.pressNumber(finalBossBuildingNumber);
        String commonImageDir = thiefGameProperties.getCommonImageDir();
        // 获取当前时间
        long currentTime = System.currentTimeMillis();
        // 从配置文件获取游戏主体运行时间
        long thiefGameRunTime = thiefGameProperties.getThiefGameConfig().getThiefGameRunTime();
        // 计算剩余等待时间
        long waitTime = currentTime - gameStartTime - thiefGameRunTime;
        log.info("等待时间：{}, 开启最终boss挑战", waitTime);
        input.delay((int) waitTime);
        // 获取中等等待时间
        int mediumTime = gameAutoProperties.getWaitTime().getMediumTime();
        input.delay(mediumTime);
        // 获取最终boss 图片 并点击
        String finalBossImage = gameImageConfig.getFinalBossBuildingImage();
        String finalBossImagePath = imagePathUtils.buildImagePathWithExtension(commonImageDir, finalBossImage);
        // 获取默认等待时间
        int defaultTimeout = gameAutoProperties.getDefaultTimeout();
        if (!imageRecognitionService.findAndClickImage(finalBossImagePath, defaultTimeout)) {
            log.error("点击最终boss 图片失败");
            return false;
        }

        // 获取长等待时间
        int longWaitTime = gameAutoProperties.getTimeout().getLongTime();

        // 检查 Victory Button 是否出现
        String victoryImage = gameImageConfig.getVictoryImage();
        String victoryImagePath = imagePathUtils.buildImagePathWithExtension(commonImageDir, victoryImage);
        // 获取小偷游戏失败时间
        int thiefGameFailureTime = thiefGameProperties.getThiefGameConfig().getThiefGameFailureTime();
        boolean isVictory = false;
        currentTime = System.currentTimeMillis();
        while (!isVictory && System.currentTimeMillis() - currentTime < thiefGameFailureTime) {
            isVictory = imageRecognitionService.findImage(victoryImagePath, defaultTimeout);
            // 每隔 longWaitTime 毫秒检查一次
            input.delay(longWaitTime);
        }
        // 通过 isVictory 判断游戏是否成功
        if (!isVictory) {
            log.error("最终boss 挑战失败");
        } else {
            log.info("最终boss 挑战成功");
        }

        return isVictory;
    }

    /**
     * 步骤 4: 执行游戏主流程
     */
    private boolean executeMainGame() {
        log.info("=== 步骤 4: 游戏主体流程开始 ===");
        String commonImageDir = thiefGameProperties.getCommonImageDir();
        int defaultTimeout = gameAutoProperties.getDefaultTimeout();
        int delayTime = gameAutoProperties.getInterval();
        this.gameImageConfig = thiefGameProperties.getThiefGameConfig().getGameImage();
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
        Point actualGoldMineCenter = new Point(attributeEnhanceBuildingCenter.x + thiefGameProperties.getGoldMineOffset().x,
                attributeEnhanceBuildingCenter.y + thiefGameProperties.getGoldMineOffset().y);
        // 获取A键 keyCode
        char attackSKey = thiefGameProperties.getThiefGameConfig().getAttackSKey();
        int attackKeyCode = input.getLetterKeyCode(attackSKey);
        if (!heroOperationService.useTargetedSkillByKey(attackKeyCode, actualGoldMineCenter)) {
            log.error("攻击金矿失败");
            return false;
        }

        // 移动视角的储物柜, 选中储物柜并编号
        int moveDurationTime = gameAutoProperties.getOperation().getMoveDuration();
        input.moveViewDown(moveDurationTime);
        // 找到储物柜并点击, 先获取路径
        String lockerImagePath = imagePathUtils.buildImagePathWithExtension(commonImageDir, String.valueOf(lockerNumber));
        if (!imageRecognitionService.findAndClickImage(lockerImagePath, defaultTimeout)) {
            log.error("找到储物柜并点击失败");
            return false;
        }
        // 给储物柜编号
        if (!numberingHandler.numbering(lockerNumber)) {
            log.error("给储物柜编号失败");
            return false;
        }

        // [暂停点]执行物品修改和转移流程前
        if (!gameStateManager.checkShouldPause()) {
            log.warn("游戏在物品修改和转移前被终止");
            return false;
        }


        // 执行物品修改和转移流程（第一次和第二次）
        List<List<String>> modifyItemBatches = new ArrayList<>();
        modifyItemBatches.add(thiefGameProperties.getFirstModifyItems());
        modifyItemBatches.add(thiefGameProperties.getSecondModifyItems());

        for (int i = 0; i < modifyItemBatches.size(); i++) {
            List<String> batchItemNames = modifyItemBatches.get(i);
            log.info("=== 第{}次物品修改和转移 ===", i + 1);

            // 使用储物柜技能并修改物品
            if (!useLockerSkillAndModifyItems(batchItemNames)) {
                log.error("第{}次使用储物柜技能并修改物品失败", i + 1);
                return false;
            }

            // 从储物柜转移物品到小偷
            if (!transferItemsFromLockerToThief(batchItemNames, defaultTimeout, delayTime)) {
                log.error("第{}次物品转移失败", i + 1);
                return false;
            }
            // [暂停点]每次遍历的执行后
            if (!gameStateManager.checkShouldPause()) {
                log.warn("游戏在第{}次物品操作后被终止", i + 1);
                return false;
            }
        }
        // 属性强化之前需要开启10连点击
        char tenClickSKey = thiefGameProperties.getThiefGameConfig().getTenClickSKey();
        input.pressKey(input.getLetterKeyCode(tenClickSKey));

        // 执行属性强化操作
        if (!executeAttributeEnhancement()) {
            return false;
        }

        // 执行小偷吞噬装备方法, 获取装备吞噬次数
        int devourEquipmentTimes = thiefGameProperties.getThiefGameConfig().getDevourEquipmentTimes();
        // 获取第三次装备itemNames
        List<String> thirdModifyItemNames = thiefGameProperties.getThirdModifyItems();
        // 执行吞噬装备方法, for 循环
        for (int i = 0; i < devourEquipmentTimes; i++) {
            log.info("=== 第{}次吞噬装备 ===", i);
            // 修改小偷物品栏物品，并丢弃
            if (!modifyThiefItemsAndDiscard(commonImageDir, defaultTimeout, delayTime)) {
                return false;
            }
            // 执行 useLockerSkillAndModifyItems 方法
            if (!useLockerSkillAndModifyItems(thirdModifyItemNames)) {
                return false;
            }
            // 执行 transferItemsFromLockerToThief 方法
            if (!transferItemsFromLockerToThief(thirdModifyItemNames, defaultTimeout, delayTime)) {
                return false;
            }
            // 第 i 次吞噬装备完成
            log.info("第{}次吞噬装备完成", i);
            // [暂停点] log.info("第{}次吞噬装备完成", i); 之后
            if (!gameStateManager.checkShouldPause()) {
                log.warn("游戏在第{}次吞噬装备后被终止", i + 1);
                return false;
            }
        }

        // 使用储物柜的寒冰技能
        String lockerIceImage = gameImageConfig.getLockerIceHaloSkillImage();
        String lockerImageDir = thiefGameProperties.getLockerImageDir();
        String lockerIceImagePath = imagePathUtils.buildImagePathWithExtension(lockerImageDir, lockerIceImage);
        if (!imageRecognitionService.findAndClickImage(lockerIceImagePath, defaultTimeout)) {
            log.error("使用储物柜寒冰技能失败");
        }

        // 执行属性强化操作
        if (!executeAttributeEnhancement()) {
            return false;
        }
        // 执行锁定攻击间隔, 调用modifierService.lockAttackInterval
        modifierService.lockAttackInterval();
        log.info("=== 步骤 4: 游戏主体流程结束 ===");
        return true;
    }


    /**
     * 使用储物柜技能并修改物品
     * @param itemNames 要修改的物品名称列表
     * @return true 如果执行成功，否则 false
     */
    private boolean useLockerSkillAndModifyItems(List<String> itemNames) {
        log.info("=== 使用储物柜技能并修改物品 ===");
        int n = itemNames.size();
        // 1. 获取储物柜技能图片路径
        String lockerSkillImage = gameImageConfig.getLockerSkillImage();
        String lockerImageDir = thiefGameProperties.getLockerImageDir();
        String lockerSkillImagePath = imagePathUtils.buildImagePathWithExtension(lockerImageDir, lockerSkillImage);
        // 获取点击间隔
        int interval = gameAutoProperties.getInterval();
        // 2. 使用非指向性技能（储物柜技能）
        if (!heroOperationService.useNonTargetedSkillByImage(lockerSkillImagePath, n, interval)) {
            log.error("使用储物柜技能失败");
            return false;
        }
        log.info("储物柜技能使用成功");

        // 3. 调用修改器方法修改物品
        if (!modifierService.modifyItems(itemNames)) {
            log.error("物品修改失败");
            return false;
        }
        log.info("物品修改成功，修改物品数量：{}", itemNames.size());

        return true;
    }


    /**
     * 选中储物柜并将物品转移到小偷
     *
     * @param itemNames      物品名称列表
     * @param defaultTimeout 默认超时时间 (毫秒)
     * @param delayTime      图片识别间隔时间 (毫秒)
     * @return true 如果转移成功，否则 false
     */
    private boolean transferItemsFromLockerToThief(List<String> itemNames, int defaultTimeout, int delayTime) {
        log.info("=== 开始从储物柜转移物品到小偷 ===");

        // 1. 选中储物柜
        input.pressNumber(lockerNumber);

        // 2. 构建物品完整路径列表
        List<String> itemPaths = new ArrayList<>();
        // 获取装备目录 和 小偷目录
        String equipmentImageDir = thiefGameProperties.getEquipmentImageDir();
        String thiefImageDir = thiefGameProperties.getThiefHeroImageDir();
        for (String itemName : itemNames) {
            String itemImagePath = imagePathUtils.buildImagePathWithExtension(equipmentImageDir, itemName);
            itemPaths.add(itemImagePath);
        }

        // 3. 获取左上角目标点坐标（小偷头像）
        String leftTopImage = gameImageConfig.getThiefLeftTopImage();
        String leftTopImagePath = imagePathUtils.buildImagePathWithExtension(thiefImageDir, leftTopImage);

        // 4. 识别图片并获取中心点作为转移物品的目标点坐标
        Point leftTopCenter = imageRecognitionService.findImageCenter(leftTopImagePath, defaultTimeout, delayTime);
        if (leftTopCenter == null) {
            log.error("获取左上角小偷头像目标点坐标失败");
            return false;
        }

        // 5. 执行物品转移
        if (!heroOperationService.transferItems(itemPaths, leftTopCenter)) {
            log.error("物品转移失败");
            return false;
        }

        log.info("物品转移完成，转移物品数量：{}", itemNames.size());
        return true;
    }


    /**
     * 修改小偷物品栏物品并丢弃
     * @param commonImageDir 公共图片目录
     * @param defaultTimeout 默认超时时间 (毫秒)
     * @param delayTime 图片识别间隔时间 (毫秒)
     * @return true 如果执行成功，否则 false
     */
    private boolean modifyThiefItemsAndDiscard(String commonImageDir, int defaultTimeout, int delayTime) {
        log.info("=== 修改小偷物品栏物品并丢弃 ===");

        // 1. 切换到小偷主体
        input.pressNumber(thiefNumber);

        // 2. 获取默认修改物品列表并执行修改
        List<String> defaultModifyItems = thiefGameProperties.getDefaultModifyItems();
        if (!modifierService.modifyItems(defaultModifyItems)) {
            log.error("修改小偷物品栏物品失败");
            return false;
        }
        log.info("小偷物品栏物品修改成功，修改物品数量：{}", defaultModifyItems.size());

        // 3. 获取完美丢弃点坐标
        String dropImage = gameImageConfig.getThiefPerfectDiscardPointImage();
        String dropImagePath = imagePathUtils.buildImagePathWithExtension(commonImageDir, dropImage);

        Point perfectDropPoint = imageRecognitionService.findImageCenter(dropImagePath, defaultTimeout, delayTime);
        if (perfectDropPoint == null) {
            log.error("获取完美丢弃点坐标失败");
            return false;
        }
        log.info("完美丢弃点坐标：({}, {})", perfectDropPoint.x, perfectDropPoint.y);

        // 4. 使用指向性技能（丢弃技能）
        String thiefDropSkillImage = gameImageConfig.getThiefDropSkillImage();
        // 获取小偷目录
        String thiefImageDir = thiefGameProperties.getThiefHeroImageDir();
        String thiefDropSkillImagePath = imagePathUtils.buildImagePathWithExtension(thiefImageDir, thiefDropSkillImage);

        if (!heroOperationService.useTargetedSkillByImage(thiefDropSkillImagePath, perfectDropPoint)) {
            log.error("小偷使用丢弃操作失败");
            return false;
        }
        log.info("丢弃操作成功");

        return true;
    }

    /**
     * 执行属性强化操作
     * 流程：选中属性强化建筑 -> 识别攻击/射程等强化选项图片 -> 点击强化
     * @return true 如果执行成功，否则 false
     */
    private boolean executeAttributeEnhancement() {
        log.info("=== 执行属性强化操作 ===");
        input.pressNumber(attributeEnhanceBuildingNumber);

        // 1. 选中属性强化建筑
        input.pressNumber(attributeEnhanceBuildingNumber);
        // 2. 获取强化建筑目录
        String enhanceBuildingImageDir = thiefGameProperties.getEnhanceBuildingImageDir();

        // 3. 识别并点击攻击强化选项, 获取攻击图片名称
        String attackEnhanceImage = gameImageConfig.getAttackSpeedUpImage();
        String rangeUpImage = gameImageConfig.getRangeUpImage();
        String multiShotUpImage = gameImageConfig.getMultiShotUpImage();
        // 获取属性强化次数
        ThiefGameConfig thiefGameConfig = thiefGameProperties.getThiefGameConfig();
        int attackEnhanceTimes = thiefGameConfig.getAttackSpeedUpTimes();
        int rangeUpTimes = thiefGameConfig.getRangeUpTimes();
        int multiShotUpTimes = thiefGameConfig.getMultiShotUpTimes();
        String attackEnhanceImagePath = imagePathUtils.buildImagePathWithExtension(enhanceBuildingImageDir, attackEnhanceImage);
        String rangeUpImagePath = imagePathUtils.buildImagePath(enhanceBuildingImageDir, rangeUpImage);
        String multiShotUpImagePath = imagePathUtils.buildImagePath(enhanceBuildingImageDir, multiShotUpImage);

        // 获取点击间隔
        int interval = gameAutoProperties.getInterval();
        // 使用非指向性技能, 强化各项属性
        if (!heroOperationService.useNonTargetedSkillByImage(attackEnhanceImagePath, attackEnhanceTimes, interval)) {
            log.error("强化攻击速度属性失败");
            return false;
        }

        if (!heroOperationService.useNonTargetedSkillByImage(rangeUpImagePath, rangeUpTimes, interval)) {
            log.error("强化射程属性失败");
            return false;
        }

        if (!heroOperationService.useNonTargetedSkillByImage(multiShotUpImagePath, multiShotUpTimes, interval)) {
            log.error("强化多重射击属性失败");
            return false;
        }

        log.info("属性强化操作完成");
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
        // 2. 获取中等等待时间, 用于等待游戏内提示文字消失, 方便识别后续建筑物图片
        int waitTime = gameAutoProperties.getWaitTime().getMediumTime();
        input.delay(waitTime);

        // 3. 给最终boss挑战编号, 获取最终boss挑战编号数字
        this.finalBossBuildingNumber = thiefGameProperties.getFinalBossBuildingNumber();
        // 获取完整路径, commonImageDir + imageName + .png

        String finalBossBuildingNumberImagePath = imagePathUtils.buildImagePathWithExtension(commonImageDir, String.valueOf(finalBossBuildingNumber));
        if (!numberingHandler.numbering(finalBossBuildingNumberImagePath, finalBossBuildingNumber)) {
            log.error("给最终boss挑战编号失败");
            return false;
        }
        // 4. 给属性强化编号编号, 获取属性强化编号数字
        this.attributeEnhanceBuildingNumber = thiefGameProperties.getAttributeEnhanceBuildingNumber();
        String attributeEnhanceBuildingNumberImagePath = imagePathUtils.buildImagePathWithExtension(commonImageDir, String.valueOf(attributeEnhanceBuildingNumber));
        if (!numberingHandler.numbering(attributeEnhanceBuildingNumberImagePath, attributeEnhanceBuildingNumber)) {
            log.error("给属性强化编号编号失败");
            return false;
        }
        this.lockerNumber = thiefGameProperties.getLockerNumber();

        return true;
    }
}
