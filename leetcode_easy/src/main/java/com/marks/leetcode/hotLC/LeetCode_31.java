package com.marks.leetcode.hotLC;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_31 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/12 9:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_31 {

    /**
     * @Description:
     * 整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
     *
     * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
     * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。
     * 更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。
     * 如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
     *
     * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
     * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
     * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
     * 给你一个整数数组 nums ，找出 nums 的下一个排列。
     *
     * 必须 原地 修改，只允许使用额外常数空间。
     * @param: nums
     * @return void
     * @author marks
     * @CreateDate: 2025/12/12 9:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void nextPermutation(int[] nums) {
        method_01(nums);
    }

    /**
     * @Description:
     * 1. 从后往前找第一个降序的元素，记为i, 假设我遍历了整个数组, 但是找不到，则说明数组已经是字典序最大的了, 此时的i为0
     * 2. 如果i = 0, 则说明数组已经是字典序最大的了, 需要翻转整个数组, 双指针翻转数组
     * 3. 如果找到降序的元素i, 则从i+1开始, 找到比nums[i]大的第一个元素, 假设为j, 交换i和j的位置
     * 4. 翻转i+1 ~ n - 1的数组
     * AC: 0ms/43.97MB
     * @param: nums
     * @return void
     * @author marks
     * @CreateDate: 2025/12/12 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(int[] nums) {
        int n = nums.length;
        // 降序遍历
        int index = -1;
        // 用 flag 标记 是否找到降序的元素, 如果没有找到, 则说明数组已经是字典序最大的了, 需要翻转整个数组
        for (int i = n - 2; i >= 0 && index == -1; i--) {
            // 倒序遍历 i+1 ~ n - 1, 找到第一个升序的元素j
            for (int j = n - 1; j > i; j--) {
                if (nums[j] > nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                    index = i;
                    break;
                }
            }
        }
        // i >= 0, 还需要进行一次交换, 例如 1,3,2 => 找到第一个降序序列是 1, x, 2, 交换1, 2的值后 2, 3, 1;
        // 2, 3, 1 但是这并不是最小的, 需要反转index + 1 ~ n - 1的数组
        // 使用双指针翻转
        int left = index + 1, right = n - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

}
