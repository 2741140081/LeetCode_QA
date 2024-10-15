package com.marks.leetcode.sliding_window.random_length;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/15 16:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1838 {
    /**
     * @Description: [
     * 元素的 频数 是该元素在一个数组中出现的次数。
     * 给你一个整数数组 nums 和一个整数 k 。在一步操作中，你可以选择 nums 的一个下标，并将该下标对应元素的值增加 1 。
     * 执行最多 k 次操作后，返回数组中最高频元素的 最大可能频数 。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * 1 <= k <= 10^5
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/15 16:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxFrequency(int[] nums, int k) {
        int result = 0;
        result = method_01(nums, k);
        return result;
    }
    /**
     * @Description: [
     * 排序 + 滑动窗口
     * 输入：nums = [1,2,4], k = 5
     * 输出：3
     * 解释：对第一个元素执行 3 次递增操作，对第二个元素执 2 次递增操作，此时 nums = [4,4,4] 。
     * 4 是数组中最高频元素，频数是 3 。
     * 2 + 2 - (1 + 2) = 1
     *
     * AC:31ms/56.61MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/15 16:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        // 先排序
        Arrays.sort(nums);
        // 左边界, 窗口值之和, 频数(最大长度)
        long target = k, sum = 0;
        int left = 0, ans = 0;

        for (int i = 0; i < n; i++) {
            sum += nums[i];
            // 当你将一个int类型的值与一个long类型的值相乘时，结果自动提升为long类型
            long temp = (long) nums[i] * (i - left + 1); // 将窗口的值全部提高到num[i]之后的和
            /*
            之前用int出错, 无法过最后一个用例
            用例:nums = {1,.....,1,1,100000}, nums.length = 30000,
            原因1:
            temp = 100000 * 30000 = 3,000,000,000; 30亿超过int 的范围21亿
            int num = 100000 * 30000; // num = -1294967296
            导致temp - sum为负数 小于k, 出错

            原因2: 同样需要修改将int k 转换为 long target
            在Java中，long类型的值和int类型的值进行比较时，long类型的值会自动转换为int类型的值（这个过程称为类型的降级装箱），
            然后进行比较。这意味着如果long值在int范围内（-2^31 到 2^31-1），那么可以直接将其当作int类型来比较。
            但是,如果long值超出了int的范围（即小于Integer.MIN_VALUE或大于Integer.MAX_VALUE），那么在比较时就会发生溢出，可能导致不准确的结果。

            所以需要将int k 转换为 long target, 防止发生降级装箱, 防止long值转为int时发生溢出
             */
            while (temp - sum > target) {
                sum -= nums[left++];
                temp -= nums[i];
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }
}
