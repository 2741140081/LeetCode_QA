package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/7 11:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2561 {

    /**
     * @Description:
     * 你有两个果篮，每个果篮中有 n 个水果。给你两个下标从 0 开始的整数数组 basket1 和 basket2 ，用以表示两个果篮中每个水果的交换成本。
     * 你想要让两个果篮相等。为此，可以根据需要多次执行下述操作：
     *
     * 选中两个下标 i 和 j ，并交换 basket1 中的第 i 个水果和 basket2 中的第 j 个水果。
     * 交换的成本是 min(basket1i,basket2j) 。
     * 根据果篮中水果的成本进行排序，如果排序后结果完全相同，则认为两个果篮相等。
     *
     * 返回使两个果篮相等的最小交换成本，如果无法使两个果篮相等，则返回 -1 。
     * @param basket1
     * @param basket2
     * @return long
     * @author marks
     * @CreateDate: 2025/4/7 11:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minCost(int[] basket1, int[] basket2) {
        long result;
        result = method_01(basket1, basket2);
        return result;
    }

    /**
     * @Description:
     * 输入：basket1 = [4,2,2,2], basket2 = [1,4,1,2]
     * 输出：1
     * 解释：交换 basket1 中下标为 1 的水果和 basket2 中下标为 0 的水果，交换的成本为 1 。
     * 此时，basket1 = [4,1,2,2] 且 basket2 = [2,4,1,2] 。重排两个数组，发现二者相等。
     *
     * 1. 如果需要交换 b1[i1], b2[j1], b1[i2], b2[j2]
     * Math.min(b1[i1], b2[j1]) = v1
     * Math.min(b1[i], b2[i]) * 2 = minValue * 2 = min
     * Math.min(min, v1)
     *
     * 2. 先判断 -1 的情况: 具体是将所有元素进行计数, 如果存在 count % 2 == 1, return -1;
     *
     * 3. 两种交换方式, a. 直接交换 Math.min(a, b); b. 通过最小值 min 进行两次交换, a - min, min - b, 2 * min
     *
     * 超时！, 感觉还是需要先处理那些相同的
     * need todo
     * @param basket1
     * @param basket2
     * @return long
     * @author marks
     * @CreateDate: 2025/4/7 11:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] basket1, int[] basket2) {
        int n = basket1.length;
        Map<Integer, Integer> count = new HashMap<>();
        Map<Integer, Integer> bMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            count.merge(basket1[i], 1, Integer::sum);
            count.merge(basket2[i], 1, Integer::sum);

            bMap.merge(basket1[i], 1, Integer::sum);
        }

        int size = count.size(), index = 0;
        int[][] bm = new int[size][2];

        int min = Integer.MAX_VALUE;
        Map<Integer, Integer> strand = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            bm[index++][0] = entry.getKey();
            min = Math.min(min, entry.getKey() * 2);
            strand.put(entry.getKey(), entry.getValue() / 2);
            if (entry.getValue() % 2 == 1) {
                return -1;
            }
        }

        for (int i = 0; i < size; i++) {
            if (bMap.containsKey(bm[i][0])) {
                bm[i][1] = bMap.get(bm[i][0]);
            }
        }

        Arrays.sort(bm, (o1, o2) -> o2[0] - o1[0]);

        int left = 0, less = size - 1;
        long ans = 0;
        while (left < size && less >= 0) {
            long abs = Math.abs(strand.get(bm[left][0]) - bm[left][1]); // abs 是 basket1[i] 与 总和一半的差值

            if (bm[left][1] > strand.get(bm[left][0])) { // 只考虑 大于的情况, 因为小于
                // 找到最右侧的 less
                while (less > 0 && bm[less][1] >= strand.get(bm[less][0]) ) {
                    less--;
                }

                int temp_less = strand.get(bm[less][0]) - bm[less][1]; // less 缺少的个数 temp_less, 并且会自动将less的补全

                long minValue = Math.min(min, Math.min(bm[less][0], bm[left][0]));

                // 通过 min 进行两次交换
                if (temp_less >= abs) {
                    // 一次可以交换完成
                    bm[left][1] = strand.get(bm[left][0]);
                    bm[less][1] += temp_less;
                    ans += abs * minValue;
                    left++;
                } else {
                    // 需要多次交换
                    bm[left][1] -= temp_less;
                    bm[less][1] = strand.get(bm[less][0]);
                    ans += temp_less * minValue;
                    less--;
                }
            }
        }

        return ans;
    }
}
