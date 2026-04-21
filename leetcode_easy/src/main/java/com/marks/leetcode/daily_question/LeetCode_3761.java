package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3761 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/17 9:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3761 {

    /**
     * @Description:
     * 给你一个整数数组 nums。
     * 镜像对 是指一对满足下述条件的下标 (i, j)：
     *
     * 0 <= i < j < nums.length，并且
     * reverse(nums[i]) == nums[j]，其中 reverse(x) 表示将整数 x 的数字反转后形成的整数。反转后会忽略前导零，例如 reverse(120) = 21。
     * 返回任意镜像对的下标之间的 最小绝对距离。下标 i 和 j 之间的绝对距离为 abs(i - j)。
     *
     * 如果不存在镜像对，返回 -1。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/04/17 9:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minMirrorPairDistance(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 将reverse(x) 后的数字存入map中, 并且更新下标值, 保证下标是最新(最大的下标)
     * 2. 当处理 nums[i] 时, 判断 map中是否包含 nums[i], 如果包含, 则获取value, 并且更新 ans 值
     * AC: 69ms/93.39MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/04/17 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        int ans = n + 1;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            // 判断 map 中是否包含 nums[i]
            if (map.containsKey(num)) {
                // 获取 index, 更新 ans 值
                int index = map.get(num);
                ans = Math.min(ans, i - index);
            }
            // 将num 反转后存入map中
            map.put(reverse(num), i);
        }

        return ans == n + 1 ? -1 : ans;
    }

    private int reverse(int num) {
        int y = 0;
        while (num > 0) {
            y = y * 10 + num % 10;
            num /= 10;
        }
        return y;
    }


    /**
     * @Description:
     * 1. 使用map 存储数字值和下标
     * 2. 反转数字值, 并判断是否在map中存在, 如果存在, 则计算最小距离, 最小距离计算可以使用二分查找
     * AC: 173ms/135.05MB
     * 3. 不是最优解
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/04/17 9:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            map.computeIfAbsent(num, k -> new ArrayList<>()).add(i);
        }
        int ans = Integer.MAX_VALUE;// 最小距离
        for (int i = 0; i < n - 1; i++) {
            // 反转 nums[i]
            int reverseNum = Integer.parseInt(new StringBuilder(String.valueOf(nums[i])).reverse().toString());
            // 判断 map 中是否存在 reverseNum
            if (map.containsKey(reverseNum)) {
                List<Integer> list = map.get(reverseNum);
                // 二分查找, 找到j, j > i && j 最小
                int j = binarySearch(list, i);
                // 判断是否满足条件
                if (j != -1 && j > i) {
                    ans = Math.min(ans, j - i);
                }
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private int binarySearch(List<Integer> list, int target) {
        int ans = -1;
        int left = 0;
        int right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) > target) {
                ans = list.get(mid);
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }
}
