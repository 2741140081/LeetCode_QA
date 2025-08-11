package com.marks.leetcode.binary_tree;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/29 17:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_582 {
    
    /**
     * @Description:
     * 系统中存在 n 个进程，形成一个有根树结构。给你两个整数数组 pid 和 ppid ，其中 pid[i] 是第 i 个进程的 ID ，ppid[i] 是第 i 个进程的父进程 ID 。
     *
     * 每一个进程只有 一个父进程 ，但是可能会有 一个或者多个子进程 。只有一个进程的 ppid[i] = 0 ，意味着这个进程 没有父进程 。
     *
     * 当一个进程 被杀掉 的时候，它所有的子进程和后代进程都要被杀掉。
     *
     * 给你一个整数 kill 表示要杀掉进程的 ID ，返回杀掉该进程后的所有进程 ID 的列表。可以按 任意顺序 返回答案。
     *
     * @param pid 
     * @param ppid 
     * @param kill
     * @return int[]
     * @author marks
     * @CreateDate: 2025/7/29 17:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] killProcess(int[] pid, int[] ppid, int kill) {
        int[] result;
        result = method_01(pid, ppid, kill);
        return result;
    }

    /**
     * @Description:
     * 示例 1:
     * 输入:
     * pid =  [1, 3, 10, 5]
     * ppid = [3, 0, 5, 3]
     * kill = 5
     * 输出: [5,10]
     *
     * @param pid 
     * @param ppid 
     * @param kill 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/7/29 17:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] pid, int[] ppid, int kill) {
        int n = pid.length;
        int max = Math.max(Arrays.stream(ppid).max().getAsInt(), Arrays.stream(pid).max().getAsInt());
        List<Integer>[] lists = new List[max + 1];

        for (int i = 0; i < lists.length; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            lists[ppid[i]].add(pid[i]);
        }
        List<Integer> ans = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(kill);
        ans.add(kill);
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next : lists[curr]) {
                ans.add(next);
                queue.add(next);
            }
        }
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }
}
