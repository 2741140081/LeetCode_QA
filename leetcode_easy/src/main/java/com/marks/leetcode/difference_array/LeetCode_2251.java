package com.marks.leetcode.difference_array;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2251 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/10 11:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2251 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的二维整数数组 flowers ，
     * 其中 flowers[i] = [starti, endi] 表示第 i 朵花的 花期 从 starti 到 endi （都 包含）。
     * 同时给你一个下标从 0 开始大小为 n 的整数数组 people ，people[i] 是第 i 个人来看花的时间。
     * 请你返回一个大小为 n 的整数数组 answer ，其中 answer[i]是第 i 个人到达时在花期内花的 数目
     * tips:
     * 1 <= flowers.length <= 5 * 10^4
     * flowers[i].length == 2
     * 1 <= starti <= endi <= 10^9
     * 1 <= people.length <= 5 * 10^4
     * 1 <= people[i] <= 10^9
     * @param: flowers
     * @param: people
     * @return int[]
     * @author marks
     * @CreateDate: 2026/06/10 11:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] fullBloomFlowers(int[][] flowers, int[] people) {
        int[] result;
        result = method_01(flowers, people);
        return result;
    }

    /**
     * @Description:
     * 1. 使用差分数组得到时间与花的数量关系, 但是由于时间过大, 如果按常规类创建 int[] 数组必定导致空间过大, 内存溢出错误发生, 不可取
     * 2. 需要一个有序的时间序列, 并且存储每一个时间点的花相对数量(相当于差分数组), 使用 TreeMap
     * 3. 由于 people 数组不是一个有序数组, 但是返回的结果需要下标元素, 所以需要通过构建间接索引的对 people 数组进行排序
     * @param: flowers
     * @param: people
     * @return int[]
     * @author marks
     * @CreateDate: 2026/06/10 11:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] flowers, int[] people) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int[] flower : flowers) {
            int start = flower[0];
            int end = flower[1];
            treeMap.put(start, treeMap.getOrDefault(start, 0) + 1);
            treeMap.put(end + 1, treeMap.getOrDefault(end + 1, 0) - 1);
        }
        int n = people.length;
        Integer[] index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        Arrays.sort(index, Comparator.comparingInt(i -> people[i]));
        int[] ans = new int[n];
        int cnt = 0; // 初始的花的数量
        // 遍历 index
        for (int i = 0; i < n; i++) {
            int j = index[i];
            while (!treeMap.isEmpty() && treeMap.firstKey() <= people[j]) {
                cnt += treeMap.pollFirstEntry().getValue();
            }
            ans[j] = cnt;
        }

        return ans;
    }

}
