package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/15 11:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1863 {

    
    /**
     * @Description:
     * 一个数组的 异或总和 定义为数组中所有元素按位 XOR 的结果；如果数组为 空 ，则异或总和为 0 。
     *
     * 例如，数组 [2,5,6] 的 异或总和 为 2 XOR 5 XOR 6 = 1 。
     * 给你一个数组 nums ，请你求出 nums 中每个 子集 的 异或总和 ，计算并返回这些值相加之 和 。
     *
     * 注意：在本题中，元素 相同 的不同子集应 多次 计数。
     *
     * 数组 a 是数组 b 的一个 子集 的前提条件是：从 b 删除几个（也可能不删除）元素能够得到 a 。
     *
     * tips:
     * 1 <= nums.length <= 12
     * 1 <= nums[i] <= 20
     *
     *
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/8/15 11:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int subsetXORSum(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }


    /**
     * @Description:
     * E1:
     * 输入：nums = [5,1,6]
     * 输出：28
     * 解释：[5,1,6] 共有 8 个子集：
     * [5,1,6], [5,1], [5,6], [5], [1,6], [1], [6], []
     * 1. 先是一个空的数组, 然后慢慢向其中添加数组元素, 并且计算此时的异或和
     * 2. 难道需要通过 n 来判断是否返回吗? 即通过 nums.length 来判断是否返回?
     * 3. a ^ b = c => a ^ b ^ b = c ^ b => a = b ^ c, 根据以上推导, 我们可以得到假设当前异或值为 a, 下一个异或元素为 b
     * 执行后的结果返回为 c, 那么就可以推导为之前的结果 a = b ^ c, 好像没什么用
     * 4. backTrack() 感受是递归而不是回溯
     *
     * AC: 0ms/40.23MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/15 11:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int ans;
    private int method_01(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        ans = 0;

        backTrack(nums, 0, 0);
        return ans;
    }

    private void backTrack(int[] nums, int index, int value) {
        if (index == nums.length) {
            ans += value;
            return;
        }
        // 添加当前 index 元素
        backTrack(nums, index + 1, value ^ nums[index]);

        // 不添加当前 index 元素
        backTrack(nums, index + 1, value);
    }
}
