package com.marks.tools.tesseract;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/6 17:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class MixedLanguageOCRTest {

    @Test
    void tesseractOCRImage() {
        String imagePath = "D:\\images\\tesseract\\input_data_chi.png";
        MixedLanguageOCR ocr = new MixedLanguageOCR();
        String result = ocr.TesseractOCRImage(imagePath);
    }
}