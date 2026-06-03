package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2170 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/3 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2170 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的数组 nums ，该数组由 n 个正整数组成。
     * 如果满足下述条件，则数组 nums 是一个 交替数组 ：
     * nums[i - 2] == nums[i] ，其中 2 <= i <= n - 1 。
     * nums[i - 1] != nums[i] ，其中 1 <= i <= n - 1 。
     * 在一步 操作 中，你可以选择下标 i 并将 nums[i] 更改 为 任一 正整数。
     * 返回使数组变成交替数组的 最少操作数 。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/06/03 15:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumOperations(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 先将整个数组分割成两个子序列, 分别是奇数序列和偶数序列
     * 2. 分别统计奇数和偶数序列中各个数字出现的频率, 分别记录最高的频率以及对于的数字, 如果不存在使用 -1 表示
     * AC: 68ms/109.48MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/06/03 15:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        Map<Integer, Integer> oddMap = new HashMap<>();
        Map<Integer, Integer> evenMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                evenMap.merge(nums[i], 1, Integer::sum);
            } else {
                oddMap.merge(nums[i], 1, Integer::sum);
            }
        }
        int[] odd = {0, 0};
        for (Map.Entry<Integer, Integer> entry : oddMap.entrySet()) {
            if (odd[0] == 0) {
                odd[0] = entry.getKey();
            } else {
                if (entry.getValue() < oddMap.get(odd[0])) {
                    odd[1] = entry.getValue() > odd[1] ? entry.getKey() : odd[1];
                } else {
                    odd[1] = odd[0];
                    odd[0] = entry.getKey();
                }
            }
        }
        int[] even = {0, 0};

        for (Map.Entry<Integer, Integer> entry : evenMap.entrySet()) {
            if (even[0] == 0) {
                even[0] = entry.getKey();
            } else {
                if (entry.getValue() < evenMap.get(even[0])) {
                    even[1] = entry.getValue() > even[1] ? entry.getKey() : even[1];
                } else {
                    even[1] = even[0];
                    even[0] = entry.getKey();
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < 2; i++) {
            if (even[i] != 0) {
                for (int j = 0; j < 2; j++) {
                    if (odd[j] != 0) {
                        if (even[i] != odd[j]) {
                            ans = Math.max(ans, evenMap.get(even[i]) + oddMap.get(odd[j]));
                        } else {
                            int max = Math.max(evenMap.get(even[i]), oddMap.get(odd[j]));
                            ans = Math.max(ans, max);
                        }
                    }
                }
            }
        }

        return n - ans;
    }

}
