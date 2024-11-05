package com.marks.leetcode.double_pointer.single_sequence;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/5 9:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_28 {
    private final int MOD = (int) (1e9 + 7);

    /**
     * @Description: [
     * 小力将 N 个零件的报价存于数组 nums。小力预算为 target，假定小力仅购买两个零件，要求购买零件的花费不超过预算，请问他有多少种采购方案。
     *
     * 注意：答案需要以 1e9 + 7 (1000000007) 为底取模，如：计算初始结果为：1000000008，请返回 1
     *
     * tips:
     * 2 <= nums.length <= 10^5
     * 1 <= nums[i], target <= 10^5
     * ]
     * @param nums 
     * @param target 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/5 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int purchasePlans(int[] nums, int target) {
        int result = 0;
//        result = method_01(nums, target);
        result = method_02(nums, target);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [2,2,1,9], target = 10
     * 输出：4
     *
     * {1, 2, 2, 9}
     * left = 0, right = 3, temp = 10
     * ans += (3 - 0) = 3
     * left = 1, right = 3, temp = 11
     * left = 1, right = 2, temp = 4
     * ans += 2 - 1 = 4
     * left = 2, right = 2, break
     * 35ms/58.43MB
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/11/5 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int target) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        int ans = 0;

        Arrays.sort(nums);

        while (left < right) {
            int temp = nums[left] + nums[right];
            if (temp > target) {
                right--;
            }else {
                ans += (right - left);
                left++;
            }
            ans = ans % MOD;
        }
        return ans;
    }

    /**
     * @Description: [
     * 这种O(n^2)的时间复杂度超时
     * Pass:47/51
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/11/5 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);

        int ans = 0;
        for (int left = 0; left < n - 1; left++) {
            int temp = target - nums[left];

            if (temp <= 0) {
                break;
            }

            for (int right = n - 1; right > left; right--) {
                if (nums[right] <= temp) {
                    ans = (ans + (right - left)) % MOD;
                    break;
                }
            }
        }

        return ans;
    }
}
