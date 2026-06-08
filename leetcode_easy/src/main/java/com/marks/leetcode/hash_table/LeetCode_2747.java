package com.marks.leetcode.hash_table;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2747 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/8 11:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2747 {

    /**
     * @Description:
     * 给你一个整数 n ，表示服务器的总数目，再给你一个下标从 0 开始的 二维 整数数组 logs ，
     * 其中 logs[i] = [server_id, time] 表示 id 为 server_id 的服务器在 time 时收到了一个请求。
     * 同时给你一个整数 x 和一个下标从 0 开始的整数数组 queries  。
     * 请你返回一个长度等于 queries.length 的数组 arr ，其中 arr[i] 表示在时间区间 [queries[i] - x, queries[i]] 内没有收到请求的服务器数目。
     * 注意时间区间是个闭区间。
     *
     * tips:
     * 1 <= n <= 10^5
     * 1 <= logs.length <= 10^5
     * 1 <= queries.length <= 10^5
     * logs[i].length == 2
     * 1 <= logs[i][0] <= n
     * 1 <= logs[i][1] <= 10^6
     * 1 <= x <= 10^5
     * x < queries[i] <= 10^6
     * @param: n
     * @param: logs
     * @param: x
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/06/08 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        int[] result;
        result = method_01(n, logs, x, queries);
        return result;
    }

    /**
     * @Description:
     * 1. x 相当于是一个时间的窗口, 需要对 logs 进行排序, 按照 time 升序排序
     * 2. 使用 map 存储 id 及其 请求次数, 使用双指针分别指向区间的两个端点.
     * 3. 由于 queries 没有明确说明是有序的, 默认是无序的, 需要通过间接排序的方式, 进行升序排序.
     * AC: 58ms/142.06MB
     * @param: n
     * @param: logs
     * @param: x
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/06/08 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[][] logs, int x, int[] queries) {
        // 对 logs 进行排序, time 升序排序
        Arrays.sort(logs, Comparator.comparingInt(a -> a[1]));
        int m = queries.length;
        // 创建一个索引数组, 用于记录 queries 的索引
        Integer[] index = new Integer[m];
        for (int i = 0; i < m; i++) {
            index[i] = i;
        }
        Arrays.sort(index, Comparator.comparingInt(a -> queries[a]));
        int[] ans = new int[m];
        Map<Integer, Integer> map = new HashMap<>();
        int right = 0, left = 0;
        for (int i = 0; i < m; i++) {
            int j = index[i];
            int end = queries[j];
            int start = end - x;
            while (right < logs.length && logs[right][1] <= end) {
                // 窗口移动
                int id = logs[right][0];
                map.merge(id, 1, Integer::sum);
                right++;
            }
            while (left < right && logs[left][1] < start) {
                // 删除过期的数据
                int id = logs[left][0];
                map.merge(id, -1, Integer::sum);
                if (map.get(id) == 0) {
                    map.remove(id);
                }
                left++;
            }
            ans[j] = n - map.size();
        }
        return ans;
    }

}
