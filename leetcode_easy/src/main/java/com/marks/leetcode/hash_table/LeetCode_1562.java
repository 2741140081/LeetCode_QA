package com.marks.leetcode.hash_table;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1562 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/2 14:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1562 {

    /**
     * @Description:
     * 给你一个数组 arr ，该数组表示一个从 1 到 n 的数字排列。有一个长度为 n 的二进制字符串，该字符串上的所有位最初都设置为 0 。
     * 在从 1 到 n 的每个步骤 i 中（假设二进制字符串和 arr 都是从 1 开始索引的情况下），二进制字符串上位于位置 arr[i] 的位将会设为 1 。
     * 给你一个整数 m ，请你找出二进制字符串上存在长度为 m 的一组 1 的最后步骤。一组 1 是一个连续的、由 1 组成的子串，且左右两边不再有可以延伸的 1 。
     * 返回存在长度 恰好 为 m 的 一组 1  的最后步骤。如果不存在这样的步骤，请返回 -1 。
     *
     * tips:
     * n == arr.length
     * 1 <= n <= 10^5
     * 1 <= arr[i] <= n
     * arr 中的所有整数 互不相同
     * 1 <= m <= arr.length
     * @param: arr
     * @param: m
     * @return int
     * @author marks
     * @CreateDate: 2026/06/02 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findLatestStep(int[] arr, int m) {
        int result;
        result = method_01(arr, m);
        return result;
    }

    /**
     * @Description:
     * 1. 如果执行完成所有步骤后, 得到的是一个二进制字符串, 长度为 n, 所有字符均为 1
     * 2. 需要找到最后一个长度为 m 的子字符串, 使用倒序的处理方式
     * 3. 有一个[0, ~ n - 1] 的全为1的二进制字符串, 处理 arr[n - 1] 时, 使用list<int[]> 存储 [left, right] 分别表示左右边界
     * 4. right - left + 1 < m 时, 这个集合可以舍弃.
     * 5. 当处理 arr[i] - 1 时, 从 list 中通过二分查找, 找到 left <= arr[i] - 1 <= right 这个区间, 然后根据 arr[i] - 1 将区间 分割为
     * [left, arr[i] - 2], [arr[i], right] 两个区间, 并且分别判断两个区间 size < m, 如果小于 m ， 则舍弃, 如果等于 m, return i;
     * AC: 15ms/86.7MB
     * @param: arr
     * @param: m
     * @return int
     * @author marks
     * @CreateDate: 2026/06/02 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int m) {
        int n = arr.length;
        if (m == n) {
            return n;
        }
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{0, n - 1});
        // 倒序处理 arr
        for (int i = n - 1; i >= 0; i--) {
            int pos = arr[i] - 1; // 由于 index 从 1 开始, 所以需要减去1
            // 通过二分查找法, 找到 list.get(j), 要求 left_j <= pos <= right_j, 如果没有找到 j, 返回 -1, 即不处理
            int j = binarySearch(list, pos);
            if (j != -1) {
                int currLeft = list.get(j)[0];
                int currRight = list.get(j)[1];
                // 需要构建两个 新的区间, 并且添加到 list 中, 修改原有区间, 并且添加新区间
                int prevRight = pos - 1;
                int nextLeft = pos + 1;
                // 修改旧区间
                list.set(j, new int[]{currLeft, prevRight});
                // 添加新区间到 j + 1 的位置
                list.add(j + 1, new int[]{nextLeft, currRight});
                // 判断 新区间和旧区间是否合法
                int result = checkList(list, j + 1, m);

                if (result == 0) {
                    return i;
                } else if (result < 0) {
                    // remove j + 1
                    list.remove(j + 1);
                }
                result = checkList(list, j, m);
                if (result == 0) {
                    return i;
                } else if (result < 0) {
                    // remove j
                    list.remove(j);
                }
            }
        }
        return -1;
    }

    private int checkList(List<int[]> list, int j, int m) {
        // size == m, return 0, < m, return -1, > m return 1
        int[] arr = list.get(j);
        int size = arr[1] - arr[0] + 1;
        return Integer.compare(size, m);
    }

    private int binarySearch(List<int[]> list, int pos) {
        int left = 0, right = list.size() - 1;
        int ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid)[0] <= pos && pos <= list.get(mid)[1]) {
                // 由于区间是不重复的, 所以 pos 只会存在一个区间内
                ans = mid;
                break;
            } else if (list.get(mid)[0] > pos) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

}
