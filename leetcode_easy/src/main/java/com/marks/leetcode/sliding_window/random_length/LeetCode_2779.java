package com.marks.leetcode.sliding_window.random_length;

import java.util.Arrays;
import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2779 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的整数数组 nums 和一个 非负 整数 k 。
     *
     * 在一步操作中，你可以执行下述指令：
     *
     * 在范围 [0, nums.length - 1] 中选择一个 此前没有选过 的下标 i 。
     * 将 nums[i] 替换为范围 [nums[i] - k, nums[i] + k] 内的任一整数。
     * 数组的 美丽值 定义为数组中由相等元素组成的最长子序列的长度。
     *
     * 对数组 nums 执行上述操作任意次后，返回数组可能取得的 最大 美丽值。
     *
     * 注意：你 只 能对每个下标执行 一次 此操作。
     *
     * 数组的 子序列 定义是：经由原数组删除一些元素（也可能不删除）得到的一个新数组，且在此过程中剩余元素的顺序不发生改变。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i], k <= 10^5
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumBeauty(int[] nums, int k) {
        int result = 0;
//        result = method_01(nums, k);
//        result = method_02(nums, k);
        result = method_03(nums, k);
        return result;
    }
    /**
     * @Description: [
     * 查分数组
     * AC:10ms/59.82MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 16:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] nums, int k) {
        int m = 0;
        m = Arrays.stream(nums).max().getAsInt();
        int[] diff = new int[m + 2];
        for (int num : nums) {
            diff[Math.max(num - k, 0)]++;
            diff[Math.min(num + k + 1, m + 1)]--;
        }
        int res = 0, count = 0;
        for (int x : diff) {
            count += x;
            res = Math.max(res, count);
        }
        return res;
    }

    /**
     * @Description: [
     * E1:nums = [4,6,1,2], k = 2
     * 1, 2, 4, 6
     * 排序 + 滑动窗口
     * AC:47ms/58.75MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 16:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int left = 0;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            while (nums[i] - 2 * k > nums[left]) {
                left++;
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }

    /**
     * @Description: [
     * 输入：nums = [4,6,1,2], k = 2
     * 输出：3
     * 解释：在这个示例中，我们执行下述操作：
     * - 选择下标 1 ，将其替换为 4（从范围 [4,8] 中选出），此时 nums = [4,4,1,2] 。
     * - 选择下标 3 ，将其替换为 4（从范围 [0,4] 中选出），此时 nums = [4,4,1,4] 。
     * 执行上述操作后，数组的美丽值是 3（子序列由下标 0 、1 、3 对应的元素组成）。
     * 可以证明 3 是我们可以得到的由相等元素组成的最长子序列长度。
     *
     * Result:605/621 超时！！！
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 16:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int ans = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = nums[i] - k; j <= nums[i] + k; j++) {
                map.merge(j, 1, Integer::sum);
                ans = Math.max(ans, map.get(j));
            }
        }
        return ans;
    }
}
