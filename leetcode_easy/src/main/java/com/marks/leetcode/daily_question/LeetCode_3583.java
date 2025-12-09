package com.marks.leetcode.daily_question;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3583 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 10:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3583 {

    private static final int MOD = (int) 1e9 + 7;

    /**
     * @Description:
     * 给你一个整数数组 nums。
     * 特殊三元组 定义为满足以下条件的下标三元组 (i, j, k)：
     * 0 <= i < j < k < n，其中 n = nums.length
     * nums[i] == nums[j] * 2
     * nums[k] == nums[j] * 2
     * 返回数组中 特殊三元组 的总数。
     *
     * 由于答案可能非常大，请返回结果对 109 + 7 取余数后的值。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/09 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int specialTriplets(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 分别使用两个Map<Integer, Long>存储 i 前后的数字及其出现的次数 prefix, suffix
     * 2. 每次移动i时(i++), prefix.put(nums[i], prefix.getOrDefault(nums[i], 0) + 1), suffix.put(nums[i], suffix.getOrDefault(nums[i], 0) - 1)
     * 3. long ans = 0; ans = (ans + x1 * x2) % MOD; x1 = prefix.getOrDefault(nums[i] * 2, 0); x2 = suffix.getOrDefault(nums[i] * 2, 0);
     * 4. AC: 315ms/185.41MB 这个时间和空间复杂度太高了吧, 但是我的方法是O(n) 的复杂度, 感觉是Map的问题, 不管了
     *
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/09 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        Map<Integer, Long> prefix = new HashMap<>();
        Map<Integer, Long> suffix = new HashMap<>();
        // 先做后缀处理
        for (int i = 1; i < n; i++) {
            suffix.put(nums[i], suffix.getOrDefault(nums[i], 0L) + 1);
        }
        // 前缀为nums[0]
        prefix.put(nums[0], 1L);
        long ans = 0;
        // 处理j, 范围是[1~n-2]
        for (int i = 1; i < n - 1; i++) {
            suffix.put(nums[i], suffix.getOrDefault(nums[i], 0L) - 1);
            long x1 = prefix.getOrDefault(nums[i] * 2, 0L);
            long x2 = suffix.getOrDefault(nums[i] * 2, 0L);
            ans = (ans + x1 * x2) % MOD;
            prefix.put(nums[i], prefix.getOrDefault(nums[i], 0L) + 1);
        }
        return (int) (ans % MOD);
    }

}
