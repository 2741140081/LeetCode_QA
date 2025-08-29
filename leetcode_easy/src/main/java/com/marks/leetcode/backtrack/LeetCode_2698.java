package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/22 17:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2698 {

    /**
     * @Description:
     * 给你一个正整数 n ，请你返回 n 的 惩罚数 。
     *
     * n 的 惩罚数 定义为所有满足以下条件 i 的数的平方和：
     *
     * 1 <= i <= n
     * i * i 的十进制表示的字符串可以分割成若干连续子字符串，且这些子字符串对应的整数值之和等于 i 。
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/8/22 17:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int punishmentNumber(int n) {
        int result;
        // 获取当前系统时间
        long startTime = System.currentTimeMillis();
        result = method_01(n);
        // 获取当前结束时的系统时间
        long endTime = System.currentTimeMillis();
        System.out.println("耗时: " + (endTime - startTime) + "ms");
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 10
     * 输出：182
     *
     * AC: 126ms(35.27%)/43.8MB(7.59%)
     * @param n 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/22 17:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans;
    private int n;

    private boolean flag;
    private int method_01(int n) {
        ans = 0;
        for (int i = 1; i <= n; i++) {
            int temp = i * i;
            String s = String.valueOf(temp);
            this.n = s.length();
            int left = 0;
            int sum = 0;
            flag = false;
            backtrack(s, left, sum, i);
        }
        return ans;
    }

    private void backtrack(String s, int left, int sum, int target) {
        if (flag) {
            return;
        }
        if (left == n) {
            if (sum == target) {
                ans += Integer.parseInt(s);
                flag = true;
            }
            return;
        }
        for (int right = left; right < n; right++) {
            //
            int nextValue = Integer.parseInt(s.substring(left, right + 1));
            if (sum + nextValue <= target) {
                backtrack(s, right + 1, sum + nextValue, target);
            }
        }
    }
}
