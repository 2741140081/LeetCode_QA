package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_826 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/11 11:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_826 {

    /**
     * @Description:
     * 你有 n 个工作和 m 个工人。给定三个数组： difficulty, profit 和 worker ，其中:
     * difficulty[i] 表示第 i 个工作的难度，profit[i] 表示第 i 个工作的收益。
     * worker[i] 是第 i 个工人的能力，即该工人只能完成难度小于等于 worker[i] 的工作。
     * 每个工人 最多 只能安排 一个 工作，但是一个工作可以 完成多次 。
     * 举个例子，如果 3 个工人都尝试完成一份报酬为 $1 的同样工作，那么总收益为 $3 。如果一个工人不能完成任何工作，他的收益为 $0 。
     * 返回 在把工人分配到工作岗位后，我们所能获得的最大利润 。
     *
     * tips:
     * n == difficulty.length
     * n == profit.length
     * m == worker.length
     * 1 <= n, m <= 10^4
     * 1 <= difficulty[i], profit[i], worker[i] <= 10^5
     * @param: difficulty
     * @param: profit
     * @param: worker
     * @return int
     * @author marks
     * @CreateDate: 2026/05/11 11:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int result;
        result = method_01(difficulty, profit, worker);
        return result;
    }

    /**
     * @Description:
     * 1. 贪心, 即先安排工作收益高的工作
     * AC: 46ms/47.77MB
     * @param: difficulty
     * @param: profit
     * @param: worker
     * @return int
     * @author marks
     * @CreateDate: 2026/05/11 11:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] difficulty, int[] profit, int[] worker) {
        int n = difficulty.length;
        int m = worker.length;
        Integer[] index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        // 通过索引排序, 间接对 profit[] 降序排序
        Arrays.sort(index,  Comparator.comparingInt((Integer i) -> profit[i]).reversed());
        // 然后直接对 worker[] 进行降序排序
        int[] array = Arrays.stream(worker).boxed().sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue).toArray();
        int left = 0;
        int right = 0;
        int ans = 0;
        while (left < n && right < m) {
            int value = profit[index[left]];
            int currDifficulty = difficulty[index[left]];
            while (right < m && array[right] >= currDifficulty) {
                ans += value;
                right++;
            }
            left++;
        }
        return ans;
    }

}
