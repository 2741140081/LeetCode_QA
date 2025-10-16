package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/15 14:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_396 {

    /**
     * @Description:
     * 给定一个长度为 n 的整数数组 nums 。
     *
     * 假设 arrk 是数组 nums 顺时针旋转 k 个位置后的数组，我们定义 nums 的 旋转函数  F 为：
     *
     * F(k) = 0 * arrk[0] + 1 * arrk[1] + ... + (n - 1) * arrk[n - 1]
     * 返回 F(0), F(1), ..., F(n-1)中的最大值 。
     *
     * 生成的测试用例让答案符合 32 位 整数。
     * tips:
     * n == nums.length
     * 1 <= n <= 10^5
     * -100 <= nums[i] <= 100
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/10/15 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxRotateFunction(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入: nums = [4,3,2,6]
     * 输出: 26
     * 解释:
     * F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
     * F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
     * F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
     * F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
     * 所以 F(0), F(1), F(2), F(3) 中的最大值是 F(3) = 26 。
     * 4, 3, 2, 6, 4, 3, 2, 6;
     * 0, 1, 2, 3 =
     *    0, 1, 2, 3=
     *
     * 1. 可以用滑动窗口来解决, 固定长度的滑动窗口, 每次向后移动一个位置, 窗口长度为nums.length
     * 2. 窗口初始元素和为nums[0] + nums[1] + nums[2] + nums[n - 1] = sum0, 并且使用前缀和存储2n个元素和
     * 3. int[] preSum = new int[2 * nums.length + 1];
     * 4. 每次向后移动, sum = sum - (pre[j] - pre[j - n + 1])(整个窗口的从1到n - 1 共计 n - 1 个数) + nums[j] * (n - 1)
     * AC: 12ms/59.23MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/15 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;

        int[] arr = new int[2 * n]; // 将数组扩展两倍
        int[] preSum = new int[2 * n + 1]; // 存储前缀和
        for (int i = 0; i < 2 * n; i++) {
            arr[i] = nums[i % n];
        }
        for (int i = 1; i < arr.length; i++) {
            preSum[i] = preSum[i - 1] + arr[i - 1];
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            // 窗口初始元素和
            sum += i * nums[i];
        }
        int ans = sum;
        int left = 1;
        for (int right = n; right < 2 * n; right++) {
            int sub = preSum[right] - preSum[left];
            sum = sum - sub + (n - 1) * arr[right];
            ans = Math.max(ans, sum);
            left++;
        }
        return ans;
    }

}
