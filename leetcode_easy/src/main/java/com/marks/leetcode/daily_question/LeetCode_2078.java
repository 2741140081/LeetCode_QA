package com.marks.leetcode.daily_question;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2078 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/20 15:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2078 {

    /**
     * @Description:
     * 街上有 n 栋房子整齐地排成一列，每栋房子都粉刷上了漂亮的颜色。给你一个下标从 0 开始且长度为 n 的整数数组 colors ，
     * 其中 colors[i] 表示第  i 栋房子的颜色。
     * 返回 两栋 颜色 不同 房子之间的 最大 距离。
     * 第 i 栋房子和第 j 栋房子之间的距离是 abs(i - j) ，其中 abs(x) 是 x 的绝对值。
     *
     * tips:
     * n == colors.length
     * 2 <= n <= 100
     * 0 <= colors[i] <= 100
     * 生成的测试数据满足 至少 存在 2 栋颜色不同的房子
     * @param: colors
     * @return int
     * @author marks
     * @CreateDate: 2026/04/20 15:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxDistance(int[] colors) {
        int result;
        result = method_01(colors);
        result = method_02(colors);
        return result;
    }

    private int method_02(int[] colors) {
        int n = colors.length;
        int k = 0;
        while (colors[k] == colors[0] && colors[n - 1 - k] == colors[0]) {
            k++;
        }

        return n - 1 - k;
    }

    /**
     * @Description:
     * 1. 暴力, 对于 i, 然后从右向左找到j, colors[j] != colors[i], ans = Max(ans, j - i)
     * 2. 剪枝, 使用set存储已经遍历过的颜色
     * AC: 1ms/42.94MB
     * @param: colors
     * @return int
     * @author marks
     * @CreateDate: 2026/04/20 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] colors) {
        int n = colors.length;
        int ans = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n - 1; i++) {
            if (!set.contains(colors[i])) {
                set.add(colors[i]);
                for (int j = n - 1; j > i; j--) {
                    if (colors[j] != colors[i]) {
                        ans = Math.max(ans, j - i);
                        break;
                    }
                }
            }
        }
        return ans;
    }

}
