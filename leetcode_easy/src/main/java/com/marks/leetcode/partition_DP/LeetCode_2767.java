package com.marks.leetcode.partition_DP;

import java.util.ArrayList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/20 18:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2767 {
    private ArrayList<String> list = new ArrayList<>();
    public int minimumBeautifulSubstrings(String s) {
        int result = 0;
        result = method_01(s);
        return result;
    }

    private int method_01(String s) {
        for (int i = 0; i < 10; i++) {
            int res = (int) Math.pow(5, i);
            String str = Integer.toBinaryString(res);
            if (str.length() <= 15) {
                list.add(str);
            }
        }
        return 0;
    }
}
