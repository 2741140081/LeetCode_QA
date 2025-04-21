package com.marks.leetcode.greedy_algorithm;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/26 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2554 {
    /**
     * @Description:
     * 给你一个整数数组 banned 和两个整数 n 和 maxSum 。你需要按照以下规则选择一些整数：
     *
     * 被选择整数的范围是 [1, n] 。
     * 每个整数 至多 选择 一次 。
     * 被选择整数不能在数组 banned 中。
     * 被选择整数的和不超过 maxSum 。
     * 请你返回按照上述规则 最多 可以选择的整数数目。
     * @param banned
     * @param n
     * @param maxSum
     * @return int
     * @author marks
     * @CreateDate: 2025/3/26 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxCount(int[] banned, int n, int maxSum) {
        int result;
        result = method_01(banned, n, maxSum);
        result = method_02(banned, n, maxSum);
        return result;
    }

    /**
     * @Description:
     * AC: 58ms/44.99MB
     * @param banned
     * @param n
     * @param maxSum
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] banned, int n, int maxSum) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int key : banned) {
            map.merge(key, 1, Integer::sum);
        }
        int ans = 0;
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if (map.containsKey(i)) {
                continue;
            }
            if (sum + i <= maxSum) {
                ans++;
                sum += i;
            }
        }
        return ans;
    }

    /**
     * @Description:
     * TLE: 超时, 时间复杂度是 O(n^2), list.contains(i) 的复杂度是 O(n), 所以 超时
     * 我们需要找一个 O(1) contains(i) 的判断, 使用 Map
     * @param banned
     * @param n
     * @param maxSum
     * @return int
     * @author marks
     * @CreateDate: 2025/3/26 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] banned, int n, int maxSum) {
        List<Integer> list = Arrays.stream(banned).boxed().collect(Collectors.toList());
        int ans = 0;
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if (list.contains(i)) {
                continue;
            }
            if (sum + i <= maxSum) {
                ans++;
                sum += i;
            }
        }
        return ans;
    }
}
