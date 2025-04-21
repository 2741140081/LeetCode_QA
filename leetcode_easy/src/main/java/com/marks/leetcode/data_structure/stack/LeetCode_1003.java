package com.marks.leetcode.data_structure.stack;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/17 14:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1003 {
    public boolean isValid(String s) {
        boolean result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * AC: 16ms/ 43.68MB
     * 优化一下判断, 进行剪枝操作
     * AC: 10ms/ 43.75MB
     * @param s
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/17 14:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s) {
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == 'c') {
                int n = list.size();
                if (n >= 2 && list.get(n - 2) == 'a' && list.get(n - 1) == 'b') {
                    list.remove(n - 1);
                    list.remove(n - 2);
                } else {
                    return false;
                }
            } else {
                list.add(ch);
            }
        }
        return list.size() == 0 ? true : false;
    }
}
