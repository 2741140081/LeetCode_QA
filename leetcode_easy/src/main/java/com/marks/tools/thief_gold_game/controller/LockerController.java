package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Arrays;
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
    private static final String LOCKER_SKILL_BOOK = "2/skill_book";          // 晕锤技能书

    // 操作延迟配置（毫秒）
    private static final int SKILL_BOOK_DELAY = 500;                       // 技能书点击间隔
    private static final int ITEM_DROP_DELAY = 300;                        // 丢弃物品延迟

    // 修改器引用
    private ModifierController modifierController;

    public LockerController(ImageRecognitionAutomation automation, ModifierController modifierController) {
        super(automation);
        this.modifierController = modifierController;
    }

    public boolean initialize() {
        // 找到储物柜位置并点击
        Point lockerPoint = findImage(LOCKER_BUILDING);
        if (lockerPoint == null) {
            LogUtil.error("未找到储物柜");
            return false;
        }
        automation.click(lockerPoint.x, lockerPoint.y);
        automation.delay(CLICK_DELAY);
        // 对储物柜进行编号
        selectNumber(LOCKER_NUMBER);
        return true;
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
        // 延迟1s
        automation.delay(1000);
        Point skillBookPoint = findImage(LOCKER_SKILL_BOOK);
        if (skillBookPoint == null) {
            LogUtil.error("未找到技能书");
            return false;
        }
        // 购买 5 次技能书
        for (int i = 0; i < 5; i++) {
            automation.click(skillBookPoint.x, skillBookPoint.y);
            // 每次购买后延迟1s
            automation.delay(SKILL_BOOK_DELAY);
        }

        LogUtil.info("晕锤技能书购买完成，共 5 本");
        return true;
    }

    /**
     * 修改物品（调用修改器）
     * @param itemNames 要修改的物品名称列表
     * @return 是否成功
     */
    public boolean modifyItems(List<String> itemNames) {
        LogUtil.info("=== 储物柜：修改物品，数量：{" + itemNames.size() + "} ===");

        if (modifierController == null) {
            LogUtil.error("ModifierController 未设置");
            return false;
        }
        return modifierController.modifyItems(itemNames);
    }

    /**
     * 丢弃储物柜物品栏中的物品到小偷物品栏中
     * @param itemName 物品名称（图片名）
     * @return 是否成功
     */
    public boolean dropItemFromLocker(String itemName) {
        // 切换到储物柜
        switchToLocker();
        // 找到物品栏中的物品, 找到物品证明修改生效
        String itemImagePath = COMMON_FOLDER + itemName;
        Point itemPoint = findImage(itemImagePath);
        if (itemPoint == null) {
            LogUtil.error("未找到物品：{}", itemImagePath);
            return false;
        }

        LogUtil.info("找到物品坐标：({}, {})", itemPoint.x, itemPoint.y);

        // 找到小偷左上角的标记点坐标
        Point flagPoint = findImage(THIEF_LEFT_TOP_FLAG);
        // 判断坐标点是否存在
        if (flagPoint == null) {
            LogUtil.error("未找到小偷左上角的标记点");
            return false;
        }

        // 移动到物品坐标，右键点击
        automation.rightClick(itemPoint.x, itemPoint.y);
        automation.delay(CLICK_DELAY);
        // 移动到 flagPoint, 点击
        automation.click(flagPoint.x, flagPoint.y);
        // 放入小偷储物栏成功
        // 延迟1s
        automation.delay(1000);
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
        pressNumber(LOCKER_NUMBER);
    }

    /**
     * 完整的储物柜第一次操作流程
     * （小偷第一次购买武器后的修改流程）
     * 1. 拾取小偷丢弃的物品(舍弃, 现在是小偷直接将物品放入储物柜中, 不需要储物柜进行拾取操作)
     * 2. 切换到修改器修改物品
     * 3. 将修改后的物品丢弃到丢弃点 2
     * @return 是否成功
     */
    public boolean executeFirstModificationProcess() {
        LogUtil.info("=== 储物柜第一次修改流程开始 ===");
        // 初始化储物柜
        if (!initialize()) {
            LogUtil.error("储物柜初始化失败");
            return false;
        }

        try {
            // 1. 切换到储物柜
            switchToLocker();

            // 2. 修改物品, 将 父类中的 FIRST_ITEM_NAMES 是第一次需要修改的物品
            // 将string[] 转为 List
            List<String> itemNames = Arrays.stream(FIRST_ITEM_NAMES).toList();
            if (!modifyItems(itemNames)) {
                return false;
            }

            // 3. 将修改后的物品丢弃到小偷物品栏中
            for (String itemName : itemNames) {
                if (!dropItemFromLocker(itemName)) {
                    return false;
                }
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
     * @param delayTime 延迟时间
     * @return 是否成功
     */
    public boolean executeSecondModificationProcess(int delayTime) {
        LogUtil.info("=== 储物柜第二次修改流程开始, 等待{} s 获取金币===", delayTime);
        try {
            // 延迟delayTime ms, 等待获取足够金币
            automation.delay(delayTime);

            // 1. 切换到储物柜
            switchToLocker();

            // 2. 修改物品, 将 父类中的 SECOND_ITEM_NAMES 是第二次需要修改的物品
            // 将string[] 转为 List
            List<String> itemNames = Arrays.stream(SECOND_ITEM_NAMES).toList();
            if (itemNames.size() != 5) {
                LogUtil.error("物品数量必须为 5，实际：{" + itemNames.size() + "}");
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
