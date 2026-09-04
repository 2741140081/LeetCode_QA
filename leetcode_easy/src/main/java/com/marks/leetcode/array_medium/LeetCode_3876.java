package com.marks.leetcode.array_medium;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3876 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/3 15:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3876 {

    /**
     * @Description:
     * 给你一个长度为 n 的数组 nums1，其中包含 互不相同 的整数。
     * 你需要构造另一个长度为 n 的数组 nums2，使得 nums2 中的元素要么全部为 奇数，要么全部为 偶数。
     * 对于每个下标 i，你必须从以下两种选择中 任选其一（顺序不限）：
     * nums2[i] = nums1[i]
     * nums2[i] = nums1[i] - nums1[j]，其中 j != i，且满足 nums1[i] - nums1[j] >= 1
     * 如果能够构造出满足条件的数组，则返回 true；否则，返回 false。
     * tips:
     * 1 <= n == nums1.length <= 10^5
     * 1 <= nums1[i] <= 10^9
     * nums1 中的所有整数互不相同。
     * @param: nums1
     * @return boolean
     * @author marks
     * @CreateDate: 2026/09/03 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean uniformArray(int[] nums1) {
        boolean result;
        result = method_01(nums1);
        return result;
    }

    /**
     * @Description:
     * 1. 统计奇数和偶数的个数, 如果奇数个数大于1 并且 偶数个数也于1, 返回 true.
     * 2. 如果只有1个奇数时, 则 nums2 只能构成奇数而不能构成偶数
     * 3. 最小的数不会发生改变, 假设 i 是最小的, 不可能找到 j, 使得 nums1[i] - nums1[j] >= 1,
     * 所以 nums2 的状态取决于 nums1[] 的最小值的奇偶性, 对 nums1[] 进行升序排序
     * 4. nums1[0] % 2 == 0, 则需要构建一个偶数数组, 只需要对 nums1[i] 是奇数的进行处理即可, 但是必定存在一个奇数
     * 无法转成偶数, 因为必定存在一个奇数并且没有比它更大的奇数
     * 5. nums1[0] % 2 != 0, 则需要一个偶数数组, 那么只需要处理偶数即可, 偶数处理只需要减去最小的一个奇数 nums1[0], 即可变成奇数
     * 所以直接返回 true
     * AC: 32ms/118.73MB
     * 优化: 不使用排序, 直接遍历查找最小值
     * AC: 6ms/118.89MB
     * @param: nums1
     * @return boolean
     * @author marks
     * @CreateDate: 2026/09/03 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums1) {
        int n = nums1.length;
        if (n == 1) {
            return true;
        }
        // 优化查找最小值
        int min = nums1[0];
        int count = 0;
        for (int j : nums1) {
            min = Math.min(min, j);
            if (j % 2 != 0) {
                count++;
            }
        }

        if (min % 2 == 0 && count > 0) {
            // 需要判断是否存在一个奇数
            return false;
        }

        return true;
    }

}
