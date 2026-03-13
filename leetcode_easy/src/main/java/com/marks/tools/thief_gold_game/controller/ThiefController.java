package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.kkplatform.SpecialHandel;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

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
 * update: 2026/3/12
 * 小偷流程基本完成
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

    // 金矿图片
    private static final String GOLD_MINE = "common/gold_mine";

    // 商店相关
    private static final String SHOP_BUILDING = "common/shop_building";     // 商店最左上角物品
    private static final String BUY_EQUIPMENT_BOX = "5/buy_equipment_box";             // 购买确认按钮

    // 等待时间配置（毫秒）
    private static final int WAIT_FOR_SHOP_TIME = 60000;                 // 等待 1 分钟去商店

    private SpecialHandel specialHandel;
    private LockerController lockerController;

    public ThiefController(ImageRecognitionAutomation automation) {
        super(automation);
        specialHandel = new SpecialHandel();
    }

    /**
     * 小偷初始化流程
     * 1. 给小偷编号 1
     * 2. 开始攻击金矿
     */
    public void initialize(LockerController lockerController) {
        LogUtil.info("=== 小偷初始化流程开始 ===");
        // 1. 给小偷编号 1
        selectNumber(THIEF_NUMBER);
        LogUtil.info("小偷编号为：["+ THIEF_NUMBER +"]");
        // 延迟1s
        automation.delay(1000);
        this.lockerController = lockerController;
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
        LogUtil.info("等待" + WAIT_FOR_SHOP_TIME + "毫秒，准备前往商店");
        automation.delay(WAIT_FOR_SHOP_TIME);

        // 找到商店坐标
        Point shopPoint = findImage(SHOP_BUILDING);
        if (shopPoint == null) {
            LogUtil.error("未找到商店");
            return false;
        }

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
     * 丢弃物品到储物柜
     * 1. 移动视角使得储物柜显示在主页面
     * @return 是否成功
     */
    public boolean dropItem() {
        LogUtil.info("=== 丢弃物品到丢弃点 ===");

        // 1. 移动视角使得储物柜显示在主页面
        pressKey(KeyEvent.VK_DOWN);
        automation.delay(CLICK_DELAY);
        pressKey(KeyEvent.VK_DOWN);
        automation.delay(CLICK_DELAY);

        // 2. 找到储物柜坐标点
        Point lockerPoint = findImage(LOCKER_BUILDING);
        if (lockerPoint == null) {
            LogUtil.error("未找到储物柜");
            return false;
        }

        // 确保当前是小偷
        pressKey(THIEF_NUMBER);

        // 3. 获取 THIEF_DROP_SKILL 的坐标, 然后计算第一件物品的坐标值
        // 点击丢弃物品技能
        Point dropSkillPoint = findImage(THIEF_DROP_SKILL);
        if (dropSkillPoint == null) {
            LogUtil.error("未找到丢弃物品技能");
            return false;
        }
        // 通过计算, 得到第一件物品的坐标, y 值保持不变, x 值减去 150 px
        Point itemPoint = new Point(dropSkillPoint.x - 150, dropSkillPoint.y);

        // 点击 itemPoint, 相当于移动操作
        automation.click(itemPoint.x, itemPoint.y);
        automation.delay(CLICK_DELAY);

        // 右键点击, 等同于拿起物品操作
        automation.rightClick(itemPoint.x, itemPoint.y);

        // 移动到储物柜坐标点并点击
        automation.click(lockerPoint.x, lockerPoint.y);
        automation.delay(CLICK_DELAY);
        // 延迟1s
        automation.delay(1000);

        // 4. 将小偷物品栏可能剩余的物品进行丢弃
        // 点击丢弃按钮
        automation.click(dropSkillPoint.x, dropSkillPoint.y);
        // 在储物柜坐标点基础上, x值不变, y 值减去 150 px, 设置新丢弃点
        Point dropPoint = new Point(lockerPoint.x, lockerPoint.y - 150);
        // 移动到新丢弃点并点击
        automation.click(dropPoint.x, dropPoint.y);
        automation.delay(CLICK_DELAY);
        // 延迟1s
        automation.delay(1000);
        LogUtil.info("物品丢弃完成");
        return true;
    }

    /**
     * 验证物品是否在物品栏中
     * @return 是否存在
     */
    public boolean verifyItemInSlot() {
        List<String> itemNames = getAllItemNames();
        // 在物品栏区域查找物品图片
        for (String itemName : itemNames) {
            itemName = COMMON_FOLDER + itemName;
            Point itemPoint = findImage(itemName);
            if (itemPoint == null) {
                LogUtil.info("物品{}未在物品栏中找到", itemName);
                return false;
            }
        }
        LogUtil.info("物品已全部在物品栏中");
        return true;
    }

    /**
     * 完整的小偷操作流程
     * 包含：攻击金矿 -> 等 1 分钟去商店 -> 丢弃物品 -> 等待储物柜修改 -> 拾取物品 -> 验证 -> 继续攻击金矿
     * 1. 攻击金矿是重复操作, 在 initialize() 中重复执行, 当前该操作可以省略
     * 2. 通过编号切换到小偷
     * 3. 点击2次"向下键", 移动视角到储物柜(使得储物柜显示在主页面上)
     * @return 是否成功
     */
    public boolean executeThiefProcess() {
        LogUtil.info("=== 小偷操作流程开始 ===");

        try {
            // 1. 等待 1 分钟并去商店购买
            if (!goToShopAndBuy()) {
                return false;
            }

            // 2. 切换到小偷, 并且按下空格键调整中心点
            pressKey(THIEF_NUMBER);
            automation.delay(CLICK_DELAY);
            pressKey(KeyEvent.VK_SPACE);
            automation.delay(CLICK_DELAY);

            // 3. 移动视角到储物柜, 并且将身上物品转移到储物柜
            if (!dropItem()) {
                return false;
            }

            // 4. 从新攻击金矿
            attackGoldMine();

            // 5. 执行第一次物品修改
            if (!lockerController.executeFirstModificationProcess()) {
                LogUtil.info("第一次物品修改流程执行失败");
            }

            LogUtil.info("小偷操作流程完成");
            return true;

        } catch (Exception e) {
            LogUtil.error("小偷操作流程异常：{}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
