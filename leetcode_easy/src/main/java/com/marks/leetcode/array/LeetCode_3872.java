package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3872 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/13 15:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3872 {

    /**
     * @Description:
     * 给你一个整数数组 nums。
     * 如果子数组中相邻元素的差值是一个常数，那么这个子数组被称为 等差子数组。
     * 你可以将 nums 中的 最多 一个元素替换为任意一个 整数。然后，从 nums 中选择一个等差子数组。
     * 返回一个整数，该整数表示你可以选择的 最长 等差子数组的长度。
     * 子数组 是数组中一段连续的元素序列。
     *
     * tips:
     * 4 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/13 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestArithmetic(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 假设有 a, b, c, d, e 5个数, 选择中间的 c 进行替换, 替换后的结果会有哪几种可能性:
     * 2. s1 = a - b, s2 = d - e; 如果 s1 != s2, 如果需要让 s1是数组的等差值, 那么可以确定 c 的大小是 c = b - s1,
     * 现在只需要判断 d 是否满足 d 与 b - 2 * s1 的关系即可.
     * 3. 这边会有两个关键的数据, 一个是 前一个的等差值, 一个是原始等差值.
     * 4. 我怎么确定被修改的是哪一个数?
     * 5. 还是需要4个数, a, b, c, d; 当前修改的是 c, 如果s1 = a - b, 并且 d = b - 2 * s1且 c != b - s1, 此时修改 c, 会让 以 b 结尾
     * 和 以 d 开头, 差值为 s1 的两端等差数组进行链接. 此时的长度为 l1 + l2 + 1. 如果 d != b - 2 * s1, 那么 此时的最大长度是 l1 + 1.
     * 6. 现在有点明确了, 需要找到在不修改任何数的情况下某个坐标的等差数的长度, 以及以某个数开始的等差长度, 分别使用前缀和和后缀和来存储
     * 7. 现在有个问题是, 一个数在前缀和后缀中存入是0 还是1?
     * AC: 98ms/213.47MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/13 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        // 前缀, 后缀
        int[][] pre = new int[n][2]; // {长度, 等差值}, 单个数使用后一个数的值并且长度设置为1, 防止发生错误
        int[][] suf = new int[n][2];
        pre[0][1] = nums[0] - nums[1];
        pre[0][0] = 1;
        suf[n - 1][1] = nums[n - 2] - nums[n - 1];
        suf[n - 1][0] = 1;
        int ans = 0;
        // 从1开始遍历, 构建前缀
        for (int i = 1; i < n; i++) {
            int s1 = nums[i - 1] - nums[i];
            if (s1 != pre[i - 1][1]) {
                pre[i][0] = 2; // 新的起点
                pre[i][1] = s1;
            } else {
                pre[i][0] = pre[i - 1][0] + 1;
                pre[i][1] = s1;
            }
            ans = Math.max(ans, pre[i][0]);
        }
        // 构建后缀, 从 n - 2开始
        for (int i = n - 2; i >= 0; i--) {
            int s1 = nums[i] - nums[i + 1]; // 还是使用 nums[i] - nums[i + 1]
            if (s1 != suf[i + 1][1]) {
                suf[i][0] = 2;
                suf[i][1] = s1;
            } else {
                suf[i][0] = suf[i + 1][0] + 1;
                suf[i][1] = s1;
            }
            ans = Math.max(ans, suf[i][0]);
        }
        if (ans + 1 >= n) {
            // 提前剪枝
            return n;
        } else {
            // 长度 + 1, 因为可以修改一个数为任意值, 所以长度 + 1.
            ans++;
        }

        // 应该从2开始, 判断 i - 1, i, 以及 i + 1 是否是等差, 如果不是, 则修改 nums[i], 以便获得最长等差子数组
        for (int i = 1; i < n - 1; i++) {
            if (nums[i - 1] - nums[i] != nums[i] - nums[i + 1]) {
                // 此时需要进行修改操作
                int s1 = pre[i - 1][1], len1 = pre[i - 1][0];
                int s2 = suf[i + 1][1], len2 = suf[i + 1][0];
                if (nums[i + 1] == nums[i - 1] - 2 * s1) {
                    if (s1 == s2) {
                        ans = Math.max(ans, len1 + len2 + 1);
                    } else {
                        ans = Math.max(ans, len1 + 2); // i 和 i + 1 都可以添加到以 i - 1 结尾的等差数列中
                    }
                }

                // 判断 len1 是否是 len2 的最长
                if (nums[i - 1] == nums[i + 1] + 2 * s2) {
                    if (s1 == s2) {
                        ans = Math.max(ans, len1 + len2 + 1);
                    } else {
                        ans = Math.max(ans, len2 + 2);
                    }
                }
            }
        }

        return ans;
    }

}
