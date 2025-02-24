package com.marks.leetcode.data_structure.difference;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/21 10:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2251 {
    /**
     * @Description:
     * 给你一个下标从 0 开始的二维整数数组 flowers ，其中 flowers[i] = [start_i, end_i] 表示第 i 朵花的 花期 从 starti 到 endi （都 包含）。
     * 同时给你一个下标从 0 开始大小为 n 的整数数组 people ，people[i] 是第 i 个人来看花的时间。
     *
     * 请你返回一个大小为 n 的整数数组 answer ，其中 answer[i]是第 i 个人到达时在花期内花的 数目 。
     *
     * tips:
     * 1 <= flowers.length <= 5 * 10^4
     * flowers[i].length == 2
     * 1 <= starti <= endi <= 10^9
     * 1 <= people.length <= 5 * 10^4
     * 1 <= people[i] <= 10^9
     * @param flowers 
     * @param people
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/21 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] fullBloomFlowers(int[][] flowers, int[] people) {
        int[] result;
//        result = method_01(flowers, people);
        result = method_02(flowers, people);
        return result;
    }
    
    /**
     * @Description:
     * AC: 79ms/68.75MB
     * @param flowers 
     * @param people
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/21 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[][] flowers, int[] people) {
        int n = flowers.length;
        TreeMap<Integer, Integer> diff = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            int start = flowers[i][0], end = flowers[i][1] + 1;
            diff.merge(start, 1, Integer::sum);
            diff.merge(end, -1, Integer::sum);
        }

        int m = people.length;
        Integer[] indices = IntStream.range(0, m).boxed().toArray(Integer[]::new);
        Arrays.sort(indices, Comparator.comparingInt(i -> people[i]));
        int[] ans = new int[m];
        int curr = 0;
        for (int x : indices) {
            while (!diff.isEmpty() && diff.firstKey() <= people[x]) {
                curr += diff.pollFirstEntry().getValue();
            }
            ans[x] = curr;
        }
        return ans;

    }

    /**
     * @Description:
     * 直接差分好像就够了, 看看数据范围
     * memory error (超出内存限制)
     * 具体地，由于本题中花开时间的取值范围为0 <= start_i <= end_i <= 10^9，
     * 这就决定了我们无法直接使用数组来计算前缀和，但可以将时间点进行离散化，利用有序集合来记录端点的变化量即可
     * @param flowers
     * @param people
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/21 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] flowers, int[] people) {
        int maxValue = 0;
        int n = flowers.length;
        int m = people.length;
        for (int i = 0; i < n; i++) {
            maxValue = Math.max(maxValue, flowers[i][1]);
        }

        int[] diff = new int[maxValue + 2];
        for (int i = 0; i < n; i++) {
            int start = flowers[i][0], end = flowers[i][1];
            diff[start]++;
            diff[end + 1]--;
        }

        int count = 0;
        int[] pre = new int[diff.length];
        for (int i = 0; i < diff.length; i++) {
            count += diff[i];
            pre[i] = count;
        }

        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            if (people[i] > maxValue) {
                ans[i] = 0;
            } else {
                ans[i] = pre[people[i]];
            }
        }
        return ans;
    }
}
