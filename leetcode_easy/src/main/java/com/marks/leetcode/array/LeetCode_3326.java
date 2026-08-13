package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3326 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/16 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3326 {

    /**
     * @Description:
     * 给你一个整数数组 nums 。
     * 一个正整数 x 的任何一个 严格小于 x 的 正 因子都被称为 x 的 真因数 。比方说 2 是 4 的 真因数，但 6 不是 6 的 真因数。
     * 你可以对 nums 的任何数字做任意次 操作 ，一次 操作 中，你可以选择 nums 中的任意一个元素，将它除以它的 最大真因数 。
     * 你的目标是将数组变为 非递减 的，请你返回达成这一目标需要的 最少操作 次数。
     * 如果 无法 将数组变成非递减的，请你返回 -1 。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/16 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    private static final int MAX_VAL = 1000001;
    private static final int[] maxFactors = new int[MAX_VAL];
    static {
        for (int i = 2; i < MAX_VAL; i++) {
            if (maxFactors[i] == 0) {
                for (int j = i; j < MAX_VAL; j += i) {
                    if (maxFactors[j] == 0) {
                        maxFactors[j] = i;
                    }
                }
            }
        }
    }

    /**
     * @Description:
     * 1. 预处理每个值的最大真因数
     * 2. 还是使用倒序处理
     * AC: 37ms/106.99MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/16 11:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) {
                nums[i] = maxFactors[nums[i]];
                if (nums[i] > nums[i + 1]) {
                    // nums[i] 是质数, 无法减少, 返回 -1
                    return -1;
                }
                ans++;
            }
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 要求数组是非递减, 即 nums[i] >= nums[i - 1]成立, 如果存在 nums[i] < nums[i - 1]，由于执行出发操作, 会使得值变小,
     * 所以需要对 nums[i - 1] 执行操作, 使得nums[i] >= nums[i - 1]成立
     * 2. 操作的关键是找到 nums[i] 的最大真因数, 并且真因数必须大于 1才能发挥效果. 通过for 循环遍历 nums[i]的真因数, 找到最大的真因数
     * 3. 应该使用倒序遍历, 如果正序遍历会导致前面可能是正常的, 但是 i 处进行除法操作后变小, 导致前面的数据会发生失效, 所以采用倒序的方式
     * AC: 268ms/91.96MB
     * 4. 查看题解, 由于数据范围不大, 所以可以采用预处理的方式, 记录 1 ~ 10^6 中所有数的最小因数,
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/16 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int ans = 0; // 记录操作次数
        // 倒序遍历
        for (int i = n - 2; i >= 0; i--) {
            // 判断当前 i 是否为非递减
            while (nums[i] > nums[i + 1]) {
                // 非递减, 需要对 nums[i] 进行操作, 直到 nums[i] <= nums[i + 1]成立
                int maxFactor = getMaxFactor(nums[i]);
                if (maxFactor == -1) {
                    return -1;
                }
                // 更新 nums[i]
                nums[i] = nums[i] / maxFactor;
                ans++;
            }
        }

        return ans;
    }

    private int getMaxFactor(int num) {
        if (num <= 1) return -1; // 1 和负数无真因数

        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return num / i; // 最小因数对应的商即为最大真因数
            }
        }
        return -1; // 未找到因数，说明是质数
    }

}
