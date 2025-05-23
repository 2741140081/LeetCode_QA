package com.marks.bom;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FilenameFilter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/19 15:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class BMBXMLParserTest {

    @Test
    void xmlParser() throws Exception {
        String filePath = "D:\\BMB\\BCE Use Cases sample";
        File dir = new File(filePath);
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".xml"));

        BMBXMLParser bmb = new BMBXMLParser();

        for (int i = 0; i < files.length; i++) {
            bmb.method_02(files[i].getPath());
        }
    }
}