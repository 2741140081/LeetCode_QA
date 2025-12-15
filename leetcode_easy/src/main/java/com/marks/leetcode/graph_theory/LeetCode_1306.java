package com.marks.leetcode.graph_theory;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1306 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/15 11:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1306 {

    /**
     * @Description:
     * 这里有一个非负整数数组 arr，你最开始位于该数组的起始下标 start 处。
     * 当你位于下标 i 处时，你可以跳到 i + arr[i] 或者 i - arr[i]。
     *
     * 请你判断自己是否能够跳到对应元素值为 0 的 任一 下标处。
     *
     * 注意，不管是什么情况下，你都无法跳到数组之外。
     * @param: arr
     * @param: start
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/15 11:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canReach(int[] arr, int start) {
        boolean result;
        result = method_01(arr, start);
        return result;
    }

    /**
     * @Description:
     * 输入：arr = [4,2,3,0,3,1,2], start = 5
     * 输出：true
     * 1. 判断数组中是否存在值为0的元素, 不存在返回false
     * 2. 创建一个Set<Integer> set, 存储已经访问过的元素, 避免重复访问
     * 3. 使用动态规划, 创建一个boolean[] dp, dp[i] 表示从i位置开始, 是否可以跳到值为0的元素, dp[i] = true 表示可以跳到值为0的元素
     * 4. 理解有误, i + arr[i] 和 i - arr[i] 的含义是, 只能跳到这两个点上面, 如果超出界限, 则不能跳
     * AC: 2ms/47.8MB
     * @param: arr
     * @param: start
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/15 11:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] arr, int start) {
        int n = arr.length;
        boolean flag = false;
        for (int j : arr) {
            if (j == 0) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return false;
        }
        // 动态规划
        boolean[] dp = new boolean[n];
        dp[start] = true;
        // 创建一个队列, 存储待访问的元素
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (arr[curr] == 0) {
                return true;
            }
            int right = curr + arr[curr];
            int left = curr - arr[curr];
            // 添加待访问的元素
            if (right < n && !dp[right]) {
                queue.offer(right);
                dp[right] = true;
            }
            if (left >= 0 && !dp[left]) {
                queue.offer(left);
                dp[left] = true;
            }
        }
        return false;
    }

}
