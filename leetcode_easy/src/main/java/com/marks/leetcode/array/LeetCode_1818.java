package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1818 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/3 9:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1818 {

    /**
     * @Description:
     * 给你两个正整数数组 nums1 和 nums2 ，数组的长度都是 n 。
     * 数组 nums1 和 nums2 的 绝对差值和 定义为所有 |nums1[i] - nums2[i]|（0 <= i < n）的 总和（下标从 0 开始）。
     * 你可以选用 nums1 中的 任意一个 元素来替换 nums1 中的 至多 一个元素，以 最小化 绝对差值和。
     * 在替换数组 nums1 中最多一个元素 之后 ，返回最小绝对差值和。因为答案可能很大，所以需要对 10^9 + 7 取余 后返回。
     * |x| 定义为：
     * 如果 x >= 0 ，值为 x ，或者
     * 如果 x <= 0 ，值为 -x
     *
     * tips:
     * n == nums1.length
     * n == nums2.length
     * 1 <= n <= 10^5
     * 1 <= nums1[i], nums2[i] <= 10^5
     * @param: nums1
     * @param: nums2
     * @return int
     * @author marks
     * @CreateDate: 2026/07/03 9:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minAbsoluteSumDiff(int[] nums1, int[] nums2) {
        int result;
        result = method_01(nums1, nums2);
        return result;
    }

    /**
     * @Description:
     * 1. 最小化 绝对差值和, 并且可以使用 nums1 中的 任意一个 元素来替换 nums1 中的 至多 一个元素
     * 2. 由于需要 nums1[] 和 nums2[] 需要对应, 所以除非用 map 存储 nums1 下标和值, 然后对 nums1[] 排序; 否则只能使用间接排序
     * Integer[] idx 来对 nums1[] 进行间接排序
     * 3. 排序完成后, 遍历 [0 ~ n), 对于 nums2[i], 如果 nums2[i] != nums1[i], 则在排序后的数组中找到 nums2[i] 的位置, 计算 minAbs 值,
     * 4. long sumDiff 是 nums1[] 和 nums2[] 的绝对差值和(不进行任何替换的大小), 如果在 i 处执行替换操作 long ans = Math.min(ans, sumDiff - (Math.abs(nums1[i] - nums2[i]) - minAbs));
     * 5. 最后返回 ans % (10^9 + 7)
     * 6. 关键点在于从 间接排序 idx[] 数组通过二分查找法找到 nums2[i] 的位置, 这个还是有点绕, 不如直接拷贝一份数组, 然后对其中一份数组进行排序, 保留一份原始 nums1[]
     * 7. 经过上述思考, 采用数组拷贝方式.
     * AC: 57ms/89.35MB
     * @param: nums1
     * @param: nums2
     * @return int
     * @author marks
     * @CreateDate: 2026/07/03 9:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int MOD = (int) (1e9 + 7);
        long sumDiff = 0;
        for (int i = 0; i < n; i++) {
            sumDiff += Math.abs(nums1[i] - nums2[i]);
        }
        // 拷贝 nums1[], 使用 System.arraycopy()
        int[] sortArr1 = new int[n];
        System.arraycopy(nums1, 0, sortArr1, 0, n);
        // 对 sortArr1[] 进行排序
        Arrays.sort(sortArr1);
        long ans = sumDiff;
        for (int i = 0; i < n; i++) {
            if (nums2[i] != nums1[i]) {
                int currAbs = Math.abs(nums1[i] - nums2[i]);
                // 二分查找 nums2[i] 的位置
                int minAbsDiff = findMinAbsDiff(sortArr1, nums2[i]);
                ans = Math.min(ans, sumDiff - currAbs + minAbsDiff);
            }
        }

        return (int) (ans % MOD);
    }

    private int findMinAbsDiff(int[] sortArr1, int target) {
        // 1. 执行二分查找
        int idx = Arrays.binarySearch(sortArr1, target);

        // 2. 计算插入点
        int insertPos;
        if (idx >= 0) {
            // 情况1: 找到了完全匹配的元素，差值为0，直接返回
            return 0;
        } else {
            // 情况2: 未找到，计算插入点
            insertPos = -idx - 1;
        }

        int minDiff = Integer.MAX_VALUE;

        // 3. 检查“最小大于等于”的候选者 (右侧邻居)
        // insertPos 指向第一个 >= target 的元素
        if (insertPos < sortArr1.length) {
            int rightVal = sortArr1[insertPos];
            int diff = Math.abs(rightVal - target);
            if (diff < minDiff) {
                minDiff = diff;
            }
        }

        // 4. 检查“最大小于”的候选者 (左侧邻居)
        // insertPos - 1 指向最后一个 < target 的元素
        if (insertPos > 0) {
            int leftVal = sortArr1[insertPos - 1];
            int diff = Math.abs(leftVal - target);
            if (diff < minDiff) {
                minDiff = diff;
            }
        }

        return minDiff;
    }

}
