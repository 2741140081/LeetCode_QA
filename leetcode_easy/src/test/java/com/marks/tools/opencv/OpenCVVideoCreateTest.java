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
 * @date 2025/5/12 15:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class OpenCVVideoCreateTest {

    @Test
    void createVideoByImage() {
        OpenCVVideoCreate videoCreate = new OpenCVVideoCreate();
        videoCreate.createVideoByImage();
    }
}