package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 10:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_976 {
    /**
     * @Description:
     * 给定由一些正数（代表长度）组成的数组 nums ，返回 由其中三个长度组成的、面积不为零的三角形的最大周长 。
     * 如果不能形成任何面积不为零的三角形，返回 0。
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 10:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int largestPerimeter(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * x1, x2, x3
     * x1 <= x2 < x3
     * x1 + x2 > x3
     * x1 + x2 + x3 is max,
     * nums = [1,2,1,10]
     * 1, 1, 2, 10
     *
     * AC: 8ms/43.98MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 10:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        for (int i = nums.length - 1; i >= 2; i--) {
            if (nums[i] < nums[i - 1] + nums[i - 2]) {
                ans = nums[i] + nums[i - 1] + nums[i - 2];
                break;
            }
        }
        return ans;
    }
}
