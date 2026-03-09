package com.marks.tools.kkplatform;

import com.marks.tools.kkplatform.common.GameOperationCommon;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ModifiersOperation </p>
 * <p>描述: 修改器相关操作
 * 1. 通过图片识别, 找到并且点击"查找游戏"按钮
 * 2. 通过图片识别, 在树形结构中找到并且点击"人物属性" 选项
 * 3. 通过图片识别, 在右侧具体属性栏中, 通过图片识别找到"经验值" 输入框(这个可以识别, 标记, 并且保存截图), 双击输入框 2/3 位置处,
 * Ctrl + A 全选内容, 输入 "45000000" , Enter 键确认
 * 4. 通过图片识别, 找到并点击 "修改"按钮, 返回执行结果(没有异常或者报错, 返回 true)
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/5 11:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ModifiersOperation extends GameOperationCommon {
    private static final String FIND_GAME_BUTTON = "find_game_btn";
    private static final String EXP_INPUT_BOX = "exp_input_box";
    private static final String MODIFY_BUTTON = "modify_btn";
    private static final String EXPERIENCE_VALUE = "45000000";

    public ModifiersOperation(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 执行修改器操作流程
     *
     * @return 是否成功
     */
    public boolean execute() {
        LogUtil.info("=== 修改器操作开始 ===");
        // 延迟100ms
        automation.delay(100);

        // 步骤 1: 点击查找游戏按钮
        if (!findGame()) {
            return false;
        }

        // 步骤 3: 修改经验值
        if (!modifyExperience()) {
            return false;
        }
        // 延迟100ms
        automation.delay(100);
        // 步骤 4: 点击修改按钮
        if (!clickModifyButton()) {
            return false;
        }
        LogUtil.info("=== 修改器操作完成 ===");
        return true;
    }

    /**
     * 步骤 1: 点击查找游戏按钮
     */
    private boolean findGame() {
        LogUtil.info("步骤 1: 查找游戏");
        return findAndClickImage(FIND_GAME_BUTTON);
    }

    /**
     * 步骤 3: 修改经验值
     */
    private boolean modifyExperience() {
        LogUtil.info("步骤 3: 修改经验值");

        Point expInputPoint = automation.findImage(EXP_INPUT_BOX);
        if (expInputPoint == null) {
            LogUtil.error("未找到经验值输入框");
            return false;
        }

        String screenshotPath = "D:\\images\\automation\\exp_input_box_marked.png";
        automation.captureRegion(
                expInputPoint.x - 100,
                expInputPoint.y - 50,
                200,
                100,
                screenshotPath
        );
        LogUtil.info("经验值输入框截图已保存：" + screenshotPath);

        int clickX = expInputPoint.x;
        int clickY = expInputPoint.y;

        oneClickAt(clickX, clickY);

        pressCombinationKey(KeyEvent.VK_CONTROL, KeyEvent.VK_A);
        automation.delay(1000);
        inputTextAndConfirm(EXPERIENCE_VALUE);
        automation.delay(1000);

        return true;
    }

    /**
     * 步骤 4: 点击修改按钮
     */
    private boolean clickModifyButton() {
        LogUtil.info("步骤 4: 点击修改按钮");

        if (findAndClickImage(MODIFY_BUTTON)) {
            LogUtil.info("修改成功");
            return true;
        }

        LogUtil.error("修改失败");
        return false;
    }
}
