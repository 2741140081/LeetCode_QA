package com.marks.tools.opencv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/9 10:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class GraphicProcessTest {

    @Test
    void imageTranslate() {
        GraphicProcess graphicProcess = new GraphicProcess();
//        graphicProcess.imageTranslate();

        graphicProcess.ballRebound();
        graphicProcess.closeAllWindows();
    }
}