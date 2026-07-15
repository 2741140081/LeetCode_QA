package com.marks.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2420 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/14 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2420 {

    /**
     * @Description:
     * 给你一个大小为 n 下标从 0 开始的整数数组 nums 和一个正整数 k 。
     * 对于 k <= i < n - k 之间的一个下标 i ，如果它满足以下条件，我们就称它为一个 好 下标：
     * 下标 i 之前 的 k 个元素是 非递增的 。
     * 下标 i 之后 的 k 个元素是 非递减的 。
     * 按 升序 返回所有好下标。
     *
     * tips:
     * n == nums.length
     * 3 <= n <= 10^5
     * 1 <= nums[i] <= 10^6
     * 1 <= k <= n / 2
     * @param: nums
     * @param: k
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/07/14 9:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> goodIndices(int[] nums, int k) {
        List<Integer> result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 思路是使用前缀和后缀, 前缀定义为 pre[], 其中 pre[i], 表示以 i 结尾的子数组中, 最长的非严格递减子数组长度,
     * 假设[j ~ i] 是非严格递减的, pre[i] = pre[j] + (i - j); 同理可以得到 suf[] 数组, 即以 i 开始的子数组中,
     * 最长的非严格递增的子数组长度.
     * 2. 构建 pre[] 需要使用正序遍历, suf[] 使用倒序遍历得到. 如果 nums[i] >= nums[i - 1], 则 pre[i] = pre[i - 1] + 1; 否则 pre[i] = 1;
     * 3. pre[] 和 suf[] 数组的长度都是 n, 构建完成后, 再次遍历 i, i 的取值范围是 [k, n - k), 对于 nums[i],
     * 只需要判断 pre[i - 1] >= k && suf[i + 1] >= k 满足要求, list.add(i); 最后返回list;
     * AC: 6ms/96.91MB
     * @param: nums
     * @param: k
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/07/14 9:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] nums, int k) {
        int n = nums.length;
        List<Integer> result = new ArrayList<>();

        if (n < 2 * k + 1) {
            return result;
        }

        int[] pre = new int[n];
        int[] suf = new int[n];

        pre[0] = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] <= nums[i - 1]) {
                pre[i] = pre[i - 1] + 1;
            } else {
                pre[i] = 1;
            }
        }

        suf[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] <= nums[i + 1]) {
                suf[i] = suf[i + 1] + 1;
            } else {
                suf[i] = 1;
            }
        }

        for (int i = k; i < n - k; i++) {
            if (pre[i - 1] >= k && suf[i + 1] >= k) {
                result.add(i);
            }
        }

        return result;
    }

}
