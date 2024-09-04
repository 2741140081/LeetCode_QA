package com.marks.leetcode.state_machine_DP;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/4 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1262 {
    /**
     * @Description: [
     * 给你一个整数数组 nums，请你找出并返回能被三整除的元素 最大和。
     *
     * tips:
     * 1 <= nums.length <= 4 * 10^4
     * 1 <= nums[i] <= 10^4
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/4 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int maxSumDivThree(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * 1. 对整个nums进行排序
     * 2. 对nums进行遍历, 使用3个ArrayList list_0, list_1, list_2 分别存储nums[i] % 3取余的
     * 即if(nums[i] % 3 == 0) {
     *     list_0.add(nums[i])
     * }
     * 贪心 + 逆向思维, 即找出remove 的最小值, 即丢弃的最小值
     * 例如 sum % 3 == 1
     * 那么我们可以丢弃 list_1中最小的一个 或者丢弃list_2中最小的两个
     * remove = Math.min(list_1.get(i)
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/4 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int sum = Arrays.stream(nums).sum();
        Arrays.sort(nums);
        ArrayList<Integer> list_1 = new ArrayList<>();
        ArrayList<Integer> list_2 = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] % 3 == 1) {
                list_1.add(nums[i]);
            } else if (nums[i] % 3 == 2) {
                list_2.add(nums[i]);
            }
        }
        int remove = Integer.MAX_VALUE;
        if (sum % 3 == 0) {
            remove = 0;
        } else if (sum % 3 == 1) {
            if (list_1.size() >= 1) {
                remove = Math.min(remove, list_1.get(list_1.size() - 1));
            }
            if (list_2.size() >= 2) {
                remove = Math.min(remove, list_2.get(list_2.size() - 1) + list_2.get(list_2.size() - 2));
            }
        }else {
            if (list_1.size() >= 2) {
                remove = Math.min(remove, list_1.get(list_1.size() - 1) + list_1.get(list_1.size() - 2));
            }
            if (list_2.size() >= 1){
                remove = Math.min(remove, list_2.get(list_2.size() - 1));
            }
        }
        return sum - remove;
    }
}
