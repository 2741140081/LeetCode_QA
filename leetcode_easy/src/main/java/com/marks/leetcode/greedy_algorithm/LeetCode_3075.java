package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/26 9:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3075 {
    
    /**
     * @Description:
     * 给你一个长度为 n 的数组 happiness ，以及一个 正整数 k 。
     * n 个孩子站成一队，其中第 i 个孩子的 幸福值 是 happiness[i] 。你计划组织 k 轮筛选从这 n 个孩子中选出 k 个孩子。
     *
     * 在每一轮选择一个孩子时，所有 尚未 被选中的孩子的 幸福值 将减少 1 。注意，幸福值 不能 变成负数，且只有在它是正数的情况下才会减少。
     *
     * 选择 k 个孩子，并使你选中的孩子幸福值之和最大，返回你能够得到的 最大值 。
     *
     * tips:
     * 1 <= n == happiness.length <= 2 * 10^5
     * 1 <= happiness[i] <= 10^8
     * 1 <= k <= n
     * @param happiness 
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2025/3/26 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumHappinessSum(int[] happiness, int k) {
        long result;
        result = method_01(happiness, k);
        return result;
    }

    /**
     * @Description:
     * AC: 43ms/60.65MB
     * @param happiness 
     * @param k 
     * @return long
     * @author marks
     * @CreateDate: 2025/3/26 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] happiness, int k) {
        Arrays.sort(happiness);
        int index = happiness.length - 1;
        long ans = 0;
        int decrease = 0;
        while (k > 0) {
            ans += (happiness[index] >= decrease ? (long)happiness[index] - decrease : 0);
            index--;
            decrease++;
            k--;
        }
        return ans;
    }
}
