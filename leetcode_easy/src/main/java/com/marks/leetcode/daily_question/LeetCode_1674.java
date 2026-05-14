package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1674 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/13 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1674 {

    /**
     * @Description:
     * 给你一个长度为 偶数 n 的整数数组 nums 和一个整数 limit 。
     * 每一次操作，你可以将 nums 中的任何整数替换为 1 到 limit 之间的另一个整数。
     * 如果对于所有下标 i（下标从 0 开始），nums[i] + nums[n - 1 - i] 都等于同一个数，则数组 nums 是 互补的 。
     * 例如，数组 [1,2,3,4] 是互补的，因为对于所有下标 i ，nums[i] + nums[n - 1 - i] = 5 。
     * 返回使数组 互补 的 最少 操作次数。
     *
     * tips:
     * n == nums.length
     * 2 <= n <= 10^5
     * 1 <= nums[i] <= limit <= 10^5
     * n 是偶数。
     * @param: nums
     * @param: limit
     * @return int
     * @author marks
     * @CreateDate: 2026/05/13 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minMoves(int[] nums, int limit) {
        int result;
        result = method_01(nums, limit);
        return result;
    }

    /**
     * @Description:
     * 1. 求对应 int sum = nums[left] + nums[right]; 求相同 sum 的最大值 count
     * 2. 返回结果是 n / 2 - count 即为最小操作次数
     * 3. 还有 limit 的限制, 由于 limit 可能小于 Math.max(num[i0], num[j0]) + limit < num[i1] + num[j1],
     * 这就导致一次修改无法满足, 必须要2次操作才能使得 num[ix] + num[jx] = num[i1] + num[j1]
     * 4. 好像是没思路了, 看看提示先
     * 5. 查看题解使用差分数组
     * 6. 假设 a, b分别是 nums[i] 和 nums[j] 种的较小值和较大值, 可以修改的范围是[2, limit * 2] 之间. 并且 c 是所需的值.
     * 7.1 2 <= c < a + 1 时, 需要进行2次操作, 将 a, b 都需要修改
     * 7.2 a + 1 <= c < a + b 时, 需要1次操作即可, 即修改a 或者 修改 b
     * 7.3 c == a + b 时, 需要 0 次操作
     * 7.4 a + b < c <= b + limit 时, 需要 1 次操作 即可
     * 7.5 b + limit < c <= limit * 2 时, 需要 2 次操作
     * AC: 6ms/74.68MB
     * @param: nums
     * @param: limit
     * @return int
     * @author marks
     * @CreateDate: 2026/05/13 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int limit) {
        int n = nums.length;
        int[] diff = new int[2 * limit + 2];
        for (int i = 0; i < n / 2; i++) {
            int a = Math.min(nums[i], nums[n - 1 - i]);
            int b = Math.max(nums[i], nums[n - 1 - i]);
            diff[2] += 2; // 需要 2 次操作
            diff[a + 1] -= 1; // 只需要 1 次操作, 减去 1 次
            diff[a + b] -= 1; // 只需要 0 次操作, 在减去 1 次
            diff[a + b + 1] += 1; // 需要 1 次操作
            diff[b + limit + 1] += 1; // 需要 1 次操作
        }
        int minOperations = n;
        int curr = 0;
        for (int i = 2; i <= 2 * limit; i++) {
            curr += diff[i];
            minOperations = Math.min(minOperations, curr);
        }

        return minOperations;
    }

}
