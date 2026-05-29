package com.marks.leetcode.hash_table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2453 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/28 16:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2453 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的数组 nums ，它包含若干正整数，表示数轴上你需要摧毁的目标所在的位置。同时给你一个整数 space 。
     * 你有一台机器可以摧毁目标。给机器 输入 nums[i] ，
     * 这台机器会摧毁所有位置在 nums[i] + c * space 的目标，其中 c 是任意非负整数。你想摧毁 nums 中 尽可能多 的目标。
     * 请你返回在摧毁数目最多的前提下，nums[i] 的 最小值 。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= space <= 10^9
     * @param: nums
     * @param: space
     * @return int
     * @author marks
     * @CreateDate: 2026/05/28 16:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int destroyTargets(int[] nums, int space) {
        int result;
        result = method_01(nums, space);
        return result;
    }

    /**
     * @Description:
     * 1. 对nums[i] 进行操作, 假设存在一个数 x, (nums[i] - x) % space == 0, 那么 x 就是 nums[i] 的最小值
     * 2. 那么 x = nums[i] % space, 然后使用 map 统计 x 出现的次数, 选择出次数最多并且 x 最小的数
     * 3. 少了一个条件, x 必须是 nums[] 数组中的元素, 改变思路
     * 4. 先对 nums[] 数组进行升序排序, x 要与最小的 nums[i] 进行一一对应, 使用 map 存储
     * AC: 63ms/114.43MB
     * @param: nums
     * @param: space
     * @return int
     * @author marks
     * @CreateDate: 2026/05/28 16:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int space) {
        Map<Integer, Integer> indexMap = new HashMap<>();
        Map<Integer, Integer> countMap = new HashMap<>();
        int n = nums.length;
        Arrays.sort(nums); // 升序排序
        for (int i = 0; i < n; i++) {
            int x = nums[i] % space;
            // 判断 x 是否存在 于 countMap 中
            if (!countMap.containsKey(x)) {
                indexMap.put(x, i);
            }
            countMap.merge(x, 1, Integer::sum);
        }
        int ans = 0;
        int maxCount = 0;
        // 遍历 map
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > maxCount || (entry.getValue() == maxCount && nums[indexMap.get(entry.getKey())] < ans)) {
                ans = nums[indexMap.get(entry.getKey())];
                maxCount = entry.getValue();
            }
        }

        return ans;
    }

}
