package com.marks.leetcode.data_structure.prefix_sum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/13 17:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_974 {
    /**
     * @Description:
     * 给定一个整数数组 nums 和一个整数 k ，返回其中元素之和可被 k 整除的非空 子数组 的数目。
     *
     * 子数组 是数组中 连续 的部分。
     * tips:
     * 1 <= nums.length <= 3 * 10^4
     * -10^4 <= nums[i] <= 10^4
     * 2 <= k <= 10^4
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/13 17:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int subarraysDivByK(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 输入：nums = [4,5,0,-2,-3,1], k = 5
     * 输出：7
     * 解释：
     * 有 7 个子数组满足其元素之和可被 k = 5 整除：
     * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
     *
     * AC:25ms/46.51MB
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/1/13 17:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int preSum = 0;
        for (int i = 0; i < n; i++) {
            preSum += nums[i];
            int key = (preSum % k + k) % k;
            if (map.containsKey(key)) {
                ans += map.get(key);
            }
            map.put(key, map.getOrDefault(key, 0) + 1);
        }
        return ans;
    }
}
