package com.marks.leetcode.array_hard;

import java.util.*;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_710. 黑名单中的随机数 </p>
 * <p>描述:
 * 给定一个整数 n 和一个 无重复 黑名单整数数组 blacklist 。
 * 设计一种算法，从 [0, n - 1] 范围内的任意整数中选取一个 未加入 黑名单 blacklist 的整数。
 * 任何在上述范围内且不在黑名单 blacklist 中的整数都应该有 同等的可能性 被返回。
 * 优化你的算法，使它最小化调用语言 内置 随机函数的次数。
 * 实现 Solution 类:
 * Solution(int n, int[] blacklist) 初始化整数 n 和被加入黑名单 blacklist 的整数
 * int pick() 返回一个范围为 [0, n - 1] 且不在黑名单 blacklist 中的随机整数
 * </p>
 *
 * tips:
 * 1 <= n <= 10^9
 * 0 <= blacklist.length <= min(10^5, n - 1)
 * 0 <= blacklist[i] < n
 * blacklist 中所有值都 不同
 *  pick 最多被调用 2 * 10^4 次
 * @author marks
 * @version v1.0
 * @date 2026/8/5 14:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Solution {
    Map<Integer, Integer> b2w;
    Random random;
    int bound;

    /**
     * @Description:
     * 1. 找到所有不在名单中的数, 构建类似于白名单集合, 大小是 m = (n - blacklist.length),
     * 也就输白名单每个数组被选择的概率是 1/m.
     * 2. 该如何通过随机的下标得到所需的数字? 假设通过随机函数返回[0 ~ m - 1] 的一个数是 j,
     * [0 ~ x] 白名单中第 j 位数的下标
     * 3. 将右侧的数字映射到左侧的 blacklist[i] 处
     * AC: 51ms/67.18MB
     * @param: n
     * @param: blacklist
     * @return
     * @author marks
     * @CreateDate: 2026/08/05 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Solution(int n, int[] blacklist) {
        b2w = new HashMap<Integer, Integer>();
        random = new Random();
        int m = blacklist.length;
        bound = n - m;
        Set<Integer> black = new HashSet<Integer>();
        for (int b : blacklist) {
            if (b >= bound) {
                black.add(b);
            }
        }

        int w = bound;
        for (int b : blacklist) {
            if (b < bound) {
                while (black.contains(w)) {
                    ++w;
                }
                b2w.put(b, w); // 将 b 下标映射给白名单 w, 例如 2 -> 4, 3 -> 6, 完善 [0 ~ bound] 所有下标都有结果
                ++w;
            }
        }
    }

    public int pick() {
        int x = random.nextInt(bound);
        return b2w.getOrDefault(x, x);
    }

}
