package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/26 16:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3171 {

    /**
     * @Description:
     * 给你一个数组 nums 和一个整数 k 。
     * 你需要找到 nums 的一个 子数组 ，满足子数组中所有元素按位或运算 OR 的值与 k 的 绝对差 尽可能 小 。
     * 换言之，你需要选择一个子数组 nums[l..r] 满足 |k - (nums[l] OR nums[l + 1] ... OR nums[r])| 最小。
     *
     * 请你返回 最小 的绝对差值。
     *
     * 子数组 是数组中连续的 非空 元素序列。
     * @param nums 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/9/26 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumDifference(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    
    /**
     * @Description:
     *
     * 1. 不能用前缀和或者后缀和来解决, 考虑能否用滑动窗口来解决?
     * 2. 用一个int[] bits = int[32] 来记录当前每一位的1的个数, 例如 nums[i] = 5, 1001 那么bit = {1,0,0,1}
     * AC: 99ms/57.36MB
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/26 16:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int[] bits = new int[31];
        // 滑动窗口判断 nums[l,r] - k 的绝对差
        int left = 0;
        int ans = Integer.MAX_VALUE;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int temp = nums[i];
            int index = 0;
            while (temp != 0) {
                if ((temp & 1) == 1) {
                    bits[index]++;
                    sum = (sum | 1 << index);
                }
                temp >>= 1;
                index++;
            }
            if (sum > 0) {
                // sum > 0 可以保证子数组不为空
                ans = Math.min(ans, Math.abs(sum - k));
            }
            while (sum > k) {
                int currValue = nums[left];
                int currIndex = 0;
                while (currValue != 0) {
                    if ((currValue & 1) == 1) {
                        bits[currIndex]--;
                        if (bits[currIndex] == 0) {
                            sum = (sum ^ (1 << currIndex));
                        }
                    }

                    currValue >>= 1;
                    currIndex++;
                }
                left++;
                if (sum > 0) {
                    ans = Math.min(ans, Math.abs(sum - k));
                }

            }
            if (sum > 0) {
                ans = Math.min(ans, Math.abs(sum - k));
            }
        }
        return ans;
    }

}
