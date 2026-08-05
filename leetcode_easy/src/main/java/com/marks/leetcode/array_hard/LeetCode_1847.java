package com.marks.leetcode.array_hard;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1847 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/4 10:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1847 {

    /**
     * @Description:
     * 一个酒店里有 n 个房间，这些房间用二维整数数组 rooms 表示，
     * 其中 rooms[i] = [roomIdi, sizei] 表示有一个房间号为 roomIdi 的房间且它的面积为 sizei 。
     * 每一个房间号 roomIdi 保证是 独一无二 的。
     * 同时给你 k 个查询，用二维数组 queries 表示，其中 queries[j] = [preferredj, minSizej] 。
     * 第 j 个查询的答案是满足如下条件的房间 id ：
     * 房间的面积 至少 为 minSizej ，且
     * abs(id - preferredj) 的值 最小 ，其中 abs(x) 是 x 的绝对值。
     * 如果差的绝对值有 相等 的，选择 最小 的 id 。如果 没有满足条件的房间 ，答案为 -1 。
     * 请你返回长度为 k 的数组 answer ，其中 answer[j] 为第 j 个查询的结果。
     *
     * tips:
     * n == rooms.length
     * 1 <= n <= 10^5
     * k == queries.length
     * 1 <= k <= 10^4
     * 1 <= roomIdi, preferredj <= 10^7
     * 1 <= sizei, minSizej <= 10^7
     * @param: rooms
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/04 10:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] closestRoom(int[][] rooms, int[][] queries) {
        int[] result;
        result = method_01(rooms, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 先对 rooms 按照 room size 进行降序排序, 然后对 queries 进行间接排序, 按照minSize 大小降序
     * 2. 使用有序集合 TreeSet 存储符合要求 roomId 值, 符合要求: currRoomSize < minSize
     * AC: 90ms/171.8MB
     * @param: rooms
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/04 10:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] rooms, int[][] queries) {
        int n = rooms.length;
        // 对 rooms 按照 size 进行降序排序
        Arrays.sort(rooms, (a, b) -> b[1] - a[1]);
        // 创建间接排序索引数组
        int k = queries.length;
        Integer[] index = new Integer[k];
        // 初始化间接索引
        for (int i = 0; i < k; i++) {
            index[i] = i;
        }
        // 对间接索引降序排序, 根据 queries 中的 minSize
        Arrays.sort(index, (a, b) -> queries[b][1] - queries[a][1]);
        // 创建结果数组
        int[] ans = new int[k];
        // 创建有序集合存储符合要求的 roomId 值
        TreeSet<Integer> validRoomId = new TreeSet<>();
        // 遍历索引数组
        int left = 0; // rooms 中待添加的索引
        for (int i = 0; i < k; i++) {
            int idx = index[i]; // 第 i 个查询
            int minSize = queries[idx][1];
            int targetPreferId = queries[idx][0];
            // 添加符合要求的roomId 到 treeSet 中
            while (left < n && rooms[left][1] >= minSize) {
                validRoomId.add(rooms[left][0]);
                left++;
            }
            // 如果有序集合为空, 此时结果为 -1
            if (validRoomId.isEmpty()) {
                ans[idx] = -1;
                continue;
            }

            // 在有序集合中找到第一个小于等于 targetPreferId 的 roomMinId
            Integer roomMinId = validRoomId.floor(targetPreferId);
            // 找到第一个大于等于 targetPreferId 的 roomMaxId
            Integer roomMaxId = validRoomId.ceiling(targetPreferId);
            // 更新 ans[idx] 为 Math.min(abs)
            if (roomMinId == null) {
                ans[idx] = roomMaxId;
            } else if (roomMaxId == null) {
                ans[idx] = roomMinId;
            } else {
                ans[idx] = Math.abs(roomMinId - targetPreferId) <= Math.abs(roomMaxId - targetPreferId) ? roomMinId : roomMaxId;
            }
        }

        return ans;
    }

}
