package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3020 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/3 17:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3020 {

    /**
     * @Description:
     * 给你一个 正整数 数组 nums 。
     *
     * 你需要从数组中选出一个满足下述条件的子集：
     *
     * 你可以将选中的元素放置在一个下标从 0 开始的数组中，并使其遵循以下模式：[x, x2, x4, ..., xk/2, xk, xk/2, ..., x4, x2, x]（注意，k 可以是任何 非负 的 2 的幂）。
     * 例如，[2, 4, 16, 4, 2] 和 [3, 9, 3] 都符合这一模式，而 [2, 4, 8, 4, 2] 则不符合。
     * 返回满足这些条件的子集中，元素数量的 最大值 。
     *
     * tips:
     * 2 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/06/03 17:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumLength(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 使用 Map<Long, Integer> 存储 nums 中每个数字出现的次数。由于 num * num, 最大时10^18 可能发生溢出，所以使用 long 类型存储。
     * 2. list 存储 map 的 key 值, 并且进行升序排序, Set visited, 判断数字是否被访问过, 如果访问过了, 则跳过.
     * 3. 需要特殊处理 nums[i] = 1 的情况
     * AC: 112ms/68.9MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/06/03 17:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Map<Long, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge((long) num, 1, Integer::sum);
        }
        // 将 map.key 转换为 list
        List<Long> list = new ArrayList<>(map.keySet());
        // 对list 升序排序
        list.sort(Long::compareTo);
        // 处理特殊情况 1
        int ans = 1;
        if (list.get(0).equals(1L)) {
            int count = map.get(list.get(0));
            // count 必须是奇数
            if (count % 2 == 0) {
                count--;
            }
            ans = Math.max(ans, count);
            list.remove(0);
        }
        Set<Long> visited = new HashSet<>();

        for (Long base : list) {
            if (visited.contains(base)) {
                continue;
            }
            int max = 0;
            while (map.containsKey(base)) {
                visited.add(base);
                max++;
                if (map.get(base) == 1) {
                    break;
                }
                base *= base;

            }
            ans = Math.max(max * 2 - 1, ans);
        }

        return ans;
    }

}
