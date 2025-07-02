package com.marks.jvm.capter_2;

import java.util.ArrayList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/2 9:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main(String[] args) {
        ArrayList<OOMObject> oomObjectArrayList = new ArrayList<>();
        while (true) {
            oomObjectArrayList.add(new OOMObject());
        }
    }
}
