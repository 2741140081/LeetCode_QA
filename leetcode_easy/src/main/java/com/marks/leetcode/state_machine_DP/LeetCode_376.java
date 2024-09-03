package com.marks.leetcode.state_machine_DP;

import java.util.ArrayList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/3 11:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_376 {
    /**
     * @Description: [
     * 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为 摆动序列 。第一个差（如果存在的话）可能是正数或负数。
     * 仅有一个元素或者含两个不等元素的序列也视作摆动序列。
     * 例如， [1, 7, 4, 9, 2, 5] 是一个 摆动序列 ，因为差值 (6, -3, 5, -7, 3) 是正负交替出现的。
     * 相反，[1, 4, 7, 2, 5] 和 [1, 7, 4, 5, 5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
     * 子序列 可以通过从原始序列中删除一些（也可以不删除）元素来获得，剩下的元素保持其原始顺序。
     *
     * 给你一个整数数组 nums ，返回 nums 中作为 摆动序列 的 最长子序列的长度 。
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/3 11:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int wiggleMaxLength(int[] nums) {
        int result = 0;
//        result = method_01(nums);
//        result = method_02(nums);
        result = method_03(nums);
        result = method_04(nums);
        return result;
    }

    /**
     * @Description: [官方题解: 贪心
     * 记录波峰和波谷的变化数量
     * 用ret 记录波峰和波谷的数量
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/3 15:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_04(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }
        int prevdiff = nums[1] - nums[0];
        int ret = prevdiff != 0 ? 2 : 1;
        for (int i = 2; i < n; i++) {
            int diff = nums[i] - nums[i - 1];
            if ((diff > 0 && prevdiff <= 0) || (diff < 0 && prevdiff >= 0)) {
                ret++;
                prevdiff = diff;
            }
        }
        return ret;
    }

    /**
     * @Description: [
     * 官方题解: 基于method_02
     * 优化空间后
     * AC:0ms/40.07MB
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/3 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }
        int up = 1;
        int down = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                up = Math.max(up, down + 1);
            } else if (nums[i] < nums[i - 1]) {
                down = Math.max(down, up + 1);
            }
        }
        return Math.max(up, down);
    }

    /**
     * @Description: [官方题解:
     * 动态规划
     * 动态规划之空间优化
     * @see #method_03(int[])
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/3 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }
        int[] up = new int[n];
        int[] down = new int[n];
        up[0] = down[0] = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                up[i] = Math.max(up[i - 1], down[i - 1] + 1);
                down[i] = down[i - 1];
            } else if (nums[i] < nums[i - 1]) {
                down[i] = Math.max(down[i - 1], up[i - 1] + 1);
                up[i] = up[i - 1];
            }else {
                up[i] = up[i - 1];
                down[i] = down[i - 1];
            }
        }
        return Math.max(up[n - 1], down[n - 1]);
    }
    /**
     * @Description: [
     * 使用ArrayList保存摆动序列
     * list = new ArrayList<Integer>();
     * 判断nums[i] 与list.get(size - 1)的大小
     * 同时使用flag记录上一次是波峰还是波谷
     * 波峰: flag = true;
     * 波谷: flag = false;
     * 另外如果不符合条件, 例如出现更高的波峰或者更低的波谷, 则根据贪心原则, 将nums[i] 替换掉list 的末尾值
     * AC:1ms/40.2MB
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/3 15:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        ArrayList<Integer> list = new ArrayList<>();
        int n = nums.length;
        if (n == 1) {
            return 1;
        }else if (n == 2) {
            return nums[0] != nums[1] ? 2 : 1;
        }
        // n > 2
        list.add(nums[0]);
        int p = 1;
        while (p < n && list.get(0) == nums[p]) {
            if (p == n - 1) {
                return list.size();
            }
            p++;
        }
        boolean flag = false; // 用flag = true 表示下一次需要的是下降的，flag = false 表示下一次是上升的
        list.add(nums[p]);
        if (list.get(0) < nums[p]) {
            flag = true;
        }

        for (int i = p + 1; i < n; i++) {
            int size = list.size();
            int temp_mid = list.get(size - 1);
            if (nums[i] != temp_mid) {
                if (flag) {
                    if (nums[i] < temp_mid) {
                        list.add(nums[i]);
                        flag = false;
                    }else {
                        // 贪心, 设置一个更大的峰顶
                        list.set(size - 1, nums[i]);
                        flag = true;
                    }
                }else {
                    if (nums[i] > temp_mid) {
                        list.add(nums[i]);
                        flag = true;
                    }else {
                        // 贪心, 设置一个更小的峰谷
                        list.set(size - 1, nums[i]);
                        flag = false;
                    }
                }
            }
        }
        return list.size();
    }
}
