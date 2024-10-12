package com.marks.leetcode.sliding_window.fixed_length;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/12 9:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2841 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和两个正整数 m 和 k 。
     * 请你返回 nums 中长度为 k 的 几乎唯一 子数组的 最大和 ，如果不存在几乎唯一子数组，请你返回 0 。
     * 如果 nums 的一个子数组有至少 m 个互不相同的元素，我们称它是 几乎唯一 子数组。
     * 子数组指的是一个数组中一段连续 非空 的元素序列。
     *
     * tips:
     * 1 <= nums.length <= 2 * 10^4
     * 1 <= m <= k <= nums.length
     * 1 <= nums[i] <= 10^9
     * ]
     * @param nums
     * @param m
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/10/12 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxSum(List<Integer> nums, int m, int k) {
        long result = 0;
        result = method_01(nums, m, k);
        result = method_02(nums, m, k);
        return result;
    }
    /**
     * @Description: [
     * AC:28ms/48.54MB
     * ]
     * @param nums
     * @param m
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/10/12 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(List<Integer> nums, int m, int k) {
        int n = nums.size();
        HashMap<Integer, Integer> map = new HashMap<>();
        long ans = 0, sum = 0;
        for (int i = 0; i < k; i++) {
            int num = nums.get(i);
            sum += num;
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        ans = map.size() >= m ? sum : 0;
        for (int i = k; i < n; i++) {
            int currNum = nums.get(i);
            int prevNum = nums.get(i - k);
            sum = sum + currNum - prevNum;

            map.put(currNum, map.getOrDefault(currNum, 0) + 1);
            map.put(prevNum, map.get(prevNum) - 1);
            if (map.get(prevNum) == 0) {
                map.remove(prevNum);
            }
            if (map.size() >= m) {
                ans = Math.max(ans, sum);
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * Result: 760/767
     * 超时
     * list.stream().distinct().count() 花的时间还是太多了, 导致超时, 需要优化
     * ]
     * @param nums
     * @param m
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/10/12 11:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(List<Integer> nums, int m, int k) {
        int n = nums.size();
        ArrayList<Long> list = new ArrayList<>();
        long ans = 0;
        long sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums.get(i);
            list.add(Long.valueOf(nums.get(i)));
        }

        if (list.stream().distinct().count() >= m) {
            ans = sum;
        }
        for (int i = k; i < n; i++) {
            list.add(Long.valueOf(nums.get(i)));
            sum = sum + nums.get(i) - list.get(0);
            list.remove(0);
            if (list.stream().distinct().count() >= m) {
                ans = Math.max(ans, sum);
            }
        }
        return ans;
    }
}
