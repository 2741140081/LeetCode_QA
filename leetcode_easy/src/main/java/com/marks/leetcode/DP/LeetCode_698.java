package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/21 11:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_698 {

    
    /**
     * @Description:
     * 给定一个整数数组  nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
     *
     * tips:
     * 1 <= k <= len(nums) <= 16
     * 0 < nums[i] < 10000
     * 每个元素的频率在 [1,4] 范围内
     * @param nums 
     * @param k
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/21 11:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        boolean result;
        result = method_01(nums, k);
        return result;
    }

    
    /**
     * @Description:
     * 1. 先判断sum % k == 0
     * 2. 不对, 只是说需要把数组分成k个子集, 没有说明是需要连续的子集
     * 3. 对于 nums[i], 可以插入到 [0 ~ k - 1] 的任意一个集合中
     * 4. 目标子集的集合sum值是确定的, 即 sum % k == 0, 在合法情况下 int target = sum / k;
     *
     * @param nums 
     * @param k 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/21 11:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0) {
            // 不合法情况
            return false;
        }
        Arrays.sort(nums); // 排序
        int n = nums.length;
        int target = sum / k;
        while (k > 0) {
            // 找到 k 组 子集, 使其子集和 = target

            k--;
        }

        return false;
    }

}
