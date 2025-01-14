package com.marks.leetcode.data_structure.prefix_sum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/14 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_525 {
    /**
     * @Description:
     * 给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/14 11:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMaxLength(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * pre[j] - pre[i] = k
     * k即为'1'的数量, 长度为i + 1 ~ j, j - (i + 1) + 1 = j - i 长度
     * 如果j - i = 2 * (pre[j] - pre[i]);
     * pre[i] = pre[j] - (j - i) / 2
     * 2 * pre[i] - i = 2 * pre[j] - j
     * AC:23ms/54.36MB
     * [0, 1, 0, 1]
     * [-1, 1, -1, 1]
     *
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/14 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int ans = 0;
        int pre = 0;
        for (int i = 0; i < nums.length; i++) {
            pre = pre + (nums[i] == 1 ? 1 : -1);
            if (map.containsKey(pre)) {
                ans = Math.max(ans, i - map.get(pre));
            } else {
                map.put(pre, i);
            }
        }
        return ans;
    }
}
