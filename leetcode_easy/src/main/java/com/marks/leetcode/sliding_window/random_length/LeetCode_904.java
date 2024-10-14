package com.marks.leetcode.sliding_window.random_length;

import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 14:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_904 {
    /**
     * @Description: [
     * 你正在探访一家农场，农场从左到右种植了一排果树。这些树用一个整数数组 fruits 表示，其中 fruits[i] 是第 i 棵树上的水果 种类 。
     *
     * 你想要尽可能多地收集水果。然而，农场的主人设定了一些严格的规矩，你必须按照要求采摘水果：
     *
     * 你只有 两个 篮子，并且每个篮子只能装 单一类型 的水果。每个篮子能够装的水果总量没有限制。
     * 你可以选择任意一棵树开始采摘，你必须从 每棵 树（包括开始采摘的树）上 恰好摘一个水果 。采摘的水果应当符合篮子中的水果类型。每采摘一次，你将会向右移动到下一棵树，并继续采摘。
     * 一旦你走到某棵树前，但水果不符合篮子的水果类型，那么就必须停止采摘。
     * 给你一个整数数组 fruits ，返回你可以收集的水果的 最大 数目。
     * tips:
     * 1 <= fruits.length <= 10^5
     * 0 <= fruits[i] < fruits.length
     * ]
     * @param fruits
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 14:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int totalFruit(int[] fruits) {
        int result = 0;
//        result = method_01(fruits);
        result = method_02(fruits);
        return result;
    }
    /**
     * @Description: [
     * 优化成功:
     * AC:5ms/54.18MB
     * ]
     * @param fruits
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] fruits) {
        int n = fruits.length;
        // one, two 分别表示水果1和水果2
        int one = fruits[0];
        int two = -1; // -1不存在于fruits[i]中
        int ans = 0;
        // left 记录左边界
        int left = 0;
        for (int i = 0; i < n; i++) {
            int temp = fruits[i];
            if (two == -1 && temp != one) {
                two = temp;
            }
            if (temp != one && temp != two) {
                // temp为第三种水果
                for (int j = i - 1; j > left; j--) {
                    //新的第一种水果
                    int curr = fruits[j];
                    int pre = fruits[j - 1];
                    if (curr != pre) {
                        left = j;
                        one = curr;
                        two = temp;
                        break;
                    }
                }
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }

    /**
     * @Description: [
     * E1:
     * 输入：fruits = [3,3,3,1,2,1,1,2,3,3,4]
     * 输出：5
     * 解释：可以采摘 [1,2,1,1,2] 这五棵树。
     *
     * AC:53ms/53.91MB
     * 感觉可以优化, map应该不是必须得, 可以用变量one, two分别记录第一种、第二种水果, 同时用sum记录数量
     *
     * ]
     * @See method_02(int[] fruits)
     * @param fruits
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 14:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] fruits) {
        int n = fruits.length;
        int ans = 0;
        // 左边界
        int left = 0;
        // 存储窗口中的水果数量和种类
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int temp = fruits[i];
            if (map.containsKey(temp) || map.size() <= 1) {
                // 数量加1, 种类不变
                map.put(temp, map.getOrDefault(temp, 0) + 1);
                ans = Math.max(ans, i - left + 1);
            }else if (map.size() == 2) {
                // 当前窗口中已经有2种类型的水果, 当前进入的水果为新类型
                while (map.size() == 2) {
                    int pre = fruits[left];
                    if (map.get(pre) == 1) {
                        map.remove(pre);
                    }else {
                        map.put(pre, map.get(pre) - 1);
                    }
                    left++;
                }
                map.put(temp, map.getOrDefault(temp, 0) + 1);
            }
        }
        return ans;
    }
}
