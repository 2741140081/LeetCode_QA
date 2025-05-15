package com.marks.tools.robot;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/15 11:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class NotePadPlusTest {

    private NotePadPlus notePadPlus = new NotePadPlus();

    @Test
    void getDisplayScale() {
        notePadPlus.getDisplayScale();
    }


    @Test
    void openNotePadPlusExe() throws Exception {
        String srcPath = "D:\\images\\targetImage\\NotepadPlusPlus.png";
        notePadPlus.openNotePadPlusExe(srcPath);
        String srcPath1 = "D:\\images\\targetImage\\NotepadPlusPlusNewFileBtn.png";
        notePadPlus.createNewFile(srcPath1);
        String srcPath2 = "D:\\images\\targetImage\\NotepadPlusFileBtn.png";
        notePadPlus.exitNotePadPlus(srcPath2);
    }

    @Test
    void createNewFile() throws Exception {
        String srcPath = "D:\\images\\targetImage\\NotepadPlusPlusNewFileBtn.png";
        notePadPlus.createNewFile(srcPath);
    }

    @Test
    void exitNotePadPlus() throws Exception {
        String srcPath = "D:\\images\\targetImage\\NotepadPlusFileBtn.png";
        notePadPlus.exitNotePadPlus(srcPath);
    }

}