package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3875 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/2 11:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3875 {

    /**
     * @Description:
     * 给你一个长度为 n 的数组 nums1，其中包含 互不相同 的整数。
     * 你需要构造另一个长度为 n 的数组 nums2，使得 nums2 中的元素要么全部为 奇数，要么全部为 偶数。
     * 对于每个下标 i，你必须从以下两种选择中 任选其一（顺序不限）：
     * nums2[i] = nums1[i]
     * nums2[i] = nums1[i] - nums1[j]，其中 j != i
     * 如果能够构造出满足条件的数组，则返回 true；否则，返回 false。
     *
     *
     * @param: nums1
     * @return boolean
     * @author marks
     * @CreateDate: 2026/09/02 11:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean uniformArray(int[] nums1) {
        boolean result;
        result = method_01(nums1);
        return result;
    }

    /**
     * @Description:
     * 1. 如果要构造全部偶数的 nums2[], 那么当 nums1[i] 是奇数时, 需要有另外一个奇数 nums1[j] 使得 nums1[i] - nums1[j] 是偶数, 否则无法构建偶数
     * 2. 如果要全部构造奇数的 nums2[], 类似
     * 3. 也就是只需要判断 n = 2 的特殊情况即可, 但是特殊情况也可以是奇数, 奇数 - 偶数还是奇数
     * 4. 所以直接返回 true
     * @param: nums1
     * @return boolean
     * @author marks
     * @CreateDate: 2026/09/02 11:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums1) {
        return true;
    }

}
