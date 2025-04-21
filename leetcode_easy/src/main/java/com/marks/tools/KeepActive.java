package com.marks.tools;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/18 16:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class KeepActive {
    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        int i = 0;
        /*
        * 执行按下鼠标左键, 然后释放
        * 整体执行 600 次, 每次执行时间为6s
        * 总体执行时间为 3600s, 即一个小时
        *
        * */
        while (i < 600) {
            Thread.sleep(500);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(500);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(5000);
            i++;
        }
    }
}
