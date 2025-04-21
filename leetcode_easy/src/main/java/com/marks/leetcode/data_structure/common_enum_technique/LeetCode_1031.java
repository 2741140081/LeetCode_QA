package com.marks.leetcode.data_structure.common_enum_technique;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/10 11:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1031 {
    /**
     * @Description:
     * 给你一个整数数组 nums 和两个整数 firstLen 和 secondLen，请你找出并返回两个非重叠 子数组 中元素的最大和，长度分别为 firstLen 和 secondLen 。
     *
     * 长度为 firstLen 的子数组可以出现在长为 secondLen 的子数组之前或之后，但二者必须是不重叠的。
     *
     * 子数组是数组的一个 连续 部分。
     *
     * @param nums 
     * @param firstLen 
     * @param secondLen 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/10 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        int result;
        result = method_01(nums, firstLen, secondLen);
        return result;
    }

    /**
     * @Description:
     * first 和 second存在两种情况
     * 1. first 和 second 没有交集, return
     * 2. first 和 second 存在交集
     * firstLen = j - i + 1
     * j = firstLen + i - 1
     * i = j - firstLen + 1
     * len = n - 1 - (n - firstLen + 1) + 1
     *
     * AC: 1ms/41.23MB
     * @param nums
     * @param firstLen
     * @param secondLen
     * @return int
     * @author marks
     * @CreateDate: 2025/1/10 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int firstLen, int secondLen) {
        return Math.max(help(nums, firstLen, secondLen), help(nums, secondLen, firstLen));
    }

    private int help(int[] nums, int secondLen, int firstLen) {
        int suml = 0;
        for (int i = 0; i < firstLen; ++i) {
            suml += nums[i];
        }
        int maxSumL = suml;
        int sumr = 0;
        for (int i = firstLen; i < firstLen + secondLen; ++i) {
            sumr += nums[i];
        }
        int res = maxSumL + sumr;
        for (int i = firstLen + secondLen, j = firstLen; i < nums.length; ++i, ++j) {
            suml += nums[j] - nums[j - firstLen];
            maxSumL = Math.max(maxSumL, suml);
            sumr += nums[i] - nums[i - secondLen];
            res = Math.max(res, maxSumL + sumr);
        }
        return res;
    }
}
