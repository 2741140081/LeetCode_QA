package com.marks.leetcode.array_hard;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2790 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/4 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2790 {

    /**
     * @Description:
     * 给你一个下标从 0 开始、长度为 n 的数组 usageLimits 。
     * 你的任务是使用从 0 到 n - 1 的数字创建若干组，
     * 并确保每个数字 i 在 所有组 中使用的次数总共不超过 usageLimits[i] 次。此外，还必须满足以下条件：
     * 每个组必须由 不同 的数字组成，也就是说，单个组内不能存在重复的数字。
     * 每个组（除了第一个）的长度必须 严格大于 前一个组。
     * 在满足所有条件的情况下，以整数形式返回可以创建的最大组数。
     *
     * tips:
     * 1 <= usageLimits.length <= 10^5
     * 1 <= usageLimits[i] <= 10^9
     * @param: usageLimits
     * @return int
     * @author marks
     * @CreateDate: 2026/09/04 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxIncreasingGroups(List<Integer> usageLimits) {
        int result;
        result = method_01(usageLimits);
        result = method_02(usageLimits);
        return result;
    }

    private int method_02(List<Integer> usageLimits) {
        final int n = usageLimits.size();
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = usageLimits.get(i);
        }

        Arrays.sort(nums);

        int length = 1, right = 0;
        long preSum = 0L;

        while (right < n) {
            preSum += nums[right] - length;
            if (preSum < 0) preSum += length;
            else length++;
            right++;
        }

        return length - 1;
    }

    /**
     * @Description:
     * 1. 通过二分法确定最大组数, left = 1, right = Math.min(Math.max(usageLimits.get(i)), n);
     * 2. 对 usageLimits 进行排序
     * 3. 假设需要构成 mid 个组, 最小的组包含1个元素, 最大的组包含 mid 个元素, 取末尾的 mid 个元素作为基底,
     * 范围是 [n - mid, n - 1], 并且当 基底元素不足时 即 usageLimits.get(i) < j, j 是第 j 列所需的元素,
     * 类似于 第1列需要1个, 第 j 列需要 j 个元素, 需要从 [0 ~ n - mid - 1] 范围取出一个合适值来进行填补,
     * int sub = j - usageLimits.get(i); 这个查找需要使用有序集合存储 TreeMap 存储 [0 ~ n - mid - 1]
     * 的元素种类和个数
     * 4. 整体复杂度分析: 二分法: O(logn), check 过程中遍历是 O(mid), 将 [0 ~ n - mid - 1] 范围的元素放入 TreeMap 中是 O(nlogn),
     * 所以总的时间复杂度是 n * logn * logn, 可以接受
     * 5. check 方法思路存在问题, 无法得到最佳组合数, 舍弃
     * @param: usageLimits
     * @return int
     * @author marks
     * @CreateDate: 2026/09/04 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<Integer> usageLimits) {
        int n = usageLimits.size();
        // 对 usageLimits 进行排序
        usageLimits.sort(Integer::compareTo);
        int left = 1, right = Math.min(usageLimits.get(n - 1), n);
        int ans = 0;
        // 执行二分法查找
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(usageLimits, mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return 0;
    }

    private boolean check(List<Integer> usageLimits, int target) {
        // 构建 TreeMap, 存储 [0 ~ n - target - 1] 的数
        int n = usageLimits.size();
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int i = 0; i < n - target; i++) {
            treeMap.merge(usageLimits.get(i), 1, Integer::sum);
        }
        int need = 1;
        for (int i = n - target; i < n; i++) {
            int sub = need - usageLimits.get(i);
            while (sub > 0) {
                // 取出 treeMap 中第一个大于等于 sub 的数, 如果不存在
                // error: 取数的结果不是最佳组合
            }
            need++;
        }
        return false;
    }

}
