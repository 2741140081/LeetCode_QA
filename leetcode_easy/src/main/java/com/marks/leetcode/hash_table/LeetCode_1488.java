package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1488 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/22 11:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1488 {

    /**
     * @Description:
     * 你的国家有 10^9 个湖泊，所有湖泊一开始都是空的。当第 n 个湖泊下雨前是空的，那么它就会装满水。
     * 如果第 n 个湖泊下雨前是 满的 ，这个湖泊会发生 洪水 。你的目标是避免任意一个湖泊发生洪水。
     * 给你一个整数数组 rains ，其中：
     * rains[i] > 0 表示第 i 天时，第 rains[i] 个湖泊会下雨。
     * rains[i] == 0 表示第 i 天没有湖泊会下雨，你 必须 选择 一个 湖泊并 抽干 这个湖泊的水。
     * 请返回一个数组 ans ，满足：
     * ans.length == rains.length
     * 如果 rains[i] > 0 ，那么ans[i] == -1 。
     * 如果 rains[i] == 0 ，ans[i] 是你第 i 天选择抽干的湖泊。
     * 如果有多种可行解，请返回它们中的 任意一个 。如果没办法阻止洪水，请返回一个 空的数组 。
     * 请注意，如果你选择抽干一个装满水的湖泊，它会变成一个空的湖泊。但如果你选择抽干一个空的湖泊，那么将无事发生。
     *
     * tips:
     * 1 <= rains.length <= 10^5
     * 0 <= rains[i] <= 10^9
     * @param: rains
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/22 11:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] avoidFlood(int[] rains) {
        int[] result;
        result = method_01(rains);
        return result;
    }

    /**
     * @Description:
     * 1. 使用 map 记录湖泊的水量, 0 表示湖泊是空的, 1 表示湖泊是满的, 2 表示湖泊溢出, 发生洪水
     * 2. 使用一个队列存储 rains 中为 0 的索引, 当 遇到 洪水时, 将队列中的索引取出来, 修改对应的ans[idx] = rains[j], 并且将 map 中的值修改为 1
     * 3. 找到问题所在了, 由于队列中存储的是预计抽水的湖泊, 但是在第 i 天时, 该湖泊是干的, 即抽水是无效的, 但是后续两天都在次湖泊下雨, 导致提取一天抽了 湖泊的水.
     * Eg: rains ={0,1,1}
     * 4. 重新思考, 需要添加一个判断, 即抽水湖泊的 下标值需要小于 idx, 这样才能执行抽水操作
     * AC: 230ms/148.79MB
     * @param: rains
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/22 11:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] rains) {
        int n = rains.length;
        Map<Integer, Integer> map = new HashMap<>(); // 存储湖泊 id 和 index 索引
        // 创建list 存储索引
        List<Integer> list = new ArrayList<>();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int r = rains[i];
            if (r > 0) {
                // 第 r 个湖泊会下雨, 需要判断 map 中的情况
                if (map.containsKey(r)) {
                    int target = map.get(r); // 目标湖泊的索引值, 需要找到最小的 list.get(j) > idx;
                    if (!list.isEmpty()) {
                        // 通过二分查找法, 找到 list 中 最小的 j, 使得 list.get(j) > idx
                        int j = binarySearch(list, target);
                        // 如果 j 不存在, 则返回空数组
                        if (j < 0) {
                            return new int[0];
                        }
                        ans[list.get(j)] = r;
                        list.remove(j);
                        // 更新 map
                        map.put(r, i);
                    } else {
                        // 返回空数组, 因为发洪水了
                        return new int[0];
                    }
                } else {
                    map.put(r, i);
                }
                ans[i] = -1;
            } else {
                // 当前不会下雨, 可以对任意湖泊抽干
                list.add(i);
            }
        }
        // 处理栈中剩余的索引
        for (Integer i : list) {
            ans[i] = 1;
        }

        return ans;
    }

    private int binarySearch(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        int index = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) > target) {
                index = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

}
