package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Solution </p>
 * <p>描述:
 * LeetCode_398 - 随机数索引
 * 给你一个可能含有 重复元素 的整数数组 nums ，请你随机输出给定的目标数字 target 的索引。你可以假设给定的数字一定存在于数组中。
 * 实现 Solution 类：
 * Solution(int[] nums) 用数组 nums 初始化对象。
 * int pick(int target) 从 nums 中选出一个满足 nums[i] == target 的随机索引 i 。如果存在多个有效的索引，则每个索引的返回概率应当相等。
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/19 15:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Solution {
    private Map<Integer, List<Integer>> map;
    private Random random;

    /**
     * @Description:
     * 1. 需要一个数据结构存储值和索引, Map<Integer, List<Integer>> map;
     * 2. 需要随机返回目标值的索引, 获取索引的list = map.get(target); int n = list.size(); int idx = (int)(Math.random() * n);
     * AC: 79ms/70.68MB
     * @param: nums
     * @return
     * @author marks
     * @CreateDate: 2026/05/19 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Solution(int[] nums) {
        map = new HashMap<>();
        random = new Random();
        for (int i = 0; i < nums.length; i++) {
            // 使用lambda表达式
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }
    }

    public int pick(int target) {
        List<Integer> list = map.get(target);
        return list.get(random.nextInt(list.size()));
    }

}
