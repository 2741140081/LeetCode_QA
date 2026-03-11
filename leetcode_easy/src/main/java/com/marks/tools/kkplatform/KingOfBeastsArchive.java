package com.marks.tools.kkplatform;

import com.marks.tools.kkplatform.common.GameOperationCommon;
import com.marks.tools.kkplatform.entity.AEEInfoDAO;
import com.marks.tools.kkplatform.service.ArchiveEquipmentService;
import com.marks.tools.kkplatform.service.impl.ArchiveEquipmentServiceImpl;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: KingOfBeastsArchive </p>
 * <p>描述: 自动化存档斗兽之王
 * 1. 按下"空格键"(任意键, 习惯性用空格, 其他键也可), 进入游戏
 * 2. 通过图片识别, 找到对应的难度选项, 点击选择难度
 * 3. 选择英雄, 英雄通过(F1, F2, F3) 的按钮进行选择, 自动化过程中只选择F1 的英雄, 按下F1 键, 通过图片识别, 找到"确认"按钮, 点击确认,
 * 按下组合键 "Ctrl + 1", 对英雄进行编号(当按下 1键时, 会选择该英雄)
 * 4. 切换到修改器app 界面 并且执行相关操作, 返回 boolean 值, true 表示成功, false 表示失败
 * 5. 如果返回 true 后, 切换到游戏界面, 返回false, 则程序并且记录错误日志
 * 6. 找到并且点击天赋按钮(一个图标), 图片识别并且点击 "火焰天赋" 按钮
 * 7. 按下 "1键" 选择英雄, 找到并点击 "准备" 按钮, 鼠标一直位于 "准备" 按钮位置处, 每隔 5s 点击一次"准备"按钮
 * 8. 过了15分钟后, 识别"存档"按钮, 是否存在, 如果不存在, 延迟30s后, 再次识别"存档" 按钮,
 * 如果存在, 点击"存档" 按钮, 找到并且点击 "开始存档" 按钮, 图片识别 并且 点击 "存档属性A" 选项
 * 如果过了 25 分钟, 还是没有找到存档按钮, 则截取当前游戏页面截图并且保存, 然后程序运行结束
 * 9. 在13步骤存档完成后, 图片识别 并点击 "退出游戏" 按钮
 *
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/5 11:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class KingOfBeastsArchive extends GameOperationCommon {
    private static final String DIFFICULTY_btn = "difficulty_btn";
    private static final String SMALL_btn = "small_btn";
    private static final String FIRE_TALENT_btn = "fire_talent_btn";
    private static final String READY_btn = "ready_btn";
    private static final String ARCHIVE_btn = "archive_btn";
    private static final String SELECT_btn = "select_btn";
    private static final String NEXT_PAGE_btn = "next_page_btn";

    private ModifiersOperation modifiersOperation;
    private SpecialHandel specialHandel;
    private boolean shouldStop = false;
    // 手动注入服务层 ArchiveEquipmentService
    private ArchiveEquipmentService archiveEquipmentService;

    public KingOfBeastsArchive(ImageRecognitionAutomation automation, ModifiersOperation modifiersOperation) {
        super(automation);
        this.modifiersOperation = modifiersOperation;
        specialHandel = new SpecialHandel();
        archiveEquipmentService = new ArchiveEquipmentServiceImpl();
    }

    public boolean executeGameAndSelectHero() {
        LogUtil.info("=== 开始游戏流程 ===");

        // 等待游戏加载完成, 按下空格键进入游戏, 该步骤可以省略, 只需要等待5s即可
        automation.delay(5000);

        // 选择难度
        if (!selectDifficulty()) {
            return false;
        }

        // 选择英雄
        return selectHero();
    }

    /**
     * 执行完整的游戏流程
     * @return 是否成功完成
     */
    public boolean executeGameLoop() {
        try {
            // 点击缩放符文按钮
            if (!selectSmallBtn()) {
                return false;
            }
            // 选择天赋
            selectTalent();

            // 准备并等待
            if (!prepareAndWait()) {
                return false;
            }

            if (!archiveGame()) {
                return false;
            }

            exitGame();

            LogUtil.info("=== 游戏流程完成 ===");
            return true;

        } catch (Exception e) {
            LogUtil.error("游戏流程异常：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 步骤 1: 按空格键进入游戏
     * 过时方法, 待弃
     */
    @Deprecated
    private boolean enterGame() {
        LogUtil.info("=== 步骤 1: 进入游戏 ===");
        automation.robot.keyPress(KeyEvent.VK_SPACE);
        automation.delay(50);
        automation.robot.keyRelease(KeyEvent.VK_SPACE);
        automation.delay(2000);
        LogUtil.info("已按空格键进入游戏");
        return true;
    }

    /**
     * 步骤 2: 选择难度
     * 修改, 直接通过循环判断难度按钮是否存在, 如果不存在, 等待1s后继续判断
     */
    private boolean selectDifficulty() {
        LogUtil.info("=== 步骤 2: 选择难度 ===");
        while (automation.findImage(DIFFICULTY_btn, false) == null) {
            LogUtil.info("等待难度按钮加载...");
            automation.delay(1000);
        }
        return findAndClickImage(DIFFICULTY_btn);
    }

    /**
     * 步骤 3: 选择英雄
     * update: 修改, 改成等待35s, 系统自动分配英雄
     * update: done
     * 并且, 通过 Snipaste 的F1 截图, 左上角 到start 点的距离 (px, py) * 0.667 后即为 start 点坐标, 同理得到 end 点坐标.
     */
    private boolean selectHero() {
        LogUtil.info("=== 步骤 3: 选择英雄 ===");
        // 圈选英雄
        Point start = new Point(1113, 600);
        Point end = new Point(1227, 720);
        moveMouseWithLeftUp(start, end, 500);
        // 延迟1s
        automation.delay(1000);
        // 选择确认按钮
        if (!findAndClickImage(SELECT_btn)) {
            // 默认方案延迟34s
            automation.delay(30000);
        }
        // 延迟3s, 等待确认
        automation.delay(3000);

        // 按下 "Ctrl + 1" 给英雄编号
        pressCombinationKey(KeyEvent.VK_CONTROL, KeyEvent.VK_1);

        LogUtil.info("英雄选择完成");
        return true;
    }

    /**
     * 步骤 pre 5: 特殊处理缩放按钮
     * 1. 不需要点击缩放按钮, 只需要找到缩放按钮的位置即可
     * 2. 找到位置后, 需要将坐标进行变换, x + 100px(向右移动100px), y - 150px(向上移动150px)
     * 3. 得到新的坐标后, 进行 点击
     */
    private boolean selectSmallBtn() {
        LogUtil.info("=== 步骤 pre 5: 点击缩放按钮 ===");
        // 延迟100ms
        automation.delay(100);
        // 获取small_btn 位置
        Point smallBtnPoint = automation.findImage(SMALL_btn);
        // 延迟1s
        automation.delay(1000);
        // 判断small_btn 是否存在
        if (smallBtnPoint == null) {
            LogUtil.error("未找到缩放按钮");
            return false;
        }
        // 找到small_btn, 变换坐标
        Point newSmallBtnPoint = new Point(smallBtnPoint.x + 100, smallBtnPoint.y - 150);
        LogUtil.info("缩放按钮坐标: " + newSmallBtnPoint);
        modifiersOperation.doubleClickAt(newSmallBtnPoint.x, newSmallBtnPoint.y);
        // 延迟500ms
        automation.delay(500);
        // 再次查找small_btn, 如果没找到, 证明点击成功, 如果找到, 继续点击
        if (automation.findImage(SMALL_btn, false) == null) {
            LogUtil.info("缩放按钮点击成功");
            return true;
        }

        return false;
    }

    /**
     * 步骤 5: 选择天赋
     * 1. 需要变更, 如果未找到天赋按钮, 不进行任何处理, 找到了天赋按钮, 则进行点击
     * 2. done
     */
    private void selectTalent() {
        LogUtil.info("=== 步骤 5: 选择天赋 ===");
        // 圈选符文建筑
        Point start = new Point(1000, 600);
        Point end = new Point(1173, 800);
        moveMouseWithLeftUp(start, end, 500);
        // 延迟1s
        automation.delay(1000);
        // 选择火焰之球天赋
        findAndClickImage(FIRE_TALENT_btn);
        // 延迟3s, 等待确认
        automation.delay(2000);
    }

    /**
     * 步骤 6: 准备并等待
     */
    private boolean prepareAndWait() {
        LogUtil.info("=== 步骤 6: 准备并等待 ===");

        // 按下 "1键" 通过编号选择英雄
        automation.robot.keyPress(KeyEvent.VK_1);
        automation.delay(100);
        automation.robot.keyRelease(KeyEvent.VK_1);
        automation.delay(CLICK_DELAY);

        // 点击 "准备" 按钮
        Point readyPoint = automation.findImage(READY_btn);
        if (readyPoint == null) {
            LogUtil.error("未找到准备按钮");
            return false;
        }

        automation.click(readyPoint.x, readyPoint.y);

        // 重置shouldStop 状态
        shouldStop = false;

        // 创建一个线程, 每隔5s点击一次 "准备" 按钮
        Thread clickThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            int scaleBtnClickCount = 0; // 缩放按钮点击计数器
            final int MAX_SCALE_BTN_CLICKS = 3; // 最大点击次数 3 次

            while (!shouldStop && System.currentTimeMillis() - startTime < 10 * 60 * 1000) {
                // 添加一个计数器, 最多3次, 需要在每次准备前, 判定是否存在缩放按钮, 如果存在, 则进行点击, 最多点击3次
                // 在每次准备前，判定是否存在缩放按钮，如果存在，则进行点击，最多点击 3 次
                if (scaleBtnClickCount < MAX_SCALE_BTN_CLICKS) {
                    Point scaleBtnPoint = automation.findImage(SMALL_btn, false);
                    if (scaleBtnPoint != null) {
                        LogUtil.info("检测到缩放按钮，第 " + (scaleBtnClickCount + 1) + " 次点击");
                        // 找到small_btn, 变换坐标后进行点击
                        Point newSmallBtnPoint = new Point(scaleBtnPoint.x + 100, scaleBtnPoint.y - 150);
                        LogUtil.info("缩放按钮坐标: " + newSmallBtnPoint);
                        modifiersOperation.doubleClickAt(newSmallBtnPoint.x, newSmallBtnPoint.y);
                        scaleBtnClickCount++;
                        automation.delay(500); // 点击后延迟 500ms，确保点击生效
                    }
                }
                automation.delay(5000);
                automation.click(readyPoint.x, readyPoint.y);
                LogUtil.info("自动点击准备按钮");
            }
        });

        clickThread.start();

        // 等待 12 分钟游戏胜利
        try {
            Thread.sleep(10 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 设置停止标志，让线程安全退出
        shouldStop = true;

        // 等待线程执行完成，确保资源被正确释放
        try {
            clickThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 步骤 7: 存档
     */
    private boolean archiveGame() {
        LogUtil.info("=== 步骤 7: 存档 ===");

        long startTime = System.currentTimeMillis();
        boolean archiveFound = false;

        while (System.currentTimeMillis() - startTime < 15 * 60 * 1000) {
            LogUtil.info("等待存档按钮出现...");
            Point point = automation.findImage(ARCHIVE_btn, false);
            if (point != null) {
                archiveFound = true;

                // 点击 "存档" 按钮
                if (!findAndClickImage(ARCHIVE_btn)) {
                    return false;
                }

                // 点击 "开始存档" 按钮, 特殊处理
                Point image = specialHandel.findImage(SELECT_btn);
                if (image == null) {
                    // 退回原始方案
                    if (!findAndClickImage(SELECT_btn)) {
                        return false;
                    }
                } else {
                    LogUtil.info("特殊处理: 找到 " + SELECT_btn + " 按钮");
                    // 点击 image point
                    modifiersOperation.oneClickAt(image.x, image.y);
                }


                // 选择存档位置
                if (!strengthenArchiveEquipment()) {
                    return false;
                }

                LogUtil.info("存档完成");
                break;
            } else {
                LogUtil.info("未找到存档按钮，3 秒后重试...");
                automation.delay(3000);
            }
        }

        if (!archiveFound) {
            LogUtil.error("超过 15 分钟未找到存档按钮，截取当前画面");
            captureErrorScreen();
            return false;
        }

        return true;
    }

    /**
     * archiveEquipmentService 来查询数据库
     * 从数据库表中查找强化未到达上限的最小id 的存档装备信息,
     * 执行强化装备流程,
     * 如果强化完成, 更新数据库信息
     * 如果强化未完成, 找下一个id的装备信息
     */
    private boolean strengthenArchiveEquipment() {
        // 找到最小可强化的装备
        AEEInfoDAO aeeInfoDAO = archiveEquipmentService.selectMinEnhanceableEquipment();
        // 图片匹配
        String aeImgName = aeeInfoDAO.getAeImgName();
        String aeImgPath = aeeInfoDAO.getAeImgPath() + aeImgName;
        int aeId = aeeInfoDAO.getAeId();
        int aeEnhancedCount = aeeInfoDAO.getAeEnhancedCount();
        int pageNumber = aeeInfoDAO.getAePageNumber();
        // 判断是否需要点击'下一页'
        if (pageNumber > 1) {
            // 需要点击下一页
            if (!findAndClickImage(NEXT_PAGE_btn)) {
                return false;
            }
        }
        Point point = automation.findImage(aeImgName, aeImgPath, true);
        // 点击强化
        if (point != null) {
            doubleClickAt(point.x, point.y);
            // 更新数据库信息
            return archiveEquipmentService.updateEnhancedCount(aeId, aeEnhancedCount + 1);
        }

        return false;
    }

    /**
     * 步骤 8: 退出游戏
     */
    private void exitGame() {
        LogUtil.info("=== 步骤 8: 退出游戏 ===");
        // 使用组合键退出游戏, 先按 F10 键, 再按 E 键, 再按 X 键, 最后再按 X, 然后等待10s, 即可完全退出游戏
        pressFunctionKey(KeyEvent.VK_F10);
        pressFunctionKey(KeyEvent.VK_E);
        pressFunctionKey(KeyEvent.VK_X);
        pressFunctionKey(KeyEvent.VK_X);
        // 等待10s
        automation.delay(5000);
        LogUtil.info("已点击退出游戏按钮");
    }


    /**
     * 截取错误截图
     */
    private void captureErrorScreen() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        String screenshotPath = "D:\\images\\automation\\error_screenshots\\error_" + timestamp + ".png";

        File dir = new File("D:\\images\\automation\\error_screenshots\\");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        automation.captureScreen(screenshotPath);
        LogUtil.error("错误截图已保存：" + screenshotPath);
    }

    /**
     * 圈选英雄 / 天赋按钮
     * 1. 鼠标移动到start点
     * 2. 左键按下(不立即释放)
     * 3. 鼠标移动到end 点
     * 4. 释放左键
     */
    public void moveMouseWithLeftUp(Point start, Point end, int spendTime) {
        try {
            // 1. 鼠标移动到起始点
            automation.robot.mouseMove((int) start.x, (int) start.y);
            automation.delay(100); // 短暂延迟，确保鼠标到位

            // 2. 左键按下（不立即释放）
            automation.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            automation.delay(200); // 等待按下稳定

            // 3. 鼠标平滑移动到结束点
            smoothMouseMove(start, end, spendTime);

            // 4. 释放左键
            automation.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            automation.delay(100); // 释放后短暂延迟

            LogUtil.info(String.format("圈选操作完成：从 (%d, %d) 到 (%d, %d), 耗时 %dms",
                    (int) start.x, (int) start.y,
                    (int) end.x, (int) end.y,
                    spendTime));

        } catch (Exception e) {
            LogUtil.error("圈选操作失败：" + e.getMessage());
            // 确保释放鼠标左键（防止卡住）
            automation.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            e.printStackTrace();
        }
    }

    /**
     * 平滑移动鼠标（模拟人类行为）
     * @param start 起始点
     * @param end 结束点
     * @param totalDuration 总耗时（毫秒）
     */
    private void smoothMouseMove(Point start, Point end, int totalDuration) {
        double distance = Math.sqrt(
                Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2)
        );

        // 计算需要移动的步数（每 20 像素一步）
        int steps = (int) (distance / 20);
        if (steps < 1) {
            steps = 1;
        }

        // 计算每步的延迟
        int delayPerStep = totalDuration / steps;
        if (delayPerStep < 1) {
            delayPerStep = 1;
        }

        // 计算每步的增量
        double dx = (end.x - start.x) / (double) steps;
        double dy = (end.y - start.y) / (double) steps;

        // 逐步移动鼠标
        for (int i = 0; i <= steps; i++) {
            int x = (int) (start.x + dx * i);
            int y = (int) (start.y + dy * i);

            automation.robot.mouseMove(x, y);
            automation.delay(delayPerStep);
        }
    }
}
