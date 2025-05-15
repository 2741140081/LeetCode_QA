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
 * @date 2025/5/14 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class OpenCvOperationTest {
    private OpenCvOperation cvOperation = new OpenCvOperation();

    @Test
    void addPictureByOpenCv() {
        String starSkyFilePath = "D:\\images\\opencv\\star_sky.jpg";
//        String landscapeFilePath = "D:\\images\\opencv\\landscape.jpg";
        String cartoonFilePath = "D:\\images\\opencv\\cartoon.jpg";
        cvOperation.addPictureByOpenCv(starSkyFilePath, cartoonFilePath);
    }

    @Test
    void findHoughLines() {
        String imgPath = "D:\\opencvProject\\TestCV\\Board.jpg";
        String result = cvOperation.findHoughLines(imgPath);
        System.out.println(result);
    }

    @Test
    void matBatch() {
        cvOperation.matBatch();
    }

    @Test
    void simpleMat() {
        cvOperation.simpleMat();

    }

    @Test
    void drawChessBoard() {
        cvOperation.drawChessBoard();

    }

    @Test
    void drawCurve() {
        cvOperation.drawCurve();

    }

    @Test
    void drawRainbow() {
        cvOperation.drawRainbow();

    }


    @Test
    void houghCircle() {
        String imgPath = "D:\\opencvProject\\TestCV\\chess.jpg";
        cvOperation.houghCircle(imgPath);

    }

    @Test
    void getCartoon() {
        String imgPath = "D:\\images\\opencv\\cartoon.jpg";
        cvOperation.getCartoon(imgPath);

    }
}