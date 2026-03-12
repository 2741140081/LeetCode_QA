package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2439 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/11 16:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2439 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的数组 nums ，它含有 n 个非负整数。
     * 每一步操作中，你需要：
     * 选择一个满足 1 <= i < n 的整数 i ，且 nums[i] > 0 。
     * 将 nums[i] 减 1 。
     * 将 nums[i - 1] 加 1 。
     * 你可以对数组执行 任意 次上述操作，请你返回可以得到的 nums 数组中 最大值 最小 为多少。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/03/11 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimizeArrayValue(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 输入：nums = [3,7,1,6]
     * 输出：5
     * 1. nums[0] 是一个只能增加不能减少的数
     * 2. 用的应该是动态规划, 从后向前遍历, 开始进行分情况讨论
     * 3. nums[i] > num[i - 1], 表示i 可以减少, i - 1 可以增加, 可以得到总和 sum = nums[i] + nums[i - 1]
     * 4. 感觉不太对, 应该用 nums[0] 作为基准, sum 作为 其它 nums[i] 与基准直接的差值 sub = nums[i] - nums[0]
     * 5. sum < 0, 证明最大值即为 nums[0], sum > 0时, 将此时的 sum 均分给[0 ~ i] 个元素, x = sum / (i + 1), y = sum % (i + 1)
     * 如果y != 0, 调整基准值 nums[0] += (x + 1), y = 0, nums[0] += x; 更新sum = sum - x * (i + 1)
     * 6. 注意数据范围, 可能超过 int 的范围, 需要使用 long
     * AC: 3ms/78.76MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/03/11 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int baseNum = nums[0];
        long sum = 0; // 差值
        for (int i = 1; i < n; i++) {
            int sub = nums[i] - baseNum;
            if (sum + sub > 0) {
                // 需要更新基准值
                long sumAbs = sum + sub;
                long x = sumAbs / (i + 1);
                long y = sumAbs % (i + 1);
                if (y != 0) {
                    x++;
                }
                // 更新基准值
                baseNum += (int) x;
                sum = sumAbs - x * (i + 1);
            } else {
                sum += sub;
            }
        }
        return baseNum;
    }

}
