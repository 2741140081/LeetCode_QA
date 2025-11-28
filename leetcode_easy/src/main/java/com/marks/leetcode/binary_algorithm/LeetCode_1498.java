package com.marks.leetcode.binary_algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1498 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/28 15:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1498 {

    private static final int MOD = (int) 1e9 + 7;

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 target 。
     * 请你统计并返回 nums 中能满足其最小元素与最大元素的 和 小于或等于 target 的 非空 子序列的数目。
     * 由于答案可能很大，请将结果对 10^9 + 7 取余后返回。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * 1 <= target <= 10^6
     * @param: nums
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2025/11/28 15:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numSubseq(int[] nums, int target) {
        int result;
        result = method_01(nums, target);
        return result;
    }

    /**
     * @Description:
     * 1. 没什么思路? 首先应该对nums进行排序, 假设当前处于 第i个位置, min = nums[i],
     * 2. 然后转换为二分查找 max = target - min, [i ~ n - 1] 中寻找 max, 假设找到max = nums[j] (j >= i)
     * 3. int ans = 0; count = j - i + 1, ans = (ans + x), x 需要好好计算一下,
     * 4. [0,....,m],非空 仅有一个元素1种, 两个元素的2种[0,1]: 2^0, [0,x,2]:2^1, [0,x,y,3]:4, [0,x,y,z,4]:2^3, ..., [0,x,y,z,m]:2^(m - 2)
     * 5. (2^0 + 2^1 +... + 2^m)
     * 2^5 = 32, 2^4  = 16, 2^3 = 8, 2^2 = 4 2^0;
     * 6. 32 + 16 + 8 + 4 + 1 = 61
     * @param: nums
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2025/11/28 15:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            if (target < nums[i] * 2) {
                // 不满足 min + max <= target
                break;
            }
            int left = i;
            int right = n - 1;
            int index = -1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] + nums[i] > target) {
                    right = mid - 1;
                } else {
                    index = mid;
                    left = mid + 1;
                }
            }
            // 计算数据的长度
            long count = index - i;
            ans = (ans + (1L << count)) % MOD;
        }

        return (int) ans % MOD;
    }

}
