package com.marks.leetcode.daily_question;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3629 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/8 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3629 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums。
     * 你从下标 0 开始，目标是到达下标 n - 1。
     * 在任何下标 i 处，你可以执行以下操作之一：
     * 移动到相邻格子：跳到下标 i + 1 或 i - 1，如果该下标在边界内。
     * 质数传送：如果 nums[i] 是一个质数 p，你可以立即跳到任何满足 nums[j] % p == 0 的下标 j 处，且下标 j != i 。
     * 返回到达下标 n - 1 所需的 最少 跳跃次数。
     *
     * 质数 是一个大于 1 的自然数，只有两个因子，1 和它本身。
     *
     * tips:
     * 1 <= n == nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/08 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minJumps(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    private static final int MX = 1000001;
    private static final List<Integer>[] factors = new ArrayList[MX];

    static {
        for (int i = 0; i < MX; i++) factors[i] = new ArrayList<>();
        for (int i = 2; i < MX; i++) {
            if (factors[i].isEmpty()) {
                for (int j = i; j < MX; j += i) factors[j].add(i);
            }
        }
    }

    private int method_02(int[] nums) {
        int n = nums.length;
        Map<Integer, List<Integer>> edges = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int a = nums[i];
            if (factors[a].size() == 1) {
                edges.computeIfAbsent(a, k -> new ArrayList<>()).add(i);
            }
        }
        int res = 0;
        boolean[] seen = new boolean[n];
        seen[n - 1] = true;
        List<Integer> q = new ArrayList<>();
        q.add(n - 1);
        while (true) {
            List<Integer> q2 = new ArrayList<>();
            for (int i : q) {
                if (i == 0) return res;
                if (i > 0 && !seen[i - 1]) {
                    seen[i - 1] = true;
                    q2.add(i - 1);
                }
                if (i < n - 1 && !seen[i + 1]) {
                    seen[i + 1] = true;
                    q2.add(i + 1);
                }
                for (int p : factors[nums[i]]) {
                    if (edges.containsKey(p)) {
                        for (int j : edges.get(p)) {
                            if (!seen[j]) {
                                seen[j] = true;
                                q2.add(j);
                            }
                        }
                        edges.get(p).clear();
                    }
                }
            }
            q = q2;
            res++;
        }
    }

    /**
     * @Description:
     * 1. 使用map 存储数字和下标, 方便判断nums[i] % nums[j] == 0 并且 j != i 且 nums[j] 是质数
     * 2. 使用 list 存储 nums[] 中的质数, 并且对 list 进行升序排序
     * 3. 在某次质数传送中, dp[i] 是质数, 并且导致 dp[j] 产生更新, 但是 dp[j - 1] 没有更新, 这样导致 dp[j - 1] 不是最小值, 所以导致错误发生,
     * 例如 [10, 11, 12, 13, 11]. 中间的 11 由 10 跳转得到, 但是 13 没有更新.
     * result: 910/933
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/08 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        Map<Integer, List<Integer>> map = new HashMap<>();
        Set<Integer> primeSet = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            if (!map.containsKey(num)) {
                map.put(num, new ArrayList<>());
            }
            map.get(num).add(i);
            if (isPrime(num)) {
                primeSet.add(num);
            }
        }
        // 将 primeSet 转为 list
        List<Integer> primeList = new ArrayList<>(primeSet);
        primeList.sort(Integer::compareTo);
        int[] dp = new int[n];
        // 初始化
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int i = 1; i < n; i++) {
            dp[i] = Math.min(dp[i], dp[i - 1] + 1);
            if (i + 1 < n) {
                dp[i] = Math.min(dp[i], dp[i + 1] + 1);
            }
            // 判断当前 nums[i] 能否找到 nums[j] 是质数并且 nums[j] % nums[i] == 0
            // 1. 通过 primeList 进行二分查找
            int index = binarySearch(primeList, nums[i]);
            if (index != -1) {
                for (int j = 0; j <= index; j++) {
                    if (nums[i] % primeList.get(j) == 0) {
                        // 获取 j 在 map 中的下标集合, 更新dp[i]
                        List<Integer> indexList = map.get(primeList.get(j));
                        for (int k : indexList) {
                            if (i == k) {
                                continue;
                            }
                            dp[i] = Math.min(dp[i], dp[k] + 1);
                        }
                    }
                }
            }
        }

        return dp[n - 1];
    }

    private int binarySearch(List<Integer> primeList, int num) {
        int left = 0, right = primeList.size() - 1;
        int index = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (primeList.get(mid) <= num) {
                index = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }

    /**
     * 判断一个数是否为质数
     * @param num 待判断的整数
     * @return 如果是质数返回true，否则返回false
     */
    private boolean isPrime(int num) {
        // 1. 小于等于1的数不是质数
        if (num <= 1) {
            return false;
        }
        // 2. 2和3是质数
        if (num <= 3) {
            return true;
        }
        // 3. 排除偶数和3的倍数，减少后续循环次数
        if (num % 2 == 0 || num % 3 == 0) {
            return false;
        }
        // 4. 从5开始，只检查6k-1和6k+1形式的数，直到sqrt(num)
        // 所有质数都可以表示为6k±1的形式（除了2和3）
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }

}
