package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_982 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/26 10:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_982 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，返回其中 按位与三元组 的数目。
     *
     * 按位与三元组 是由下标 (i, j, k) 组成的三元组，并满足下述全部条件：
     *
     * 0 <= i < nums.length
     * 0 <= j < nums.length
     * 0 <= k < nums.length
     * nums[i] & nums[j] & nums[k] == 0 ，其中 & 表示按位与运算符。
     *
     * tips:
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] < 2^16
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/26 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countTriplets(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description: [方法描述]
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/26 10:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {

        return 0;
    }

    /**
     * @Description:
     * 1. 暴力解法应该 O(n^3), n 是 10^3, 所以总的是 10^9, 大概率超时.
     * 2. 先计算 int x = nums[i] & nums[j]; 将 x 存入map 集合, key 为 x, value 为 x 的个数
     * AC: 1610ms/49.8MB
     * 事件复杂度太高了, 查看官方题解, 看看如何优化.
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/26 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                map.merge(nums[i] & nums[j], 1, Integer::sum);
            }
        }
        int ans = 0;
        for (int k = 0; k < nums.length; k++) {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if ((entry.getKey() & nums[k]) == 0) {
                    ans += entry.getValue();
                }
            }
        }

        return ans;
    }

}
