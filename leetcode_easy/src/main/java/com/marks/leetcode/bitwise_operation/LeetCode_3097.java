package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/9 14:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3097 {


    /**
     * @Description:
     * 给你一个 非负 整数数组 nums 和一个整数 k 。
     *
     * 如果一个数组中所有元素的按位或运算 OR 的值 至少 为 k ，那么我们称这个数组是 特别的 。
     *
     * 请你返回 nums 中 最短特别非空 子数组的长度，如果特别子数组不存在，那么返回 -1 。
     *
     * tips:
     * 1 <= nums.length <= 2 * 10^5
     * 0 <= nums[i] <= 10^9
     * 0 <= k <= 10^9
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/10/9 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumSubarrayLength(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    private int method_02(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (x >= k) {
                return 1;
            }
            for (int j = i - 1; j >= 0 && (nums[j] | x) != nums[j]; j--) {
                nums[j] |= x; // 由于每次都会修改nums[j], 所以 nums[j] 相当于是 [j,i] 这个子数组的或运算的结果
                if (nums[j] >= k) {
                    ans = Math.min(ans, i - j + 1);
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }


    /**
     * @Description:
     *
     * 1. 与之前的的解决办法相同, 滑动窗口, int[] count 记录 "1" 位次的个数
     * 2. int res; 存储数组或运行结果, 如果 res >= k, ans = Math.min(ans, i - left + 1);
     * 3. 当res >= k 时, 移动left, 使得res < k, 同时在循环中不断判断更新ans的结果,
     * 另外一个点, 当减去count[i] == 0 时, res ^= (1 << i); 即将第 i 位的值的 1变为0。
     * AC: 48ms/68.49MB
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/10/9 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        // 判断特殊情况 -1
        int orRes = 0;
        for (int num : nums) {
            orRes |= num;
        }
        if (orRes < k) {
            return -1;
        }
        int left = 0;
        int ans = nums.length; // 因为当前数组是特别数组，所以 ans 初始化为数组长度
        int[] count = new int[31];
        int res = 0;
        for (int right = 0; right < nums.length; right++) {
            for (int i = 0; i < 31; i++) {
                if ((nums[right] & (1 << i)) != 0) {
                    count[i]++;
                    res |= (1 << i);
                }
            }
            while (res >= k && left <= right) {
                ans = Math.min(ans, right - left + 1);
                for (int i = 0; i < 31; i++) {
                    if ((nums[left] & (1 << i)) != 0) {
                        count[i]--;
                        if (count[i] == 0) {
                            res ^= (1 << i);
                        }
                    }
                }
                left++;
            }
        }
        return ans;
    }

}
