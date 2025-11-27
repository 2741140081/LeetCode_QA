package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3638 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/25 15:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3638 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 weight，表示按直线排列的 n 个包裹的重量。装运 定义为包裹的一个连续子数组。
     * 如果一个装运满足以下条件，则称其为 平衡装运：最后一个包裹的重量 严格小于 该装运中所有包裹中 最大重量 。
     * 选择若干个 不重叠 的连续平衡装运，并满足 每个包裹最多出现在一次装运中（部分包裹可以不被装运）。
     * 返回 可以形成的平衡装运的最大数量 。
     *
     * tips:
     * 2 <= n <= 10^5
     * 1 <= weight[i] <= 10^9
     * @param: weight
     * @return int
     * @author marks
     * @CreateDate: 2025/11/25 15:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxBalancedShipments(int[] weight) {
        int result;
        result = method_01(weight);
        return result;
    }

    /**
     * @Description:
     * 输入: weight = [2,5,1,4,3]
     * 输出: 2
     * 1. 每个包裹最多只能出现在一次装运中，并且每次转运最小需要两个包裹, 并且两个包裹不相同。
     * 2. 完全可以用左右指针 + 排序来解决, 不行, 不能用双指针来解决, 因为这并不是两个指针向中间移动的过程
     * 3. 装运的概念在此处的定义是连续的子数组, 所以不能进行排序。
     * AC: 3ms/106.8MB
     * @param: weight
     * @return int
     * @author marks
     * @CreateDate: 2025/11/25 15:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] weight) {
        int ans = 0;
        int n = weight.length;
        for (int i = 0; i < n - 1; i++) {
            if (weight[i] - weight[i + 1] > 0) {
                ans++;
                i++;
            }
        }

        return ans;
    }

}
