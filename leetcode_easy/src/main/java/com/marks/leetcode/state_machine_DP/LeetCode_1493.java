package com.marks.leetcode.state_machine_DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/30 17:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1493 {
    /**
     * @Description: [
     * 给你一个二进制数组 nums ，你需要从中删掉一个元素。
     *
     * 请你在删掉元素的结果数组中，返回最长的且只包含 1 的非空子数组的长度。
     *
     * 如果不存在这样的子数组，请返回 0 。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * nums[i] 要么是 0 要么是 1 。
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/30 17:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestSubarray(int[] nums) {
        int result = 0;
//        result = method_01(nums);
//        result = method_02(nums);
        result = method_03(nums);
//        result = method_04(nums);
        return result;
    }

    /**
     * @Description: [
     * @see #method_03(int[])
     * 基于method_03, 空间优化
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/2 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_04(int[] nums) {
        int n = nums.length;

        int p0 = 0;
        int p1 = 0;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                p1 = p0;
                p0 = 0;
            }else {
                p0 = p0 +  1;
                p1 = p1 + 1;
            }
            ans = Math.max(ans, p1);
        }
        return ans == n ? ans - 1 : ans;
    }

    /**
     * @Description: [递推优化
     * 官方题解:
     * pre[] 与method_02的定义一致
     * dp[] 定义如下:
     * 以i结尾, 并且可以删除一个0
     * 案例E1:{1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1}
     * pre[] 存储的是每一段连续的1的值
     * pre[]={1, 2, 3, 4, 0, 1, 2, 3, 0, 0, 1, 2, 3}
     * dp[] 存储的是将i结尾0删除后的结果
     * dp[] ={1, 2, 3, 4, 4, 5, 6, 7, 3, 0, 1, 2, 3}
     *
     * nums[i] == 0:
     * dp[i] = pre[i - 1]
     * nums[i] == 1
     * dp[i] = dp[i - 1] + 1
     *
     * 因为dp[i]/pre[i] 只与 dp[i - 1]/ pre[i - 1] 有关, 因此可以使用空间优化, 使得空间复杂度降低
     * @see #method_04(int[])
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/2 10:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] nums) {
        int n = nums.length;
        int[] pre = new int[n];
        pre[0] = nums[0];
        int[] dp = new int[n];
        dp[0] = nums[0];
        int ans = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] == 0) {
                dp[i] = pre[i - 1];
                pre[i] = 0;
            }else {
                pre[i] = pre[i - 1] +  1;
                dp[i] = dp[i - 1] + 1;
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans == n ? ans - 1 : ans;
    }

    /**
     * @Description: [
     * 查看官方题解是说使用动态规划
     * 对于位置i
     * 分别计算pre[i - 1] 和 suf[i + 1]的值
     * pre[i] 表示以i结尾的 值为1的数量
     * suf[i] 表示以i开始的 值为1的数量
     * ans = Math.max(ans, pre[i - 1] + suf[i + 1])
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/2 9:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        int[] pre = new int[n];
        int[] suf = new int[n];
        pre[0] = nums[0];
        for (int i = 1; i < n; i++) {
            pre[i] = nums[i] != 0 ? pre[i - 1] + 1 : 0;
        }
        suf[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suf[i] = nums[i] != 0 ? suf[i + 1] + 1 : 0;
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int preSum = i == 0 ? 0 : pre[i - 1];
            int sufSum = i == n - 1 ? 0 : suf[i + 1];
            ans = Math.max(ans, preSum + sufSum);
        }
        return ans;
    }

    /**
     * @Description: [
     * 10^5 时间复杂度小于 O(n^2)
     * 对整体的nums进行遍历, 存储为1的数量list_1
     * 和存储0的数量, list_0
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/30 17:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;

        List<Integer> list_0 = new ArrayList<>();
        List<Integer> list_1 = new ArrayList<>();
        int count_zero = 0;
        int count_one = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                count_one++;
            }else {
                if (count_one > 0) {
                    list_1.add(count_one);
                }
                count_one = 0;
            }

            if (nums[i] == 0) {
                count_zero++;
            }else {
                if (count_zero > 0) {
                    list_0.add(count_zero);
                }
                count_zero = 0;
            }
            if (i == n - 1) {
                if (count_one > 0) {
                    list_1.add(count_one);
                }
                if (count_zero > 0) {
                    list_0.add(count_zero);
                }
            }

        }
        if (nums[0] == 0) {
            list_0.remove(0);
        }
        int m = list_1.size();
        int[] dp = new int[m + 1];

        int len = list_0.size() + list_1.size();
        // 特殊判断
        if (list_1.size() == 0) {
            return 0;
        }

        List<Integer> list = new ArrayList<>();
        int oneIndex = 0;
        int zeroIndex = 0;
        // 合并
        for (int i = 0; i < len; i++) {
            if (i % 2 == 0) {
                list.add(list_1.get(oneIndex));
                oneIndex++;
            }else {
                if (list_0.get(zeroIndex) > 1) {
                    list.add(0);
                }
                zeroIndex++;
            }

        }
        int[] array = list.stream().mapToInt(Integer::intValue).toArray();
        int ans = array[0];
        for (int i = 1; i < array.length; i++) {
            ans = Math.max(ans, array[i - 1] + array[i]);
        }
        // 相邻两个数相加最大
        return ans == n ? ans - 1 : ans;
    }
}
