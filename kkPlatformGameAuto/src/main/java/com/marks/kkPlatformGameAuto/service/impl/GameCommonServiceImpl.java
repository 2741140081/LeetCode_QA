package com.marks.kkPlatformGameAuto.service.impl;

import com.marks.kkPlatformGameAuto.service.GameCommonService;
import com.marks.kkPlatformGameAuto.service.WindowSwitcherService;
import com.marks.kkPlatformGameAuto.state.GameStateManager;
import com.marks.kkPlatformGameAuto.util.InputController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: GameCommonServiceImpl </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19 10:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Service
public class GameCommonServiceImpl implements GameCommonService {
    @Autowired
    private GameStateManager gameStateManager;

    @Autowired
    private InputController input;

    @Autowired
    private WindowSwitcherService windowSwitcherService;

    /**
     * 退出游戏
     * 使用组合键退出游戏, 先按 F10 键, 再按 E 键, 再按 X 键, 最后再按 X, 然后等待10s, 即可完全退出游戏
     */
    @Override
    public void exitGame() {
        log.info("=== 退出游戏 ===");
        switchGameWindow();
        // 按下 F10 键, 弹出系统菜单选项, pressFunctionKey 会自动查找 F10 键的 keyCode
        input.pressFunctionKey(10);
        // 按下 E 键退出游戏游戏选项
        int ECode = input.getLetterKeyCode('E');
        int XCode = input.getLetterKeyCode('X');
        input.pressKey(ECode);
        input.pressKey(XCode);
        input.pressKey(XCode);
        // 等待10s
        input.delay(10000);
        // 更改游戏状态为停止
        gameStateManager.stopGame();
    }

    /**
     * 暂停游戏
     * 1. 使用 F10 弹出系统菜单选项
     * 2. 按下 P 键暂停游戏
     * 3. 按下 R 键关闭菜单选项, 回到暂停后的游戏界面
     * 4. 修改state状态为暂停, 线程处于等待状态
     */
    @Override
    public void pauseGame() {
        log.info("=== 收到暂停游戏请求 ===");
        switchGameWindow();
        input.pressFunctionKey(10);
        input.pressKey(input.getLetterKeyCode('P'));
        input.pressKey(input.getLetterKeyCode('R'));
        gameStateManager.pauseGame();
        log.info("游戏已暂停");
    }

    /**
     * 恢复游戏
     * 1. 使用 F10 弹出系统菜单选项
     * 2. 按下 M 键恢复游戏
     * 3. 按下 R 键关闭菜单选项, 回到游戏界面
     * 4. 修改state状态为运行, 线程继续运行
     */
    @Override
    public void resumeGame() {
        log.info("=== 收到恢复游戏请求 ===");
        switchGameWindow();
        input.pressFunctionKey(10);
        input.pressKey(input.getLetterKeyCode('M'));
        input.pressKey(input.getLetterKeyCode('R'));
        gameStateManager.resumeGame();
        log.info("游戏已恢复");
    }

    private void switchGameWindow() {
        windowSwitcherService.switchToGame();
    }
}
