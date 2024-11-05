package com.marks.leetcode.double_pointer.single_sequence;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/5 16:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_611 {
    public int triangleNumber(int[] nums) {
        int result;
//        result = method_01(nums);
//        result = method_02(nums);
        result = method_03(nums);
        return result;
    }

    /**
     * @Description: [
     * 官解
     * AC:37ms/42.44MB
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/11/5 16:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        int ans = 0;

        for (int i = 0; i < n - 2; i++) {
            int right = i;
            for (int left = i + 1; left < n - 1; left++) {
                while (right + 1 < n && nums[right + 1] < nums[i] + nums[left]) {
                    right++;
                }
                ans += Math.max(right - left, 0);
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * AC:714ms/42.39MB
     * 并没有时间复杂度上的优化, 查看官解, method_03
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/11/5 16:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        int ans = 0;

        for (int i = 0; i < n - 2; i++) {
            for (int left = i + 1; left < n - 1; left++) {
                int right = left + 1;
                int temp = nums[i] + nums[left]; // 较短的两条边之和
                while (right < n && nums[right] < temp) {
                    right++;
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * E1:
     * 输入: nums = [2,2,3,4]
     * 输出: 3
     * 解释:有效的组合是:
     * 2,3,4 (使用第一个 2)
     * 2,3,4 (使用第二个 2)
     * 2,2,3
     * AC:740ms/42.36MB
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/11/5 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        int ans = 0;

        for (int i = 0; i < n - 2; i++) {

            for (int left = i + 1; left < n - 1; left++) {
                int right = n - 1;
                int temp = nums[i] + nums[left]; // 较短的两条边之和
                while (right > left && nums[right] >= temp) {
                    right--;
                }
                if (right == left) {
                    continue;
                }
                if (temp > nums[right]) {
                    ans += (right - left);
                }
            }
        }
        return ans;
    }
}
