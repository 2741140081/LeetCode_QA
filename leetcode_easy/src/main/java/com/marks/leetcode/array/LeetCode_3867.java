package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3867 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/16 11:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3867 {



    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums。
     * 构造一个数组 prefixGcd，其中对于每个下标 i：
     * 令 mxi = max(nums[0], nums[1], ..., nums[i])。
     * prefixGcd[i] = gcd(nums[i], mxi)。
     * 在构造 prefixGcd 之后：
     * 将 prefixGcd 按 非递减 顺序排序。
     * 通过取 最小的未配对 元素和 最大的未配对 元素来形成数对。
     * 重复此过程，直到无法再形成更多数对。
     * 对于每个形成的数对，计算 两个元素的最大公约数 gcd。
     * 如果 n 是奇数，prefixGcd 数组中的 中间 元素保持 未配对 状态，并应被忽略。
     * 返回一个整数，表示所有形成数对的 最大公约数之和。
     *
     * 术语 gcd(a, b) 表示 a 和 b 的 最大公约数。
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/07/16 11:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long gcdSum(int[] nums) {
        long result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 构建 gcd 方法, 计算 两个数的最大公约数
     * 2. 构建 prefixGcd 数组
     * AC: 75ms/105.89MB
     * 就是一个普通的模拟题, 按照要求模拟即可
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/07/16 11:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {
        int n = nums.length;
        int[] prefixGcd = new int[n];
        int maxNum = 0;
        for (int i = 0; i < n; i++) {
            maxNum = Math.max(maxNum, nums[i]);
            prefixGcd[i] = gcd(nums[i], maxNum);
        }
        // 升序排序
        Arrays.sort(prefixGcd);
        long ans = 0;
        // 构建数对
        int left = 0, right = n - 1;
        while (left < right) {
            ans += gcd(prefixGcd[left], prefixGcd[right]);
            left++;
            right--;
        }

        return ans;
    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

}
