package com.marks.leetcode.array_hard;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_220 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/11 11:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_220 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和两个整数 indexDiff 和 valueDiff 。
     * 找出满足下述条件的下标对 (i, j)：
     * i != j,
     * abs(i - j) <= indexDiff
     * abs(nums[i] - nums[j]) <= valueDiff
     * 如果存在，返回 true ；否则，返回 false 。
     *
     * tips:
     * 2 <= nums.length <= 10^5
     * -10^9 <= nums[i] <= 10^9
     * 1 <= indexDiff <= nums.length
     * 0 <= valueDiff <= 10^9
     * @param: nums
     * @param: indexDiff
     * @param: valueDiff
     * @return boolean
     * @author marks
     * @CreateDate: 2026/08/11 11:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        boolean result;
        result = method_01(nums, indexDiff, valueDiff);
        result = method_02(nums, indexDiff, valueDiff);
        return result;
    }

    /**
     * @Description:
     * 1. 使用桶排序优化
     * @param: nums
     * @param: indexDiff
     * @param: valueDiff
     * @return boolean
     * @author marks
     * @CreateDate: 2026/08/11 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_02(int[] nums, int indexDiff, int valueDiff) {
        int n = nums.length;
        Map<Long, Long> map = new HashMap<Long, Long>();
        long w = (long) valueDiff + 1;
        for (int i = 0; i < n; i++) {
            long id = getID(nums[i], w);
            if (map.containsKey(id)) {
                return true;
            }
            if (map.containsKey(id - 1) && Math.abs(nums[i] - map.get(id - 1)) < w) {
                return true;
            }
            if (map.containsKey(id + 1) && Math.abs(nums[i] - map.get(id + 1)) < w) {
                return true;
            }
            map.put(id, (long) nums[i]);
            if (i >= indexDiff) {
                map.remove(getID(nums[i - indexDiff], w));
            }
        }
        return false;
    }

    public long getID(long x, long w) {
        if (x >= 0) {
            return x / w;
        }
        // 负数的除法行为也变成向下取整（Floor division），从而保证桶的连续性和均匀性。
        // 例如 -1/10 = 0, 但是希望 -1 映射到 -1 桶的id 处, 所以最后 -1
        return (x + 1) / w - 1;
    }

    /**
     * @Description:
     * 1. 使用有序集合 TreeMap, 假设 i > j存储 [j, i - 1] 的所有元素 j = i - indexDiff
     * 2. 类似于滑动窗口 left, right 分别执行窗口的两端, 对于 i, 需要在 treeMap 中分别找到 min 和 max,
     * 其中 min = treeMap.floor, max = treeMap.ceiling, 然后分别比较 min 和 max 与 valueDiff
     * 3. 初始化窗口中, 也有可能包含有效数据, 所以应该从 0 开始处理, i - 1 - (i - indexDiff) + 1
     * AC: 267ms/119.25MB
     * @param: nums
     * @param: indexDiff
     * @param: valueDiff
     * @return boolean
     * @author marks
     * @CreateDate: 2026/08/11 11:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums, int indexDiff, int valueDiff) {
        int n = nums.length;
        int left = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            int curr = nums[i];
            // 先查找 map 中最大和最小
            Integer min = map.floorKey(curr);
            if (min != null) {
                if (curr - min <= valueDiff) {
                    return true;
                }
            }
            Integer max = map.ceilingKey(curr);
            if (max != null) {
                if (max - curr <= valueDiff) {
                    return true;
                }
            }
            // 添加当前元素, 并且删除 left
            if (i - left >= indexDiff) {
                map.merge(nums[left], -1, Integer::sum);
                if (map.get(nums[left]) == 0) {
                    map.remove(nums[left]);
                }
                left++;
            }
            map.merge(nums[i], 1, Integer::sum);
        }
        return false;
    }

}
