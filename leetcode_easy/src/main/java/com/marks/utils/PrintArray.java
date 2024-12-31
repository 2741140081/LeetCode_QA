package com.marks.utils;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/31 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class PrintArray {
    public void print(int[] array) {
        int n = array.length;
        if (n == 0) {
            System.out.println("{}");
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (int i = 0; i < n - 1; i++) {
            sb.append(array[i] + ", ");
        }
        sb.append(array[n - 1] + "}");
        System.out.println(sb);
    }
}
