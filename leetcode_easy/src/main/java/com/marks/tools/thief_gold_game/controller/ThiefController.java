package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.kkplatform.SpecialHandel;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ThiefController </p>
 * <p>描述: 小偷控制器</p>
 * 当 DifficultyController 执行难度选择流程后, 默认当前小偷是已选中状态
 * 1. 先给小偷编号1.
 * 2. 通过图片识别找到金矿坐标
 * 3. 按下 A 键, 鼠标移动到金矿坐标点, 左键点击金矿, 释放 A 键
 * 4. 等待1分钟, 通过图片匹配找到商店坐标, 左键点击商店, 购买物品
 * 5. 按下数字'1', 切换到小偷, 识别并返回丢弃点坐标
 * 6. 左键点击小偷技能栏的丢弃物品按钮, 鼠标移动到丢弃点坐标处, 左键点击丢弃点坐标, 物品丢弃在了丢弃点处
 * 7. 小偷等待储物柜操作 [储物柜将修改后的物品丢弃到丢弃点], 等待储物柜返回丢弃点坐标(两个丢弃点坐标可能不一致)
 * 8. 按下数字'1', 切换到小偷, 左键点击技能栏的范围拾取按钮, 鼠标移动到储物柜返回的丢弃点处, 左键点击丢弃点
 * 9. 需要重新'A' 金矿, 重复步骤2 - 3, 并且通过装备图片, 判断是否拾取成功, 判断图片是否存在于小偷的物品栏中, 如果存在则返回成功, 否则返回失败
 * 10. 步骤9的返回值, 返回给了储物柜的方法中, [储物柜操作: 等待1分钟, 小偷偷取金币. 储物柜购买5次技能物品, 并且修改物品, 将物品丢弃在丢弃点, 返回执行成功/失败]
 * 11. 等待储物柜[步骤10中储物柜操作]再次修改物品的返回值, 如果成功, 则执行步骤8的操作.然后判断最后一件修改后的物品图片是否在小偷的物品栏中.
 * 12 再次 'A' 金矿. 小偷流程执行完成
 * 13. 将两个丢弃点区分, 使用丢弃点1 和丢弃点2 来区分
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/12 9:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ThiefController extends CommonController {
    // 小偷编号
    private static final int THIEF_NUMBER = 1;

    // 技能图片
    private static final String THIEF_DROP_SKILL = "1/drop_skill";        // 丢弃物品技能
    private static final String THIEF_PICKUP_SKILL = "1/pickup_skill";     // 范围拾取技能

    // 金矿图片
    private static final String GOLD_MINE = "common/gold_mine";

    // 商店相关
    private static final String SHOP_BUILDING = "common/shop_building";     // 商店最左上角物品
    private static final String BUY_EQUIPMENT_BOX = "5/buy_equipment_box";             // 购买确认按钮


    private static final String ITEM_NAME_I291 = "common/ItemName_I291";           // 验证第一次拾取的物品
    private static final String ITEM_NAME_I294 = "common/ItemName_I294";           // 验证最后一次修改的物品

    // 等待时间配置（毫秒）
    private static final int WAIT_FOR_SHOP_TIME = 60000;                 // 等待 1 分钟去商店
    private static final int WAIT_FOR_GOLD_TIME = 120000;                // 等待 2 分钟偷取金币

    private SpecialHandel specialHandel;

    public ThiefController(ImageRecognitionAutomation automation) {
        super(automation);
        specialHandel = new SpecialHandel();
    }

    /**
     * 小偷初始化流程
     * 1. 给小偷编号 1
     * 2. 开始攻击金矿
     * @return 是否成功
     */
    public boolean initialize() {
        LogUtil.info("=== 小偷初始化流程开始 ===");
        // 1. 给小偷编号 1
        selectNumber(THIEF_NUMBER);
        LogUtil.info("小偷编号为：{}", THIEF_NUMBER);

        LogUtil.info("小偷初始化完成");
        return true;
    }

    /**
     * 攻击金矿操作
     * 通过 A 键指向金矿获取金币
     * @return 是否成功
     */
    public boolean attackGoldMine() {
        LogUtil.info("=== 开始攻击金矿 ===");

        // 找到金矿坐标
        Point goldMinePoint = findImage(GOLD_MINE);
        if (goldMinePoint == null) {
            LogUtil.error("未找到金矿");
            return false;
        }

        LogUtil.info("找到金矿坐标：({}, {})", goldMinePoint.x, goldMinePoint.y);

        // 按下 A 键
        automation.robot.keyPress(KeyEvent.VK_A);
        automation.delay(CLICK_DELAY);

        // 移动鼠标到金矿坐标并点击
        automation.click(goldMinePoint.x, goldMinePoint.y);
        automation.delay(CLICK_DELAY);

        // 释放 A 键
        automation.robot.keyRelease(KeyEvent.VK_A);
        automation.delay(CLICK_DELAY);

        LogUtil.info("金矿攻击完成");
        return true;
    }

    /**
     * 去商店购买物品流程
     * 等待 1 分钟后找到商店，购买最便宜的物品
     * @return 是否成功
     */
    public boolean goToShopAndBuy() {
        LogUtil.info("=== 前往商店购买物品 ===");

        // 等待 1 分钟
        LogUtil.info("等待{}毫秒，准备前往商店", WAIT_FOR_SHOP_TIME);
        automation.delay(WAIT_FOR_SHOP_TIME);

        // 找到商店坐标
        Point shopPoint = findImage(SHOP_BUILDING);
        if (shopPoint == null) {
            LogUtil.error("未找到商店");
            return false;
        }

        LogUtil.info("找到商店坐标：({}, {})", shopPoint.x, shopPoint.y);

        // 点击商店
        automation.click(shopPoint.x, shopPoint.y);
        automation.delay(CLICK_DELAY);

        // 通过 SpecialHandel 来购买商店左上角的商品
        Point thingPoint = specialHandel.findImage(BUY_EQUIPMENT_BOX);
        if (thingPoint == null) {
            LogUtil.error("购买确认失败");
            return false;
        } else {
            automation.click(thingPoint.x, thingPoint.y);
            automation.delay(CLICK_DELAY);
        }

        LogUtil.info("商店购买完成");
        return true;
    }

    /**
     * 丢弃物品到指定丢弃点
     * @param dropPointName 丢弃点图片名称 ("drop_point_1" 或 "drop_point_2")
     * @return 是否成功
     */
    public boolean dropItem(String dropPointName) {
        LogUtil.info("=== 丢弃物品到丢弃点 ===");

        // 确保当前是小偷
        pressKey(THIEF_NUMBER);

        // 点击丢弃物品技能
        Point dropSkillPoint = findImage(THIEF_DROP_SKILL);
        if (dropSkillPoint == null) {
            LogUtil.error("未找到丢弃物品技能");
            return false;
        }

        automation.click(dropSkillPoint.x, dropSkillPoint.y);
        automation.delay(CLICK_DELAY);

        // 找到丢弃点坐标
        Point dropPoint = findImage(dropPointName);
        if (dropPoint == null) {
            LogUtil.error("未找到丢弃点：{}", dropPointName);
            return false;
        }

        LogUtil.info("找到丢弃点坐标：({}, {})", dropPoint.x, dropPoint.y);

        // 移动到丢弃点并点击
        automation.click(dropPoint.x, dropPoint.y);
        automation.delay(CLICK_DELAY);

        LogUtil.info("物品已丢弃到{}", dropPointName);
        return true;
    }

    /**
     * 从丢弃点拾取修改后的物品
     * @param dropPointName 丢弃点图片名称
     * @return 是否成功
     */
    public boolean pickupModifiedItem(String dropPointName) {
        LogUtil.info("=== 拾取修改后的物品 ===");

        // 确保当前是小偷
        pressKey(THIEF_NUMBER);

        // 点击范围拾取技能
        Point pickupSkillPoint = findImage(THIEF_PICKUP_SKILL);
        if (pickupSkillPoint == null) {
            LogUtil.error("未找到范围拾取技能");
            return false;
        }

        automation.click(pickupSkillPoint.x, pickupSkillPoint.y);
        automation.delay(CLICK_DELAY);

        // 找到丢弃点坐标
        Point dropPoint = findImage(dropPointName);
        if (dropPoint == null) {
            LogUtil.error("未找到丢弃点：{}", dropPointName);
            return false;
        }

        LogUtil.info("找到丢弃点坐标：({}, {})", dropPoint.x, dropPoint.y);

        // 移动到丢弃点并点击
        automation.click(dropPoint.x, dropPoint.y);
        automation.delay(CLICK_DELAY);

        LogUtil.info("已从{}拾取物品", dropPointName);
        return true;
    }

    /**
     * 验证物品是否在物品栏中
     * @param itemName 物品名称（图片名）
     * @return 是否存在
     */
    public boolean verifyItemInSlot(String itemName) {
        LogUtil.info("=== 验证物品是否存在：{} ===", itemName);

        // 在物品栏区域查找物品图片
        Point itemPoint = findImage(itemName);

        if (itemPoint != null) {
            LogUtil.info("物品{}存在于物品栏中", itemName);
            return true;
        } else {
            LogUtil.info("物品{}未在物品栏中找到", itemName);
            return false;
        }
    }

    /**
     * 完整的小偷操作流程
     * 包含：攻击金矿 -> 等 1 分钟去商店 -> 丢弃物品 -> 等待储物柜修改 -> 拾取物品 -> 验证 -> 继续攻击金矿
     * @param dropPointName 使用的丢弃点名称
     * @param expectedItemName 期望获得的物品名称
     * @return 是否成功
     */
    // TODO: 这个方法需要与 LockerController 配合，目前先实现框架
    public boolean executeFullProcess(String dropPointName, String expectedItemName) {
        LogUtil.info("=== 小偷完整操作流程开始 ===");

        try {
            // 1. 攻击金矿
            if (!attackGoldMine()) {
                return false;
            }

            // 2. 等待 1 分钟并去商店购买
            if (!goToShopAndBuy()) {
                return false;
            }

            // 3. 丢弃物品到指定点, 丢弃到点 1
            if (!dropItem(DROP_POINT_1)) {
                return false;
            }

            // TODO: 4. 等待储物柜拾取并修改物品
            // 这里需要调用 LockerController 的方法

            LogUtil.info("等待储物柜操作完成...");
            automation.delay(5000); // 临时等待时间

            // 5. 拾取修改后的物品
            if (!pickupModifiedItem(dropPointName)) {
                return false;
            }

            // 6. 验证物品是否正确, 验证物品 I291
            if (!verifyItemInSlot(ITEM_NAME_I291)) {
                LogUtil.error("物品验证失败：{}", ITEM_NAME_I291);
                return false;
            }

            // 7. 重新攻击金矿
            if (!attackGoldMine()) {
                return false;
            }

            // 8. 等待储物柜购买技能物品并且修改
            // TODO 8: 这里需要调用 LockerController 的方法

            // 9. 再次拾取修改后的物品
            if (!pickupModifiedItem(DROP_POINT_2)) {
                return false;
            }

            // 10. 验证物品是否正确, 验证物品 I294
            if (!verifyItemInSlot(ITEM_NAME_I294)) {
                LogUtil.error("物品验证失败：{}", ITEM_NAME_I294);
                return false;
            }

            LogUtil.info("小偷完整操作流程完成");
            return true;

        } catch (Exception e) {
            LogUtil.error("小偷操作流程异常：{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
