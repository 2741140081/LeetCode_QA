package com.marks.leetcode.difference_array;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3347 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/11 16:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3347 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和两个整数 k 和 numOperations 。
     * 你必须对 nums 执行 操作  numOperations 次。每次操作中，你可以：
     * 选择一个下标 i ，它在之前的操作中 没有 被选择过。
     * 将 nums[i] 增加范围 [-k, k] 中的一个整数。
     * 在执行完所有操作以后，请你返回 nums 中出现 频率最高 元素的出现次数。
     * 一个元素 x 的 频率 指的是它在数组中出现的次数。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 0 <= k <= 10^9
     * 0 <= numOperations <= nums.length
     * @param: nums
     * @param: k
     * @param: numOperations
     * @return int
     * @author marks
     * @CreateDate: 2026/06/11 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxFrequency(int[] nums, int k, int numOperations) {
        int result;
        result = method_01(nums, k, numOperations);
        return result;
    }

    /**
     * @Description:
     * 1. 差不多理解了, 对于 nums[i], 取值返回是 nums[i] - k, nums[i] + k 之间的数字,
     * 2. 但是由于只能操作 numOperations 次, 并且每一个元素仅能操作一次.
     * 3. 使用 map 存储 nums[i] 及其出现的次数
     * 4. 差分数组, 由于值较大, 所以需要通过离散化来处理, 并且由于限制操作次数, 所以需要在离散化和差分时需要额外存储操作次数
     * @param: nums
     * @param: k
     * @param: numOperations
     * @return int
     * @author marks
     * @CreateDate: 2026/06/11 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k, int numOperations) {
        Map<Integer, Integer> cnt = new HashMap<>();
        Map<Integer, Integer> diff = new TreeMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum); // cnt[x]++
            diff.putIfAbsent(x, 0); // 把 x 插入 diff，以保证下面能遍历到 x
            // 把 [x-k, x+k] 中的每个整数的出现次数都加一
            diff.merge(x - k, 1, Integer::sum); // diff[x-k]++
            diff.merge(x + k + 1, -1, Integer::sum); // diff[x+k+1]--
        }

        int ans = 0;
        int sumD = 0;
        for (Map.Entry<Integer, Integer> e : diff.entrySet()) {
            sumD += e.getValue();
            ans = Math.max(ans, Math.min(sumD, cnt.getOrDefault(e.getKey(), 0) + numOperations));
        }
        return ans;
    }

}
