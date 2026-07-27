package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_810 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/27 10:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_810 {

    /**
     * @Description:
     * 黑板上写着一个非负整数数组 nums[i] 。
     * Alice 和 Bob 轮流从黑板上擦掉一个数字，Alice 先手。
     * 如果擦除一个数字后，剩余的所有数字按位异或运算得出的结果等于 0 的话，当前玩家游戏失败。
     * 另外，如果只剩一个数字，按位异或运算得到它本身；如果无数字剩余，按位异或运算结果为 0。
     * 并且，轮到某个玩家时，如果当前黑板上所有数字按位异或运算结果等于 0 ，这个玩家获胜。
     * 假设两个玩家每步都使用最优解，当且仅当 Alice 获胜时返回 true。
     *
     * tips:
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] < 2^16
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/07/27 11:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean xorGame(int[] nums) {
        boolean result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. xor 运算, 相同为0, 不同为1, 可以统计每一位上1的数量 int[] cnt = new int[16];
     * 2. 如何删除, 是一个最优解?
     * 直接看题解, AC: 0ms/45.67MB
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/07/27 11:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums) {
        int n = nums.length;
        if (n % 2 == 0) {
            return true;
        }
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }

        return xor == 0;
    }

}
