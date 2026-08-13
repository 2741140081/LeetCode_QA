package com.marks.leetcode.array;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1187 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/17 15:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1187 {

    /**
     * @Description:
     * 给你两个整数数组 arr1 和 arr2，返回使 arr1 严格递增所需要的最小「操作」数（可能为 0）。
     * 每一步「操作」中，你可以分别从 arr1 和 arr2 中各选出一个索引，
     * 分别为 i 和 j，0 <= i < arr1.length 和 0 <= j < arr2.length，
     * 然后进行赋值运算 arr1[i] = arr2[j]。
     * 如果无法让 arr1 严格递增，请返回 -1。
     *
     * tips:
     * 1 <= arr1.length, arr2.length <= 2000
     * 0 <= arr1[i], arr2[i] <= 10^9
     * @param: arr1
     * @param: arr2
     * @return int
     * @author marks
     * @CreateDate: 2026/07/17 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        int result;
        result = method_01(arr1, arr2);
        return result;
    }

    /**
     * @Description:
     * 1. arr2 中相同的值是没有意义的, 需要删除重复值, 然后进行排序操作。
     * 2. arr1[i] <= arr1[i - 1], 此时无法构成严格递增要求, 有两种解决方法,
     * 2.1 从 arr2 中找到一个最小的 j, 并且 arr2[j] > arr1[j - 1], 然后赋值给 arr1[i] = arr2[j], ans++;
     * 2.2 保留 arr1[i] 不变, 修改 0 ~ i - 1, 使得满足严格递增要求, 也就是需要对于 [0 ~ i - 1] 需要从 arr2 中查找 j,
     * 满足 arr2[j] < arr1[i]
     * 3. 假设当前处理 i, dp[i - 1] = k,
     * @param: arr1
     * @param: arr2
     * @return int
     * @author marks
     * @CreateDate: 2026/07/17 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr1, int[] arr2) {
        int n = arr1.length;
        Set<Integer> set = new HashSet<>();
        for (int num : arr2) {
            set.add(num);
        }
        List<Integer> list = new ArrayList<>(set);
        int m = list.size();
        list.sort(Comparator.comparingInt(o -> o));

        int maxChange = Math.min(n, m);
        // 定义 dp[][], dp[i][j] = v 定义为: 前 i 个元素, 执行 j 次交换后, 结尾元素的最小值 v.
        int[][] dp = new int[n + 1][maxChange + 1];
        // 初始化
        int INF = Integer.MAX_VALUE / 2;
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = -1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= Math.min(i, m); j++) {
                // 保留当前 arr1[i - 1]
                if (dp[i - 1][j] < arr1[i - 1]) {
                    dp[i][j] = arr1[i - 1];
                }
                // 替换当前 arr1[i - 1]
                if (j > 0 && dp[i - 1][j - 1] != INF) {
                    int idx = binarySearchLimitLeft(list, j - 1, dp[i - 1][j - 1]);
                    if (idx != m) {
                        // 找到值可以进行替换操作
                        dp[i][j] = Math.min(dp[i][j], list.get(idx));
                    }
                }
                if (i == n && dp[i][j] != INF) {
                    return j;
                }
            }
        }

        return -1;
    }

    /**
     * @Description: [方法描述]
     * @param: list
     * @param: low
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/07/20 11:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int binarySearchLimitLeft(List<Integer> list, int low, int target) {
        int high = list.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid) > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

}
