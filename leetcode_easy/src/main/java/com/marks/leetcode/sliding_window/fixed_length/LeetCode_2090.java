package com.marks.leetcode.sliding_window.fixed_length;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/9 16:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2090 {
    public int[] getAverages(int[] nums, int k) {
        int[] result;
        result = method_01(nums, k);
        return result;
    }
    /**
     * @Description: [
     * 输入：nums = [7,4,3,9,1,8,5,2,6], k = 3
     * 输出：[-1,-1,-1,5,4,4,-1,-1,-1]
     * 解释：
     * - avg[0]、avg[1] 和 avg[2] 是 -1 ，因为在这几个下标前的元素数量都不足 k 个。
     * - 中心为下标 3 且半径为 3 的子数组的元素总和是：7 + 4 + 3 + 9 + 1 + 8 + 5 = 37 。
     *   使用截断式 整数除法，avg[3] = 37 / 7 = 5 。
     * - 中心为下标 4 的子数组，avg[4] = (4 + 3 + 9 + 1 + 8 + 5 + 2) / 7 = 4 。
     * - 中心为下标 5 的子数组，avg[5] = (3 + 9 + 1 + 8 + 5 + 2 + 6) / 7 = 4 。
     * - avg[6]、avg[7] 和 avg[8] 是 -1 ，因为在这几个下标后的元素数量都不足 k 个。
     * ]
     * @param nums
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2024/10/9 16:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        if (n < 2 * k + 1) {
            return ans;
        }
        long sum = 0;
        for (int i = 0; i < 2 * k + 1; i++) {
            sum += nums[i];
        }
        ans[k] =(int) sum / (2 * k + 1);
        for (int i = 2 * k + 1; i < n; i++) {
            sum = sum + nums[i] - nums[i - 2 * k - 1];
            ans[i - k] =(int) sum / (2 * k + 1);
        }
        return ans;
    }
}
