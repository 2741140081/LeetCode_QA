package com.marks.leetcode.state_machine_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/11 9:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1186 {
    /**
     * @Description: [
     * 给你一个整数数组，返回它的某个 非空 子数组（连续元素）在执行一次可选的删除操作后，所能得到的最大元素总和。
     * 换句话说，你可以从原数组中选出一个子数组，并可以决定要不要从中删除一个元素（只能删一次哦），
     * （删除后）子数组中至少应当有一个元素，然后该子数组（剩下）的元素总和是所有子数组之中最大的。
     *
     * 注意，删除一个元素后，子数组 不能为空。
     * tips:
     * 1 <= arr.length <= 105
     * -104 <= arr[i] <= 104
     * ]
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumSum(int[] arr) {
        int result = 0;
//        result = method_01(arr);
        result = method_02(arr);
        return result;
    }

    /**
     * @Description: [
     * AC:5ms/52.17MB
     * ]
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] arr) {
        int n = arr.length;
        if (n == 1) {
            return arr[0];
        }
        int dp0 = arr[0];
        int dp1 = 0;
        int ans = arr[0];
        for (int i = 1; i < n; i++) {
            dp1 = Math.max(dp1 + arr[i], dp0);
            dp0 = Math.max(dp0, 0) + arr[i];
            ans = Math.max(ans, Math.max(dp0, dp1));
        }
        return ans;
    }

    /**
     * @Description: [
     * arr = [1,-2,0,3]
     * 感觉与LeetCode_1493类似, 也是删除一个元素(但是该题是可以删除或者不删除)
     * 查看官方题解的状态转移方程后得到:
     * AC:6ms/52.19MB
     * 由于dp[i] 只与dp[i - 1] 存在关联, 所以可以使用空间优化复杂度
     * @see LeetCode_1493#longestSubarray(int[])
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int n = arr.length;
        if (n == 1) {
            return arr[0];
        }
        int[] dp0 = new int[n]; // 是否因该在dp处添加一个转态, 用来表示dp是否已经执行过删除一个元素的操作,0 表示未删除, 1 表示删除
        int[] dp1 = new int[n]; // 是否因该在dp处添加一个转态, 用来表示dp是否已经执行过删除一个元素的操作,0 表示未删除, 1 表示删除
        dp0[0] = arr[0];
        dp1[0] = 0;
        int ans = arr[0];
        for (int i = 1; i < n; i++) {
            dp0[i] = Math.max(dp0[i - 1], 0) + arr[i];
            dp1[i] = Math.max(dp1[i - 1] + arr[i], dp0[i - 1]);
            ans = Math.max(ans, Math.max(dp0[i], dp1[i]));
        }
        return ans;
    }
}
