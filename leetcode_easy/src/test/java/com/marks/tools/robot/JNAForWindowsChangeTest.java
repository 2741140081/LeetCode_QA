package com.marks.tools.robot;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/6/3 15:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class JNAForWindowsChangeTest {

    @Test
    void getForceForWindowsName() throws InterruptedException {
        String[] arr = new String[]{"Google Chrome", "Microsoft Teams", "NotePad++", "Outlook"};
        List<String> list = Arrays.asList(arr);
        JNAForWindowsChange jna = new JNAForWindowsChange(list);

    }


}