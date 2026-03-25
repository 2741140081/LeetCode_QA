package com.marks.leetcode.DP;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1871 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/25 16:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1871 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的二进制字符串 s 和两个整数 minJump 和 maxJump 。
     * 一开始，你在下标 0 处，且该位置的值一定为 '0' 。当同时满足如下条件时，你可以从下标 i 移动到下标 j 处：
     *
     * i + minJump <= j <= min(i + maxJump, s.length - 1) 且
     * s[j] == '0'.
     * 如果你可以到达 s 的下标 s.length - 1 处，请你返回 true ，否则返回 false 。
     *
     * tips:
     * 2 <= s.length <= 10^5
     * s[i] 要么是 '0' ，要么是 '1'
     * s[0] == '0'
     * 1 <= minJump <= maxJump < s.length
     * @param: s
     * @param: minJump
     * @param: maxJump
     * @return boolean
     * @author marks
     * @CreateDate: 2026/03/25 16:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canReach(String s, int minJump, int maxJump) {
        boolean result;
//        result = method_01(s, minJump, maxJump);
//        result = method_02(s, minJump, maxJump);
        result = method_03(s, minJump, maxJump);
        return result;
    }

    /**
     * @Description:
     * 1. 查看题解, 使用了前缀和优化范围查询, 有效降低时间复杂度
     * AC: 10ms/46.88MB
     * @param: s
     * @param: minJump
     * @param: maxJump
     * @return boolean
     * @author marks
     * @CreateDate: 2026/03/25 16:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_03(String s, int minJump, int maxJump) {
        int n = s.length();
        char[] array = s.toCharArray();
        // 判断n - 1处下标是否为0
        if (array[n - 1] == '1') {
            return false;
        }
        boolean[] dp = new boolean[n];
        dp[0] = true;
        // 前缀和
        int[] prefix = new int[n];
        // 初始化
        for (int i = 0; i < minJump; i++) {
            prefix[i] = 1;
        }
        for (int i = minJump; i < n; i++) {
            int left = i - maxJump, right = i - minJump;
            if (array[i] == '0') {
                // 当前点可以跳跃
                int count = prefix[right] - (left <= 0 ? 0 : prefix[left - 1]);
                dp[i] = count > 0;
            }
            prefix[i] = prefix[i - 1] + (dp[i] ? 1 : 0);
        }

        return dp[n - 1];
    }

    /**
     * @Description:
     * 1. 看下dp 怎么处理
     * AC: 2498ms/46.65MB
     * 2. 这个时间复杂度太高了, 基本相当于超时
     * 3. 需要使用前缀和优化处理 范围, 可以在 O(1) 的时间复杂度判断范围[Math.max(i - maxJump,0), i - minJump] 中是否包含1
     * @param: s
     * @param: minJump
     * @param: maxJump
     * @return boolean
     * @author marks
     * @CreateDate: 2026/03/25 16:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_02(String s, int minJump, int maxJump) {
        int n = s.length();
        char[] array = s.toCharArray();
        // 判断n - 1处下标是否为0
        if (array[n - 1] == '1') {
            return false;
        }
        boolean[] dp = new boolean[n];
        dp[0] = true;
        for (int i = minJump; i < n; i++) {
            if (array[i] == '1') {
                continue;
            }
            // 处理0的情况, 当前坐标是i, 可以从什么位置跳到当前坐标?[Math.max(i - maxJump,0), i - minJump]
            for (int j = (i - minJump); j >= Math.max(i - maxJump, 0); j--) {
                if (dp[j]) {
                    dp[i] = dp[j];
                    break;
                }
            }
        }

        return dp[n - 1];
    }

    /**
     * @Description:
     * 1. 判断可达性
     * 2. 用队列先试试看
     * 3. 时间复杂度是 O(n^2)
     * 超时: 108/143
     * @param: s
     * @param: minJump
     * @param: maxJump
     * @return boolean
     * @author marks
     * @CreateDate: 2026/03/25 16:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s, int minJump, int maxJump) {
        int n = s.length();
        char[] array = s.toCharArray();
        // 判断n - 1处下标是否为0
        if (array[n - 1] == '1') {
            return false;
        }
        boolean[] dp = new boolean[n];
        // 创建队列
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        dp[0] = true;
        while (!queue.isEmpty()) {
            // 取出队首元素
            int curr = queue.poll();
            // 最佳和最远下标
            int l = curr + minJump;
            int r = Math.min(curr + maxJump, n - 1);
            for (int i = l; i <= r; i++) {
                if (array[i] == '0' && !dp[i]) {
                    queue.offer(i);
                    dp[i] = true;
                }
            }
        }

        return dp[n - 1];
    }

}
