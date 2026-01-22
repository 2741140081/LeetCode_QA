package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3507 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/22 15:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3507 {

    /**
     * @Description:
     * 给你一个数组 nums，你可以执行以下操作任意次数：
     *
     * 选择 相邻 元素对中 和最小 的一对。如果存在多个这样的对，选择最左边的一个。
     * 用它们的和替换这对元素。
     * 返回将数组变为 非递减 所需的 最小操作次数 。
     *
     * 如果一个数组中每个元素都大于或等于它前一个元素（如果存在的话），则称该数组为非递减。
     * tips:
     * 1 <= nums.length <= 50
     * -1000 <= nums[i] <= 1000
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/01/22 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumPairRemoval(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： nums = [5,2,3,1]
     * 1. 没什么好方法, 直接暴力吧, 遍历整个数组 [7, 5, 4] => 合并 3, 1 => [7, 5 + 1, 4 + 2]
     * 2. [7, 6]
     * AC: 1ms/43.62MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/01/22 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = 0; // 删除的次数
        int n = nums.length;
        while (n > 1) {
            // 判断当前剩余元素是否是非递减的, 先判断在合并.
            if (check(nums, n)) {
                return ans;
            }
            int min = Integer.MAX_VALUE; // 最小值
            int index = -1; // 最小值索引
            for (int i = 0; i < n - 1; i++) {
                if (nums[i] + nums[i + 1] < min) {
                    min = nums[i] + nums[i + 1]; // 取最左侧的最小值
                    index = i;
                }
            }
            // 合并
            ans++;
            nums[index] = min;
            // index 后面的数据前移一个位置
            for (int i = index + 1; i < n - 1; i++) {
                nums[i] = nums[i + 1];
            }
            n--;
        }

        return ans;
    }

    private boolean check(int[] nums, int n) {
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                return false;
            }
        }
        return true;
    }

}
