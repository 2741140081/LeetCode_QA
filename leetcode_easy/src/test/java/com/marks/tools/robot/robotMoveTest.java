package com.marks.tools.robot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/7 14:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class robotMoveTest {

    @Test
    void moveTwiceAndClick() {
        robotMove robotMove = new robotMove();
        robotMove.moveTwiceAndClick();
        System.out.println("====> end");
    }
}