package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_457 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/6 15:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_457 {

    /**
     * @Description:
     * 存在一个不含 0 的 环形 数组 nums ，每个 nums[i] 都表示位于下标 i 的角色应该向前或向后移动的下标个数：
     *
     * 如果 nums[i] 是正数，向前（下标递增方向）移动 nums[i] 步
     * 如果 nums[i] 是负数，向后（下标递减方向）移动 abs(nums[i]) 步
     * 因为数组是 环形 的，所以可以假设从最后一个元素向前移动一步会到达第一个元素，而第一个元素向后移动一步会到达最后一个元素。
     *
     * 数组中的 循环 由长度为 k 的下标序列 seq 标识：
     *
     * 遵循上述移动规则将导致一组重复下标序列 seq[0] -> seq[1] -> ... -> seq[k - 1] -> seq[0] -> ...
     * 所有 nums[seq[j]] 应当不是 全正 就是 全负
     * k > 1
     * 如果 nums 中存在循环，返回 true ；否则，返回 false 。
     *
     * tips:
     * 1 <= nums.length <= 5000
     * -1000 <= nums[i] <= 1000
     * nums[i] != 0
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/06 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean circularArrayLoop(int[] nums) {
        boolean result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 之前记得的使用快慢指针来检测环形链表成环. 当前可以使用快慢指针来检测环形数组成环判断.
     * 2. 由于需要的环的值要么全正, 要么全负. 所以记录初始值 boolean flag = nums[i] > 0;
     * 如果双指针中某一个指针上所指向的值与 flag 值不同, 则说明该环形数不成环.
     * 3. 自环的判定: nums[i] % n == 0; 此时产生自环, 并且自环需要剔除, 因为 k > 1
     * AC: 0ms/42.19MB
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/06 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                continue;
            }
            int slow = i, fast = next(nums, i);
            while (nums[slow] * nums[fast] > 0 && nums[slow] * nums[next(nums, fast)] > 0) {
                if (slow == fast) {
                    if (slow == next(nums, slow)) {
                        // 排除 k = 1 的自环
                        break;
                    } else {
                        return true;
                    }
                }
                slow = next(nums, slow);
                fast = next(nums, next(nums, fast));
            }
            int add = i;
            // 更新已经访问过的环形数
            while (nums[add] * nums[next(nums, add)] > 0) {
                int temp = add;
                add = next(nums, add);
                nums[temp] = 0;
            }

        }

        return false;
    }

    private int next(int[] nums, int curr) {
        int n = nums.length;

        return ((curr + nums[curr]) % n + n) % n;
    }

}
