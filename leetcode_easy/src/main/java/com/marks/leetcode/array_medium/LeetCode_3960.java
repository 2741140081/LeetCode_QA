package com.marks.leetcode.array_medium;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3960 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/15 11:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3960 {

    /**
     * @Description:
     * 给你一个整数数组 nums。
     * 定义 频率平衡 子数组 如下：
     * 如果子数组只包含 一种 元素，则它是频率平衡的。
     * 否则，必然存在一个正整数 f，使得子数组中的每个不同值出现的次数要么是 f，要么是 2 * f，并且这两种 频率 都在不同值中出现。
     * 返回一个整数，表示 最长 频率平衡子数组的长度。
     *
     * tips:
     * 1 <= nums.length <= 10^3
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/09/15 11:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getLength(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 使用 map 统计每个元素出现的次数, 并且记录次数出现的频率
     * AC: 671ms/46.29MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/09/15 11:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> countMap;
        int ans = 1;
        for (int i = 0; i < n - ans; i++) {
            countMap = new HashMap<>();
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = i; j < n; j++) {
                if (countMap.containsKey(nums[j])) {
                    Integer cnt = countMap.get(nums[j]);
                    map.merge(cnt, -1, Integer::sum); // 除以 cnt key, 添加 cnt + 1 key
                    if (map.get(cnt) == 0) {
                        map.remove(cnt);
                    }
                    map.merge(cnt + 1, 1, Integer::sum);
                    countMap.merge(nums[j], 1, Integer::sum);
                } else {
                    countMap.put(nums[j], 1);
                    map.merge(1, 1, Integer::sum);
                }
                if (map.size() == 1 && countMap.size() == 1) {
                    ans = Math.max(ans, j - i + 1);
                } else if (map.size() == 2 && countMap.size() > 1) {
                    int[] keys = new int[2];
                    int idx = 0;
                    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        keys[idx++] = entry.getKey();
                    }
                    if (keys[0] == keys[1] * 2 || keys[1] == keys[0] * 2) {
                        ans = Math.max(ans, j - i + 1);
                    }
                }
            }

        }

        return ans;
    }

}
