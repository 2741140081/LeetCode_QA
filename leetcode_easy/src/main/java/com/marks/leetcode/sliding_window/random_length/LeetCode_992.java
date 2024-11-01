package com.marks.leetcode.sliding_window.random_length;

import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 15:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_992 {
    /**
     * @Description: [
     * 给定一个正整数数组 nums和一个整数 k，返回 nums 中 「好子数组」 的数目。
     *
     * 如果 nums 的某个子数组中不同整数的个数恰好为 k，则称 nums 的这个连续、不一定不同的子数组为 「好子数组 」。
     *
     * 例如，[1,2,3,1,2] 中有 3 个不同的整数：1，2，以及 3。
     * 子数组 是数组的 连续 部分。
     *
     * tips:
     * 1 <= nums.length <= 2 * 10^4
     * 1 <= nums[i], k <= nums.length
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int subarraysWithKDistinct(int[] nums, int k) {
        int result = 0;
        result = method_01(nums, k);
        result = method_02(nums, k);
        result = method_03(nums, k);
        return result;
    }

    /**
     * @Description: [官方题解:
     * AC:3ms/45.46MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 16:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] nums, int k) {
        return findMaxSub(nums, k) - findMaxSub(nums, k - 1);
    }

    private int findMaxSub(int[] nums, int k) {
        int res = 0;
        int[] maps = new int[nums.length + 1];
        int left = 0;
        for (int i = 0 ; i < nums.length; i++) {
            if (++maps[nums[i]] == 1) {
                k--;
            }
            while (k < 0) {
                if (--maps[nums[left]] == 0) {
                    k++;
                }
                left++;
            }
            res += i - left + 1;
        }
        return  res;
    }

    /**
     * @Description: [
     * AC:57ms/45.32MB
     * 感觉这个的时间复杂度
     * map_1.merge(nums[right], 1, Integer::sum);
     * map_1.put(num, map_1.getOrDefault(num, 0) + 1);
     * map.put用时会更少
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 16:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int n = nums.length;
        int left_1 = 0; // >= k
        int left_2 = 0; // > k + 1
        HashMap<Integer, Integer> map_1 = new HashMap<>();
        HashMap<Integer, Integer> map_2 = new HashMap<>();
        int ans = 0;

        for (int right = 0; right < n; right++) {
            map_1.merge(nums[right], 1, Integer::sum);
            map_2.merge(nums[right], 1, Integer::sum);

            while (map_1.size() >= k && left_1 <= right ) {
                if (map_1.get(nums[left_1]) == 1) {
                    map_1.remove(nums[left_1]);
                }else {
                    map_1.merge(nums[left_1], -1, Integer::sum);
                }
                left_1++;
            }

            while (map_2.size() > k && left_2 <= right ) {
                if (map_2.get(nums[left_2]) == 1) {
                    map_2.remove(nums[left_2]);
                }else {
                    map_2.merge(nums[left_2], -1, Integer::sum);
                }
                left_2++;
            }

            ans += left_1 - left_2;
        }
        return ans;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [1,2,1,2,3], k = 2
     * 输出：7
     * 解释：恰好由 2 个不同整数组成的子数组：[1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
     *
     * AC:41ms/46.29MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int left_1 = 0; // >= k
        int left_2 = 0; // > k + 1
        HashMap<Integer, Integer> map_1 = new HashMap<>();
        HashMap<Integer, Integer> map_2 = new HashMap<>();
        int ans = 0;

        for (int right = 0; right < n; right++) {
            int num = nums[right];
            map_1.put(num, map_1.getOrDefault(num, 0) + 1);
            map_2.put(num, map_2.getOrDefault(num, 0) + 1);
            while (map_1.size() >= k && left_1 <= right ) {
                if (map_1.get(nums[left_1]) == 1) {
                    map_1.remove(nums[left_1]);
                }else {
                    map_1.put(nums[left_1], map_1.get(nums[left_1]) - 1);
                }
                left_1++;
            }

            while (map_2.size() > k && left_2 <= right ) {
                if (map_2.get(nums[left_2]) == 1) {
                    map_2.remove(nums[left_2]);
                }else {
                    map_2.put(nums[left_2], map_2.get(nums[left_2]) - 1);
                }
                left_2++;
            }

            ans += left_1 - left_2;

        }

        return ans;
    }
}
