package com.marks.tools.opencv;

import org.junit.jupiter.api.Test;


/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/23 10:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class SurfFeatureExtractorTest {

    @Test
    void readImage() {
        String imagePath = "D:\\images\\opencv\\image_recognition\\cartoon.jpg";
        SurfFeatureExtractor featureExtractor = new SurfFeatureExtractor();
        Mat mat1 = featureExtractor.readImage(imagePath);
        Mat mat = featureExtractor.readImage(imagePath);
        MatOfKeyPoint keyPoint = featureExtractor.extract(mat);
        System.out.println("====> start");

        Mat captureMat = featureExtractor.captureDesktopRegion(414, 172, 427, 240);



    }
}