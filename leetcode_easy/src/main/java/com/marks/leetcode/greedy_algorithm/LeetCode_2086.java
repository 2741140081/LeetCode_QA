package com.marks.leetcode.greedy_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/9 15:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2086 {
    
    /**
     * @Description:
     * 给你一个下标从 0 开始的字符串 hamsters ，其中 hamsters[i]  要么是：
     *
     * 'H' 表示有一个仓鼠在下标 i ，或者
     * '.' 表示下标 i 是空的。
     * 你将要在空的位置上添加一定数量的食物桶来喂养仓鼠。
     * 如果仓鼠的左边或右边至少有一个食物桶，就可以喂食它。
     * 更正式地说，如果你在位置 i - 1 或者 i + 1 放置一个食物桶，就可以喂养位置为 i 处的仓鼠。
     *
     * 在 空的位置 放置食物桶以喂养所有仓鼠的前提下，请你返回需要的 最少 食物桶数。如果无解请返回 -1 。
     *
     * tips:
     * 1 <= hamsters.length <= 10^5
     * hamsters[i] 要么是 'H' ，要么是 '.' 。
     * @param hamsters
     * @return int
     * @author marks
     * @CreateDate: 2025/4/9 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumBuckets(String hamsters) {
        int result;
        result = method_01(hamsters);
        return result;
    }

    /**
     * @Description:
     * 1. 判断 -1 的情况, 两端是 HH, 或者中间是 HHH
     * 2. 尽可能少, 先给两端是H的配一个桶, 不不不, 只需要给end位置的H配一个桶
     * 3. 我们只给 H的右侧加桶, 如果右侧无法加桶, 那么才去加到左侧, 例如 .HH.
     *
     * AC: 5ms/44.24MB
     * @param hamsters 
     * @return int
     * @author marks
     * @CreateDate: 2025/4/9 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String hamsters) {
        int n = hamsters.length();
        if (n == 1) {
            return hamsters.equals("H") ? -1 : 0;
        }
        boolean starts = hamsters.startsWith("HH");
        boolean ends = hamsters.endsWith("HH");
        boolean contains = hamsters.contains("HHH");
        if (starts || ends || contains) {
            return -1;
        }

        char[] array = hamsters.toCharArray();
        int ans = 0;
        if (array[0] == 'H') {
            array[1] = 'X'; // X表示添加一个桶
            ans++;
        }
        for (int i = 1; i < array.length; i++) {
            if (array[i] == 'H' && array[i - 1] != 'X') {
                if (i + 1 < n && array[i + 1] == '.') {
                    // 右侧加桶
                    array[i + 1] = 'X';
                } else {
                    // 左侧加桶
                    array[i - 1] = 'X';
                }
                ans++;
            }
        }
        return ans;
    }
}
