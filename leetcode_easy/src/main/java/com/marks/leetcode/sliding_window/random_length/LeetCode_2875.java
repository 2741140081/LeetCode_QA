package com.marks.leetcode.sliding_window.random_length;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/25 16:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2875 {
    public int minSizeSubarray(int[] nums, int target) {
        int result = 0;
        result = method_01(nums, target);
        return result;
    }

    /**
     * @Description: [
     * 输入：nums = [1,1,1,2,3], target = 4
     * 输出：2
     * 解释：在这个例子中 infinite_nums = [1,1,1,2,3,1,1,1,2,3,1,1,...].
     * 区间 [4,5] 内的子数组的元素和等于 target = 4 ，且长度 length = 2 。
     * 可以证明，当元素和等于目标值 target = 4 时，2 是子数组的最短长度。
     * AC:161ms/53.91MB
     * 修改 k值获取方式和 cnt的方式将循环变成整除
     * AC:8ms/53.86MB
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/10/25 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int target) {
        int n = nums.length;
        long total = Arrays.stream(nums).sum();
        long k = target % total;
        long ans = 0;
        ans += (target / total) * n;

        int left = 0; // 窗口的左边界
        int len = 2 * n + 1; // len为窗口的长度
        long sum = 0; // 窗口部分的sum值
        for (int right = 0; right < 2 * n; right++) {
            // 遍历找到[left, right] 使得结果等于k
            sum += nums[right % n];
            while (left < n && sum > k) {
                sum -= nums[left++];
            }

            if (sum == k) {
                len = Math.min(len, right - left + 1);
            }
        }
        // 判断是否符合要求, 即是否存在k, 使得pre[left] + pre[right] == k
        if (len == 2 * n + 1) {
            return -1;
        }else {
            ans += len;
        }
        return (int) ans;
    }
}
