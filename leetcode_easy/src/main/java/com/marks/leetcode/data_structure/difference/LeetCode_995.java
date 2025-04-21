package com.marks.leetcode.data_structure.difference;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/19 10:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_995 {
    /**
     * @Description:
     * 给定一个二进制数组 nums 和一个整数 k 。
     * k位翻转 就是从 nums 中选择一个长度为 k 的 子数组 ，同时把子数组中的每一个 0 都改成 1 ，把子数组中的每一个 1 都改成 0 。
     * 返回数组中不存在 0 所需的最小 k位翻转 次数。如果不可能，则返回 -1 。
     * 子数组 是数组的 连续 部分。
     * @param nums 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/2/19 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minKBitFlips(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description:
     * 滑动窗口
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/2/19 10:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        return 0;
    }


    /**
     * @Description:
     * 将 ‘0’ 聚集在一起的最小代价 + 1, 即为最小的反转次数
     * [0,0,0,1,0,1,1,0] k = 3
     *
     * 0. [0,1,1,1,1,1,1,0]
     * 1. [1,0,0,1,1,1,1,0]
     * 2. [1,1,1,0,1,1,1,0]
     *
     * 1.1[1,0,0,1,1,1,1,0]
     * 1.2[1,0,1,0,0,1,1,0]
     * 1.3[1,1,0,1,0,1,1,0]
     * 1.4[1,1,1,0,1,1,1,0]
     * 差分
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/2/19 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int[] diff = new int[n + 1];
        int ans = 0, count = 0;
        for (int i = 0; i < n; i++) {
            count += diff[i];
            if ((nums[i] + count) % 2 == 0) {
                if (i + k > n) {
                    return -1;
                }
                ans++;
                count++;
                diff[i + k]--;
            }
        }
        return ans;
    }
}
