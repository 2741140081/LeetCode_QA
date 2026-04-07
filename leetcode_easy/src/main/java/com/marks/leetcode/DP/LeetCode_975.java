package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_975 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/3 9:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_975 {


    /**
     * @Description:
     * 给定一个整数数组 arr，你可以从某一起始索引出发，跳跃一定次数。
     * 在你跳跃的过程中，第 1、3、5... 次跳跃称为奇数跳跃，而第 2、4、6... 次跳跃称为偶数跳跃。
     * 你可以按以下方式从索引 i 向后跳转到索引 j（其中 i < j）：
     * 在进行奇数跳跃时（如，第 1，3，5... 次跳跃），你将会跳到索引 j，使得 arr[i] <= arr[j]，且 arr[j] 的值尽可能小。如果存在多个这样的索引 j，你只能跳到满足要求的最小索引 j 上。
     * 在进行偶数跳跃时（如，第 2，4，6... 次跳跃），你将会跳到索引 j，使得 arr[i] >= arr[j]，且 arr[j] 的值尽可能大。如果存在多个这样的索引 j，你只能跳到满足要求的最小索引 j 上。
     * （对于某些索引 i，可能无法进行合乎要求的跳跃。）
     * 如果从某一索引开始跳跃一定次数（可能是 0 次或多次），就可以到达数组的末尾（索引 arr.length - 1），那么该索引就会被认为是好的起始索引。
     *
     * 返回好的起始索引的数量。
     * tips:
     * 1 <= arr.length <= 20000
     * 0 <= arr[i] < 100000
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/04/03 9:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int oddEvenJumps(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * 1. 明确起始点是 n - 1, 在开始时可以进行奇数或者偶数跳跃, dp[n - 1] = 1, 相当于跳跃 0 次, 也是一个好的起始索引,  条件需要相反
     * 2. 奇数跳跃时, j = n - 1, 求 i, arr[i] <= arr[j], 并且 如果存在多个这样的索引, 找到最大索引的 i, dp[i] = dp[j] + 1
     * 3.
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/04/03 9:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {

        return 0;
    }

}
