package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2195 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/2 9:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2195 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k 。
     * 请你向 nums 中追加 k 个 未 出现在 nums 中的、互不相同 的 正 整数，并使结果数组的元素和 最小 。
     * 返回追加到 nums 中的 k 个整数之和。
     *
     * tips:
     *  <= nums.length <= 10^5
     * 1 <= nums[i], k <= 10^9
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/07/02 9:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minimalKSum(int[] nums, int k) {
        long result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. long prev = 0, long curr = nums[i]; 中间空余的数字 long cnt = curr - prev - 1; k -= cnt; sum += cnt * (curr + prev) / 2,
     * 将 k 转换成 long 类型 long kLong = (long) k;
     * 2. 需要判断 cnt 与 kLong 之间的大小关系: cnt >= kLong, cnt < kLong 两种关系, 分别进行分析
     * 2.1 cnt >= kLong: sum += kLong * (prev + 1 + prev + kLong) / 2; kLong = 0
     * 2.2 cnt < kLong: sum += cnt * (prev + curr) / 2; kLong -= cnt
     * AC: 23ms/71.72MB
     * 核心思想：
     * 先对数组排序，然后从小到大遍历
     * 对于相邻两个数 prev 和 curr，它们之间有 cnt = curr - prev - 1 个空缺数字
     * 优先填充较小的空缺数字，使得总和最小
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/07/02 9:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        Arrays.sort(nums);

        long sum = 0;
        long kLong = k;
        long prev = 0;

        for (long curr : nums) {
            if (curr <= prev) {
                continue;
            }

            long cnt = curr - prev - 1;

            if (cnt > 0) {
                if (cnt >= kLong) {
                    sum += kLong * (prev + 1 + prev + kLong) / 2;
                    kLong = 0;
                    break;
                } else {
                    sum += cnt * (prev + 1 + curr - 1) / 2;
                    kLong -= cnt;
                }
            }

            prev = curr;
        }

        if (kLong > 0) {
            sum += kLong * (prev + 1 + prev + kLong) / 2;
        }

        return sum;
    }

}
