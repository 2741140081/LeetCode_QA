package com.marks.leetcode.daily_question;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1665 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/12 10:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1665 {

    /**
     * @Description:
     * 给你一个任务数组 tasks ，其中 tasks[i] = [actual_i, minimum_i] ：
     * actuali 是完成第 i 个任务 需要耗费 的实际能量。
     * minimumi 是开始第 i 个任务前需要达到的最低能量。
     * 比方说，如果任务为 [10, 12] 且你当前的能量为 11 ，那么你不能开始这个任务。如果你当前的能量为 13 ，你可以完成这个任务，且完成它后剩余能量为 3 。
     * 你可以按照 任意顺序 完成任务。
     * 请你返回完成所有任务的 最少 初始能量。
     *
     * tips:
     * 1 <= tasks.length <= 10^5
     * 1 <= actual_i <= minimum_i <= 10^4
     * @param: tasks
     * @return int
     * @author marks
     * @CreateDate: 2026/05/12 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumEffort(int[][] tasks) {
        int result;
        result = method_01(tasks);
        return result;
    }

    /**
     * @Description:
     * E1: tasks = [[1,3],[2,4],[4,8]]
     * 1. 可以得到最小值 int min = sum(actual[i]), int max = sum(minimum[i]), 当有 max 时必定可以完成所有任务, 无需考虑任务的顺序.
     * 2. 通过二分查找 int mid = (min + max) / 2; 找到最小初始能量使得可以完成所有任务. min = 7, max = 14
     * 3. 如何对 tasks 进行处理, 是按照实际能量排序还是最小能量排序? mid = 10;
     * 3.1 按照 minimum[i] 降序排序, 并且对 actual[i] 升序排序 => [4, 8], [2, 4], [1, 3]
     * @param: tasks
     * @return int
     * @author marks
     * @CreateDate: 2026/05/12 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] tasks) {
        Arrays.sort(tasks, (a, b) -> a[1] - a[0] - (b[1] - b[0]));
        int ans = 0;
        for (int[] task : tasks) {
            ans = Math.max(ans + task[0], task[1]);
        }
        return ans;
    }

}
