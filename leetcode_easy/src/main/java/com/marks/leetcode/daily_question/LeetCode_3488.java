package com.marks.leetcode.daily_question;

import java.util.*;

import static java.util.Collections.binarySearch;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3488 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/16 14:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3488 {


    /**
     * @Description:
     * 给你一个 环形 数组 nums 和一个数组 queries 。
     * 对于每个查询 i ，你需要找到以下内容：
     * 数组 nums 中下标 queries[i] 处的元素与 任意 其他下标 j（满足 nums[j] == nums[queries[i]]）之间的 最小 距离。
     * 如果不存在这样的下标 j，则该查询的结果为 -1 。
     * 返回一个数组 answer，其大小与 queries 相同，其中 answer[i] 表示查询i的结果。
     *
     * tips:
     * 1 <= queries.length <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * 0 <= queries[i] < nums.length
     * @param: nums
     * @param: queries
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/04/16 14:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> solveQueries(int[] nums, int[] queries) {
        List<Integer> result;
        result = method_01(nums, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 通过map 存储数组元素和下标, 方便查询
     * 2. 数组是一个环形数组, List<Integer> list 存储 nums 中元素出现的下标, 对于 查询 i，可以通过 nums[queries[i]] 获取 nums 中元素出现的下标 j,
     * 然后找到 j 下一个坐标, 只能向前找, 不能向后找, 但是可以循环遍历
     * 3. 如果 list.size() == 1, result[i] = -1;
     * 4. 解答错误, 还是需要找前一个和后一个对比才行
     * 超时: 612/614
     * 感觉是 indexof 方法耗时, 使用二分查找进行优化处理
     * AC: 149ms/176.78MB
     * @param: nums
     * @param: queries
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/04/16 14:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] nums, int[] queries) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            List<Integer> list = map.getOrDefault(num, new ArrayList<>());
            list.add(i);
            map.put(num, list);
        }
        List<Integer> ans = new ArrayList<>();
        for (int query : queries) {
            List<Integer> list = map.get(nums[query]);
            // 使用二分查找, 找到 queries[i] 的下一个坐标
            if (list.size() == 1) {
                ans.add(-1);
            } else {
                int index = binarySearchIndex(list, query);
                int prev, next;
                if (index == list.size() - 1) {
                    // 最后一个, 只能取第一个
                    next = n + list.get(0);
                } else {
                    // 找到下一个坐标
                    next = list.get(index + 1);
                }

                if (index == 0) {
                    prev = list.get(list.size() - 1) - n;
                } else {
                    // 找到前一个坐标
                    prev = list.get(index - 1);
                }
                ans.add(Math.min(next - query, query - prev));
            }
        }

        return ans;
    }

    private int binarySearchIndex(List<Integer> list, int query) {
        int left = 0;
        int right = list.size() - 1;
        int ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) > query) {
                right = mid - 1;
            } else {
                ans = mid;
                left = mid + 1;
            }
        }
        return ans;
    }

}
