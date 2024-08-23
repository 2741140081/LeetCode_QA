package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * <p>项目名称: 俄罗斯套娃信封问题, 二维数组LIS </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/23 9:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_354 {
    /**
     * @Description: [
     * 给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
     *
     * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
     *
     * 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
     *
     * 注意：不允许旋转信封。
     *
     * tips:
     * 1 <= envelopes.length <= 10^5
     * envelopes[i].length == 2
     * 1 <= wi, hi <= 10^5
     * ]
     * @param envelopes
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 9:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxEnvelopes(int[][] envelopes) {
        int result = 0;
//        result = method_01(envelopes);
        result = method_02(envelopes);
        return result;
    }

    private int method_02(int[][] envelopes) {
        int n = envelopes.length;
        if (n == 0) {
            return 0;
        }
        // Arrays.sort(envelopes, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]); //相当于下面，更简洁
        Arrays.sort(envelopes, (o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            }else {
                return o2[1] - o1[1];
            }
        });
        List<Integer> list = new ArrayList<>();
        list.add(envelopes[0][1]);
        for (int i = 1; i < n; i++) {
            int num = envelopes[i][1];
            if (num > list.get(list.size() - 1)) {
                list.add(num);
            }else {
                int index = binarySearch(list, num);
                list.set(index, num);
            }
        }
        return list.size();
    }

    private int binarySearch(List<Integer> list, int target) {
        // 二分法查找插入位置
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (list.get(mid) < target) {
                left = mid + 1;
            }else {
                right = mid;
            }
        }
        return left;
    }

    /**
     * @Description: [
     * envelopes = [[5,4],[6,4],[6,7],[2,3]]
     * 排序，后LIS
     * 但是提交结果超时！！！！！
     * 改成贪心+二分法来试试, 开始method_02
     * ]
     * @param envelopes
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 9:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] envelopes) {
        int n = envelopes.length;
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                //首先我们将所有的信封按照 w 值第一关键字升序、h 值第二关键字降序进行排序；
                if (o1[0] != o2[0]) {
                    return o1[0] - o2[0];
                }else {
                    return o2[1] - o1[1];
                }
            }
        });
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                // 随后我们就可以忽略 w 维度，求出 h 维度的最长严格递增子序列，其长度即为答案。
                if (envelopes[i][1] > envelopes[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
