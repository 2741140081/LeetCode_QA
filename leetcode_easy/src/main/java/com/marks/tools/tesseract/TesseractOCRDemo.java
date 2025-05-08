package com.marks.tools.tesseract;

import net.sourceforge.tess4j.Tesseract;

import java.io.File;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/6 10:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class TesseractOCRDemo {

    public static void main(String[] args) {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:\\Tesseract_OCR\\tessdata");
        tesseract.setLanguage("chi_sim");
        File file = new File("D:\\images\\tesseract\\beijing_data_center.png");
        try {
            String outputString = tesseract.doOCR(file);
            System.out.println(outputString);
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
