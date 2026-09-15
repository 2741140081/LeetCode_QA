package com.marks.leetcode.array_medium;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2761 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/15 17:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2761 {

    /**
     * @Description:
     * 给你一个整数 n 。如果两个整数 x 和 y 满足下述条件，则认为二者形成一个质数对：
     * 1 <= x <= y <= n
     * x + y == n
     * x 和 y 都是质数
     * 请你以二维有序列表的形式返回符合题目要求的所有 [xi, yi] ，列表需要按 xi 的 非递减顺序 排序。
     * 如果不存在符合要求的质数对，则返回一个空数组。
     * 注意：质数是大于 1 的自然数，并且只有两个因子，即它本身和 1 。
     * tips:
     * 1 <= n <= 10^6
     * @param: n
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/09/15 17:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> findPrimePairs(int n) {
        List<List<Integer>> result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 遍历[2~ n - 2] 闭区间, 找出所有的质数, 存储到 Set 集合中
     * 2. 遍历 Set 集合, 对于 s_i, 判断 n - s_i 是否也在 Set 集合中, 如果在, 则将 [s_i, n - s_i] 添加到结果集中
     * 3. 由于需要升序数组, 所以将 值同样存储在 List 中, 并且升序, 最后用 list 遍历替代 set 遍历
     * 4. 使用筛法, 埃拉托斯特尼筛法 来得到
     * AC: 499ms/265.35MB
     * @param: n
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/09/15 17:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int n) {
        List<Integer> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                set.add(i);
                list.add(i);
                for (int j = i * 2; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        List<List<Integer>> result = new ArrayList<>();
        for (int s_i : list) {
            int y = n - s_i;
            if (y < s_i) { // 防止重复数对, 例如[3,7] 和 [7,3] 是重复的
                break;
            }
            if (set.contains(y)) {
                result.add(Arrays.asList(s_i, y));
            }
        }

        return result;
    }

}
