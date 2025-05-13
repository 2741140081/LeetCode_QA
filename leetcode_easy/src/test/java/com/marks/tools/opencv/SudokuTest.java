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
 * @date 2025/5/13 15:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class SudokuTest {

    @Test
    void imgFrameDetection() {
        String srcImgPath = "D:\\images\\tesseract\\sudoku.png";
        Sudoku sudoku = new Sudoku();
//        String saveImgPath = sudoku.imgFrameDetection(srcImgPath);
        String saveImgWithLinePath = sudoku.imgLineDrawing(srcImgPath);
    }

}