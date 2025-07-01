package com.marks.tools.opencv;

import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/6/4 16:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class SudokuSolverTest {

    @Test
    void solveSudo() throws Exception {
        String imagePath = "D:\\images\\save\\sudoku_37.png";
        SudokuSolver solver = new SudokuSolver();
        solver.solveSudo(imagePath);
    }
}