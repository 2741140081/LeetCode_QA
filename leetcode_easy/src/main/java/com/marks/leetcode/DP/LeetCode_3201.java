package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/15 10:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3201 {

    /**
     * @Description:
     * 给你一个整数数组 nums。
     *
     * nums 的子序列 sub 的长度为 x ，如果其满足以下条件，则称其为 有效子序列：
     *
     * (sub[0] + sub[1]) % 2 == (sub[1] + sub[2]) % 2 == ... == (sub[x - 2] + sub[x - 1]) % 2
     * 返回 nums 的 最长的有效子序列 的长度。
     *
     * 一个 子序列 指的是从原数组中删除一些元素（也可以不删除任何元素），剩余元素保持原来顺序组成的新数组。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/10/15 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumLength(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    
    /**
     * @Description:
     * 1. 奇数 + 奇数 = 奇数 + 奇数
     * 2. 偶数 + 偶数 = 偶数 + 偶数
     * 3. 奇数 + 偶数 = 偶数 + 奇数
     *
     * AC: 7ms/60.98MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/15 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            nums[i] %= 2;
        }
        int ans = 0;
        // 奇数 + 奇数 和 偶数 + 偶数
        int add = 0;
        int even = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                add++;
            } else {
                even++;
            }
        }
        ans = Math.max(add, even);

        // 奇数 + 偶数
        int prev = nums[0];
        int count = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] != prev) {
                prev = nums[i];
                count++;
            }
        }
        ans = Math.max(ans, count);
        return ans;
    }

}
