package com.marks.leetcode.graph_theory_algorithm.dfs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/18 9:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_841 {
    private Deque<Integer> stack = new ArrayDeque<>();
    private int[] ans;
    /**
     * @Description: [
     * 有 n 个房间，房间按从 0 到 n - 1 编号。最初，除 0 号房间外的其余所有房间都被锁住。你的目标是进入所有的房间。
     * 然而，你不能在没有获得钥匙的时候进入锁住的房间。
     * 当你进入一个房间，你可能会在里面找到一套 不同的钥匙，每把钥匙上都有对应的房间号，即表示钥匙可以打开的房间。你可以拿上所有钥匙去解锁其他房间。
     * 给你一个数组 rooms 其中 rooms[i] 是你进入 i 号房间可以获得的钥匙集合。如果能进入 所有 房间返回 true，否则返回 false。
     * ]
     * @param rooms
     * @return boolean
     * @author marks
     * @CreateDate: 2024/12/18 9:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        boolean result;
        result = method_01(rooms);
        return result;
    }

    /**
     * @Description: [
     * 感觉和 LC_797 是一样的类型,
     * 但是有一个区别是所求结果不同, 本题要求进入所有房间,
     * AC:1ms/43.52MB
     * ]
     * @param rooms
     * @return boolean
     * @author marks
     * @CreateDate: 2024/12/18 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(List<List<Integer>> rooms) {
        int n = rooms.size();
        ans = new int[n];
        stack.offerLast(0);
        DFSMaxArea(0, rooms);

        for (int i = 0; i < ans.length; i++) {
            if (ans[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private void DFSMaxArea(int i, List<List<Integer>> rooms) {
        if (ans[i] == 1) {
            // 是否已经拿过钥匙了或者说是否重复进入这个房间
            return;
        }
        ans[i] = 1; // 访问标记
        for (Integer num : rooms.get(i)) {
            stack.offerLast(num);
            DFSMaxArea(num, rooms);
            stack.pollLast();
        }
    }
}
