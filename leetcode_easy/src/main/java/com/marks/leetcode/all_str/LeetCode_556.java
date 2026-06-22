package com.marks.leetcode.all_str;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_556 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/18 15:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_556 {

    /**
     * @Description:
     * 给你一个正整数 n ，请你找出符合条件的最小整数，其由重新排列 n 中存在的每位数字组成，并且其值大于 n 。
     * 如果不存在这样的正整数，则返回 -1 。
     * 注意 ，返回的整数应当是一个 32 位整数 ，如果存在满足题意的答案，但不是 32 位整数 ，同样返回 -1 。
     * tips:
     * 1 <= n <= 2^31 - 1
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/06/18 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int nextGreaterElement(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 从后向前找到第一个递减的位置
     * AC: 1ms/41.61MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/06/18 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        String nStr = String.valueOf(n);
        List<Integer> list = new ArrayList<>();
        int len = nStr.length();
        int idx = -1;
        for (int i = len - 1; i >= 0; i--) {
            int num = nStr.charAt(i) - '0';
            if (list.isEmpty() || num >= list.get(list.size() - 1)) {
                list.add(num);
            } else {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            StringBuilder ans = new StringBuilder();
            for (int i = 0; i < idx; i++) {
                ans.append(nStr.charAt(i));
            }
            // 找到 list中第一个比 nStr.charAt(idx)大的数
            for (int i = 0; i < list.size(); i++) {
                int num = list.get(i);
                if (num > nStr.charAt(idx) - '0') {
                    ans.append(num);
                    // 替换
                    list.set(i, nStr.charAt(idx) - '0');
                    break;
                }
            }
            // 剩余的数字
            for (Integer j : list) {
                ans.append(j);
            }
            // 将 ans 转换为 long
            long ansLong = Long.parseLong(ans.toString());
            if (ansLong > Integer.MAX_VALUE) {
                return -1;
            } else {
                return (int) ansLong;
            }
        }

        return -1;
    }

}
