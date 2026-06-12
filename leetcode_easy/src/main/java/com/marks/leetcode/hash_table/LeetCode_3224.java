package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3224 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/9 15:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3224 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums ，n 是 偶数 ，同时给你一个整数 k 。
     * 你可以对数组进行一些操作。每次操作中，你可以将数组中 任一 元素替换为 0 到 k 之间的 任一 整数。
     * 执行完所有操作以后，你需要确保最后得到的数组满足以下条件：
     * 存在一个整数 X ，满足对于所有的 (0 <= i < n) 都有 abs(a[i] - a[n - i - 1]) = X 。
     * 请你返回满足以上条件 最少 修改次数。
     *
     * tips:
     * 2 <= n == nums.length <= 10^5
     * n 是偶数。
     * 0 <= nums[i] <= k <= 10^5
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/06/09 15:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minChanges(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 查看题解使用差分数组? 如何实现
     * 2. int a = Math.min(nums[i], nums[n - 1 - i]), int b = Math.max(nums[i], nums[n - 1 - i]);
     * 3. 整体理解错误, 需要的 b - a 的组合 而不是 a + b, X 的取值范围是 [0, k] 之间, int abs = b - a, 需要让 abs 增大, k - a, 或者 k
     * 4. 那些情况下仅需要修改1次可以完成, diff[0] += 1 需要1次, 如果持续减少 a 的值 或者增加 b 的值, 相当于增加 abs的值, diff[abs + 1] += 1
     * diff[abs] -= 1; 需要 0 次修改.{abs + 1, b, k - a} 这三种都是仅需要1次修改即可, 当超过这3种情况后需要修改2次 {b + 1, k - a - 1}
     * AC: 4ms/75.55MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/06/09 16:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int n = nums.length;
        int[] diff = new int[k + 2];
        for (int i = 0; i < n / 2; i++) {
            int a = Math.min(nums[i], nums[n - 1 - i]);
            int b = Math.max(nums[i], nums[n - 1 - i]);
            int abs = b - a;
            int max = Math.max(b, k - a);
            diff[0] += 1;
            diff[abs] -= 1;
            diff[abs + 1] += 1;
            diff[max + 1] += 1;
        }

        int minOps = n;
        int cnt = 0;
        for (int i = 0; i < k + 2; i++) {
            cnt += diff[i];
            minOps = Math.min(minOps, cnt);
        }

        return minOps;
    }

    /**
     * @Description:
     * 1. 找到 X 数量最大的组合数
     * 2. 其它不同的 x1 与 X 的关系
     * 2.1 x1 > X: 需要调整较大的数变小或者调整较小的数变大, int abs = x1 - X; int max = Math.max(nums[i], nums[n - 1 - i]); int min = Math.min(nums[i], nums[n - 1 - i]);
     * 将 max 减少 abs, max -= abs check int currX1 = Math.abs(max - min); min += abs; int currX2 = Math.abs(max - min); 如果 currX1 == X || currX2 == X 需要修改次数 +1
     * 否则修改次数 +2
     * fail: 超时 通过的测试用例: 681/682
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/06/09 15:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n / 2; i++) {
            int tempX = Math.abs(nums[i] - nums[n - 1 - i]);
            map.put(tempX, map.getOrDefault(tempX, 0) + 1);
        }

        int ans = n;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int sum = 0;
            int maxX = entry.getKey();
            int cnt = entry.getValue();
            int remain = n / 2 - cnt; // 需要修改的最小次数
            if (remain > ans) {
                continue;
            }
            for (int i = 0; i < n / 2; i++) {
                int tempX = Math.abs(nums[i] - nums[n - 1 - i]);
                int max = Math.max(nums[i], nums[n - 1 - i]);
                int min = Math.min(nums[i], nums[n - 1 - i]);
                if (tempX > maxX) {
                    int abs = tempX - maxX;
                    int currX1 = Math.abs(max - abs - min);
                    if (currX1 == maxX && (max - abs >= 0 || min + abs <= k)) {
                        sum += 1;
                    } else {
                        sum += 2;
                    }
                } else if (tempX < maxX) {
                    int abs = maxX - tempX;
                    int currX1 = Math.abs(max + abs - min);
                    if (currX1 == maxX && (max + abs <= k || min - abs >= 0)) {
                        sum += 1;
                    } else {
                        sum += 2;
                    }
                }
            }
            ans = Math.min(ans, sum);

        }

        return ans;
    }

}
