package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2967 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/26 10:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2967 {

    /**
     * @Description:
     * 给你一个长度为 n 下标从 0 开始的整数数组 nums 。
     * 你可以对 nums 执行特殊操作 任意次 （也可以 0 次）。每一次特殊操作中，你需要 按顺序 执行以下步骤：
     * 从范围 [0, n - 1] 里选择一个下标 i 和一个 正 整数 x 。
     * 将 |nums[i] - x| 添加到总代价里。
     * 将 nums[i] 变为 x 。
     * 如果一个正整数正着读和反着读都相同，那么我们称这个数是 回文数 。比方说，121 ，2552 和 65756 都是回文数，但是 24 ，46 ，235 都不是回文数。
     * 如果一个数组中的所有元素都等于一个整数 y ，且 y 是一个小于 10^9 的 回文数 ，那么我们称这个数组是一个 等数数组 。
     * 请你返回一个整数，表示执行任意次特殊操作后使 nums 成为 等数数组 的 最小 总代价。
     * tips:
     * 1 <= n <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/06/26 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minimumCost(int[] nums) {
        long result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 选择一个最近的回文数 X
     * 2. 回文数的范围应该在[min ~ max] 区间内, 这样可以使用二分法进行查找
     * 3. 计算总代价, 先对数组 nums 进行升序排序, 另外需要找到规律, 即 x 越大代价越小还是 X 越小代价越小?
     * 4. 假设有 x1, x2 两个数, x1 < x2, 先列出一个计算总代价的公式出来, 有 n 个数, 前 i 个数小于 x1, 有n - i 大于等于 x1, 总代价为
     * i * x1 - preSum[i] + (preSum[n] - preSum[i] - (n - i) * x1) = (2*i - n) * x1 + preSum[n] - 2 * preSum[i]
     * 5. 是有公式, 但是无法确定是否存在关系, 但是有不能使用枚举的方式.
     * 6. 查看了相关的提示, 需要找到nums 的中位数 m, 然后根据中位数构建一个小于 m 的最大回文数 和 一个大于等于 m的最小回文数, 然后分别计算总代价, 取最小的代价返回.
     * 7. 将 m 转成一个 String, 使用双指针, 将除了中间位置外的元素一致 假设 12345678 => 12345321 中间位置如果是增加 将4 变成 5, 如果是减少将 5 变成 4
     * 8. 得到 int left = 12344321 < m, int right = 12355321 > m, 枚举 [left, m), 和 [m, right] 之间是否还存在更优秀(更大或更小)的回文数
     * need todo:
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/06/26 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {
        // 先排序
        Arrays.sort(nums);
        int n = nums.length;
        // 找到中位数
        int m;
        if (n % 2 == 0) {
            m = (nums[n / 2] + nums[n / 2 - 1]) / 2;
        } else {
            m = nums[n / 2];
        }
        // 根据中位数构建一个小于 m 的最大回文数 和 一个大于等于 m的最小回文数
        String mStr = String.valueOf(m);
        int len = mStr.length();
        int l, r;
        char[] max = new char[len];
        char[] min = new char[len];
        if (len % 2 == 0) {
            // 偶数
            l = len / 2 - 1;
            r = len / 2;
        } else {
            l = r = len / 2;
        }


        return 0;
    }

}
