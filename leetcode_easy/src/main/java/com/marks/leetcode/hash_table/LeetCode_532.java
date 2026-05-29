package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_532 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/29 14:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_532 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k，请你在数组中找出 不同的 k-diff 数对，并返回不同的 k-diff 数对 的数目。
     * k-diff 数对定义为一个整数对 (nums[i], nums[j]) ，并满足下述全部条件：
     * 0 <= i, j < nums.length
     * i != j
     * |nums[i] - nums[j]| == k
     * 注意，|val| 表示 val 的绝对值。
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/05/29 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findPairs(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }


    /**
     * @Description:
     * 1. 先排序, 升序排序
     * 2. 遍历数组, 对于 nums[i], 使用 Set 存储[0, i - 1] 之间的所有数字
     * 3. set 中查找 nums[i] - k, 如果存在, 则 ans++
     * 4. 将当前 nums[i] 添加到 set 中
     * 5. 需要对 nums[] 数组进行去重操作
     * AC: 18ms/46.7MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/05/29 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        // 对 nums[] 数组进行排序
        Arrays.sort(nums);
        int ans = 0;
        map.put(nums[0], 1);
        for (int i = 1; i < n; i++) {
            // 如果 k = 0, 则 nums[i] = nums[j], 并且 i != j
            if (k == 0) {
                if (map.containsKey(nums[i]) && map.get(nums[i]) == 1) {
                    ans++;
                }
            } else {
                if (map.containsKey(nums[i] - k) && !map.containsKey(nums[i])) { // 表示第一次找到
                    ans++;
                }
            }
            map.merge(nums[i], 1, Integer::sum);
        }

        return ans;
    }

}
