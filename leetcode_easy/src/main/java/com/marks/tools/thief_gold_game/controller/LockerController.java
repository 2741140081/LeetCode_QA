package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LockerController </p>
 * <p>描述: 储物柜操作类，管理物品的拾取、丢弃和修改 </p>
 * 相关的流程如下
 * 1. 找到储物柜位置, 左键点击选中储物柜, 给储物柜进行编号(初始化操作)
 * 2. 按下键盘2, 选中储物柜, 找到并且返回红色/绿色宝箱坐标, 左键点击坐标, 拾取成功
 * 3. 切换窗口到修改器窗口,[修改器操作: 修改物品], 等待修改器返回结果. 传递的参数用List<String> list 传递,
 * 修改后的物品名称, 第一次传递list 大小为1, 第二次传递大小为5, 具体名称后续完善
 * 4. 切换到游戏主体窗口, 键盘按下2, 选中储物柜, 通过图片名称找到图片的坐标, 移动鼠标到坐标点, 右键点击, 移动鼠标到
 * 丢弃点2, 鼠标左键点击, 丢弃成功, 返回true 给到 ThiefController类中的方法
 * 5. 等待ThiefController类中的方法调用 LockerController类中的方法 buySkill方法
 * 6. 键盘按下2, 选中储物柜, 找到技能书位置, 左键点击技能书, 共点击5次, 间隔500ms. [切换到修改器窗口, 修改5个物品, 返回修改结果]
 * 7. 切换窗口到游戏主体窗口, 键盘按下2, 选中储物柜, 根据修改后的物品名称, 遍历循环, 通过图片名称找到图片的坐标, 移动鼠标到坐标点, 右键点击, 
 * 移动鼠标到丢弃点2, 鼠标左键点击, 丢弃成功. 返回结果值给 ThiefController类中的调用方法
 * 8. 储物柜流程执行完毕
 * 
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/12 11:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LockerController extends CommonController {
    // 储物柜编号
    private static final int LOCKER_NUMBER = 2;

    // 技能相关图片
    private static final String LOCKER_SKILL_BOOK = "skill_book";          // 晕锤技能书
    private static final String LOCKER_SKILL_BUTTON = "skill_button";      // 储物柜技能按钮

    // 宝箱图片
    private static final String GREEN_CHEST = "green_chest";               // 绿色宝箱
    private static final String RED_CHEST = "red_chest";                   // 红色宝箱

    // 物品栏相关
    private static final String LOCKER_ITEM_SLOT_PREFIX = "locker_item_";  // 储物柜物品栏前缀

    // 操作延迟配置（毫秒）
    private static final int SKILL_BOOK_DELAY = 500;                       // 技能书点击间隔
    private static final int ITEM_DROP_DELAY = 300;                        // 丢弃物品延迟

    // 修改器引用
    private ModifierController modifierController;

    public LockerController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 设置修改器控制器引用
     * @param modifierController 修改器控制器
     */
    public void setModifierController(ModifierController modifierController) {
        this.modifierController = modifierController;
    }

    /**
     * 储物柜初始化流程
     * 1. 找到储物柜并选中
     * 2. 给储物柜编号为 2
     * @return 是否成功
     */
    public boolean initialize() {
        LogUtil.info("=== 储物柜初始化流程开始 ===");

        // 1. 找到储物柜位置并点击
        Point lockerPoint = findImage("locker");
        if (lockerPoint == null) {
            LogUtil.error("未找到储物柜");
            return false;
        }

        LogUtil.info("找到储物柜坐标：({}, {})", lockerPoint.x, lockerPoint.y);
        automation.click(lockerPoint.x, lockerPoint.y);
        automation.delay(CLICK_DELAY);

        // 2. 给储物柜编号为 2
        selectNumber(LOCKER_NUMBER);

        LogUtil.info("储物柜初始化完成，编号为：{}", LOCKER_NUMBER);
        return true;
    }

    /**
     * 拾取丢弃点的物品
     * 先找绿色宝箱，如果没有则找红色宝箱
     * @return 是否成功
     */
    public boolean pickupItemFromDropPoint() {
        LogUtil.info("=== 储物柜拾取物品 ===");

        // 切换到储物柜
        switchToLocker();

        // 1. 先找绿色宝箱
        Point greenChestPoint = findImage(GREEN_CHEST);
        if (greenChestPoint != null) {
            LogUtil.info("找到绿色宝箱坐标：({}, {})", greenChestPoint.x, greenChestPoint.y);
            automation.click(greenChestPoint.x, greenChestPoint.y);
            automation.delay(CLICK_DELAY);
            LogUtil.info("绿色宝箱拾取成功");
            return true;
        }

        // 2. 绿色宝箱不存在，找红色宝箱
        LogUtil.info("未找到绿色宝箱，尝试查找红色宝箱...");
        Point redChestPoint = findImage(RED_CHEST);
        if (redChestPoint != null) {
            LogUtil.info("找到红色宝箱坐标：({}, {})", redChestPoint.x, redChestPoint.y);
            automation.click(redChestPoint.x, redChestPoint.y);
            automation.delay(CLICK_DELAY);
            LogUtil.info("红色宝箱拾取成功");
            return true;
        }

        // 3. 两种宝箱都不存在，报错
        LogUtil.error("未找到任何宝箱（绿色和红色都不存在），无法拾取物品");
        // TODO: 截图保存错误现场
        return false;
    }

    /**
     * 购买晕锤技能书
     * 点击 5 次技能书，每次间隔 500ms
     * @return 是否成功
     */
    public boolean buySkillBooks() {
        LogUtil.info("=== 储物柜购买晕锤技能书（5 次） ===");

        // 切换到储物柜
        switchToLocker();

        // 点击技能按钮
        Point skillButtonPoint = findImage(LOCKER_SKILL_BUTTON);
        if (skillButtonPoint == null) {
            LogUtil.error("未找到技能按钮");
            return false;
        }

        automation.click(skillButtonPoint.x, skillButtonPoint.y);
        automation.delay(CLICK_DELAY);

        // 购买 5 次技能书
        for (int i = 1; i <= 5; i++) {
            Point skillBookPoint = findImage(LOCKER_SKILL_BOOK);
            if (skillBookPoint == null) {
                LogUtil.error("第{}次购买：未找到技能书", i);
                return false;
            }

            LogUtil.info("第{}次点击技能书，坐标：({}, {})", i, skillBookPoint.x, skillBookPoint.y);
            automation.click(skillBookPoint.x, skillBookPoint.y);

            if (i < 5) {
                automation.delay(SKILL_BOOK_DELAY);
            }
        }

        LogUtil.info("晕锤技能书购买完成，共 5 本");
        return true;
    }

    /**
     * 修改物品（调用修改器）
     * @param itemNames 要修改的物品名称列表
     * @return 是否成功
     */
    // TODO: 需要与 ModifierController 配合
    public boolean modifyItems(List<String> itemNames) {
        LogUtil.info("=== 储物柜：修改物品，数量：{} ===", itemNames.size());

        if (modifierController == null) {
            LogUtil.error("ModifierController 未设置");
            return false;
        }

        try {
            // 切换到修改器窗口 TODO: 添加 switchToModifiersWindow 方法 在 CommonController 中
//            if (!switchToModifiersWindow()) {
//                LogUtil.error("切换到修改器窗口失败");
//                return false;
//            }

            // 调用修改器修改物品 TODO: 添加 modifyItems 方法 在 ModifierController 中
//            boolean result = modifierController.modifyItems(itemNames);

            // 切换回游戏窗口
            switchToGameWindow();
            automation.delay(CLICK_DELAY);

            // 返回结果, TODO
            return false;

        } catch (Exception e) {
            LogUtil.error("修改物品异常：{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 丢弃储物柜物品栏中的物品到丢弃点 2
     * @param itemName 物品名称（图片名）
     * @return 是否成功
     */
    public boolean dropItemFromLocker(String itemName) {
        LogUtil.info("=== 储物柜丢弃物品：{} ===", itemName);

        // 切换到储物柜
        switchToLocker();

        // 找到物品栏中的物品
        String itemImagePath = LOCKER_ITEM_SLOT_PREFIX + itemName;
        Point itemPoint = findImage(itemImagePath);
        if (itemPoint == null) {
            LogUtil.error("未找到物品：{}", itemImagePath);
            return false;
        }

        LogUtil.info("找到物品坐标：({}, {})", itemPoint.x, itemPoint.y);

        // 移动到物品坐标，右键点击
        automation.robot.mouseMove(itemPoint.x, itemPoint.y);
        automation.delay(ITEM_DROP_DELAY);
        automation.robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
        automation.delay(50);
        automation.robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
        automation.delay(ITEM_DROP_DELAY);

        // 移动到丢弃点 2
        Point dropPoint2 = findImage(DROP_POINT_2);
        if (dropPoint2 == null) {
            LogUtil.error("未找到丢弃点 2");
            return false;
        }

        LogUtil.info("移动到丢弃点 2 坐标：({}, {})", dropPoint2.x, dropPoint2.y);
        automation.click(dropPoint2.x, dropPoint2.y);
        automation.delay(CLICK_DELAY);

        LogUtil.info("物品{}已丢弃到丢弃点 2", itemName);
        return true;
    }

    /**
     * 批量丢弃 5 件物品到丢弃点 2
     * @param itemNames 物品名称列表
     * @return 是否成功
     */
    public boolean dropMultipleItems(List<String> itemNames) {
        LogUtil.info("=== 储物柜批量丢弃{}件物品 ===", itemNames.size());

        for (int i = 0; i < itemNames.size(); i++) {
            String itemName = itemNames.get(i);
            LogUtil.info("正在丢弃第{}件物品：{}", i + 1, itemName);

            if (!dropItemFromLocker(itemName)) {
                LogUtil.error("丢弃第{}件物品{}失败", i + 1, itemName);
                return false;
            }

            automation.delay(ITEM_DROP_DELAY);
        }

        LogUtil.info("批量丢弃完成，共{}件物品", itemNames.size());
        return true;
    }

    /**
     * 切换到储物柜（按数字 2）
     * @return 是否成功
     */
    public void switchToLocker() {
        LogUtil.info("=== 切换到储物柜（编号 2） ===");
        pressKey(LOCKER_NUMBER);
    }

    /**
     * 完整的储物柜第一次操作流程
     * （小偷第一次购买武器后的修改流程）
     * 1. 拾取小偷丢弃的物品
     * 2. 切换到修改器修改物品
     * 3. 将修改后的物品丢弃到丢弃点 2
     * @param targetItemName 目标物品名称
     * @return 是否成功
     */
    // TODO: 需要与 ThiefController 和 ModifierController 配合
    public boolean executeFirstModificationProcess(String targetItemName) {
        LogUtil.info("=== 储物柜第一次修改流程开始 ===");

        try {
            // 1. 拾取丢弃点的物品
            if (!pickupItemFromDropPoint()) {
                return false;
            }

            // 2. 修改物品（单个物品）
            List<String> itemNames = List.of(targetItemName);
            if (!modifyItems(itemNames)) {
                return false;
            }

            // 3. 将修改后的物品丢弃到丢弃点 2
            if (!dropItemFromLocker(targetItemName)) {
                return false;
            }

            LogUtil.info("储物柜第一次修改流程完成");
            return true;

        } catch (Exception e) {
            LogUtil.error("储物柜第一次修改流程异常：{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 完整的储物柜第二次操作流程
     * （等待小偷收集 1000 金币后的技能书修改流程）
     * 1. 购买 5 本晕锤技能书
     * 2. 切换到修改器修改 5 个物品
     * 3. 将 5 件修改后的物品丢弃到丢弃点 2
     * @param itemNames 5 个物品名称列表
     * @return 是否成功
     */
    // TODO: 需要与 ThiefController 和 ModifierController 配合
    public boolean executeSecondModificationProcess(List<String> itemNames) {
        LogUtil.info("=== 储物柜第二次修改流程开始（5 件物品） ===");

        try {
            if (itemNames.size() != 5) {
                LogUtil.error("物品数量必须为 5，实际：{}", itemNames.size());
                return false;
            }

            // 1. 购买 5 本技能书
            if (!buySkillBooks()) {
                return false;
            }

            // 2. 修改 5 个物品
            if (!modifyItems(itemNames)) {
                return false;
            }

            // 3. 批量丢弃 5 件物品
            if (!dropMultipleItems(itemNames)) {
                return false;
            }

            LogUtil.info("储物柜第二次修改流程完成");
            return true;

        } catch (Exception e) {
            LogUtil.error("储物柜第二次修改流程异常：{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
