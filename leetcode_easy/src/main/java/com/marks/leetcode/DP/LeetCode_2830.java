package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2830 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/24 15:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2830 {

    /**
     * @Description:
     * 给你一个整数 n 表示数轴上的房屋数量，编号从 0 到 n - 1 。
     * 另给你一个二维整数数组 offers ，其中 offers[i] = [starti, endi, goldi] 表示第 i 个买家想要以 goldi 枚金币的价格购买从 starti 到 endi 的所有房屋。
     * 作为一名销售，你需要有策略地选择并销售房屋使自己的收入最大化。
     * 返回你可以赚取的金币的最大数目。
     * 注意 同一所房屋不能卖给不同的买家，并且允许保留一些房屋不进行出售。
     * tips:
     * 1 <= n <= 10^5
     * 1 <= offers.length <= 10^5
     * offers[i].length == 3
     * 0 <= starti <= endi <= n - 1
     * 1 <= goldi <= 10^3
     * @param: n
     * @param: offers
     * @return int
     * @author marks
     * @CreateDate: 2026/02/24 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximizeTheProfit(int n, List<List<Integer>> offers) {
        int result;
        result = method_01(n, offers);
        return result;
    }

    /**
     * @Description:
     * 直接查看题解, 需要后续自行理解 need todo
     * AC: 22ms/138.68MB
     * @param: n
     * @param: offers
     * @return int
     * @author marks
     * @CreateDate: 2026/02/24 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, List<List<Integer>> offers) {
        List<int[]>[] groups = new ArrayList[n];
        Arrays.setAll(groups, i -> new ArrayList<>());
        for (List<Integer> offer : offers) {
            groups[offer.get(1)].add(new int[]{offer.get(0), offer.get(2)});
        }

        int[] f = new int[n + 1];
        for (int end = 0; end < n; end++) {
            f[end + 1] = f[end];
            for (int[] p : groups[end]) {
                f[end + 1] = Math.max(f[end + 1], f[p[0]] + p[1]);
            }
        }
        return f[n];
    }

}
