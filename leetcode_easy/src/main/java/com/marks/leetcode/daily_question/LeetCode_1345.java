package com.marks.leetcode.daily_question;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1345 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/18 11:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1345 {

    /**
     * @Description:
     * 给你一个整数数组 arr ，你一开始在数组的第一个元素处（下标为 0）。
     * 每一步，你可以从下标 i 跳到下标 i + 1 、i - 1 或者 j ：
     * i + 1 需满足：i + 1 < arr.length
     * i - 1 需满足：i - 1 >= 0
     * j 需满足：arr[i] == arr[j] 且 i != j
     * 请你返回到达数组最后一个元素的下标处所需的 最少操作次数 。
     * 注意：任何时候你都不能跳到数组外面。
     *
     * tips:
     * 1 <= arr.length <= 5 * 10^4
     * -10^8 <= arr[i] <= 10^8
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/05/18 11:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minJumps(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * 1. 这题使用动态规划解决
     * 2. 转移有两种方式,
     * 2.1 一般方式, dp[i] = Math.min(dp[i], Math.min(dp[i - 1], dp[i + 1]) + 1);
     * 2.2 特殊方式: arr[i] = arr[j] && i != j时, dp[i] = Math.min(dp[i], dp[j] + 1); 对于所有的 j 都进行遍历
     * 3. 在处理过程中, 是否需要用到优先队列? 待定, 先理一理思路.
     * 4. 假设将 0 节点入栈, 栈中存储 int[] = new int[2]; {ops, index} 分别是操作数和索引. 当前栈中元素{0, 0}, int[] dp = new int[n];
     * dp[0] = 0;
     * 5. 然后通过map 获取所有 j 的列表 map.get(arr[i]); 更新所有的 j, 如果 dp[j] > dp[i] + 1, 更新dp[j] 并且将 j 入栈, 特殊方式完成;
     * 6. 然后处理一般方式, 更新 i 前后的节点, 如果 dp[i - 1] > dp[i] + 1, 更新 dp[i - 1] 并将 i - 1 入栈; 同样的方式操作 i + 1;
     * 7. 如果当前 i == n - 1, 返回此时的dp[i]; 思路完成, 写代码
     * 超时: 26/33
     * 8. 这个 map 需要处理, 假设我第一次通过 map.get(arr[i]); 然后执行 map.remove(arr[i]);
     * AC: 76ms/78.11MB
     * @param: arr
     * @return int
     * @author marks
     * @CreateDate: 2026/05/18 11:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        // 初始化dp, 一步一步走到最后, 最多需要 n 步
        Arrays.fill(dp, n);
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }
        // 创建优先队列
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        dp[0] = 0;
        // 添加0节点入栈
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int ops = curr[0], index = curr[1];
            // 判断 当前节点是否为最后一个节点
            if (index == n - 1) {
                return ops;
            }

            // 先处理特殊方式
            for (int j : map.getOrDefault(arr[index], new ArrayList<>())) {
                if (dp[j] > ops + 1) {
                    dp[j] = ops + 1;
                    queue.offer(new int[]{dp[j], j});
                }
            }
            // 将 map 删除 arr[i]
            map.remove(arr[index]);
            // 处理一般方式
            if (index - 1 >= 0 && dp[index - 1] > ops + 1) {
                dp[index - 1] = ops + 1;
                queue.offer(new int[]{dp[index - 1], index - 1});
            }
            if (index + 1 < n && dp[index + 1] > ops + 1) {
                dp[index + 1] = ops + 1;
                queue.offer(new int[]{dp[index + 1], index + 1});
            }
        }

        return 0;
    }

}
