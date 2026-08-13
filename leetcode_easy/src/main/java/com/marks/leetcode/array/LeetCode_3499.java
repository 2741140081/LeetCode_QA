package com.marks.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3499 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/21 10:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3499 {

    /**
     * @Description:
     * 给你一个长度为 n 的二进制字符串 s，其中：
     * '1' 表示一个 活跃 区段。
     * '0' 表示一个 非活跃 区段。
     * 你可以执行 最多一次操作 来最大化 s 中的活跃区段数量。在一次操作中，你可以：
     * 将一个被 '0' 包围的连续 '1' 区块转换为全 '0'。
     * 然后，将一个被 '1' 包围的连续 '0' 区块转换为全 '1'。
     * 返回在执行最优操作后，s 中的 最大 活跃区段数。
     * 注意：处理时需要在 s 的两侧加上 '1' ，即 t = '1' + s + '1'。这些加上的 '1' 不会影响最终的计数。
     * tips:
     * 1 <= n == s.length <= 10^5
     * s[i] 仅包含 '0' 或 '1'
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/07/21 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxActiveSectionsAfterTrade(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 由于只能进行一次操作, 并且需要操作后的连续1的数量最大, 并且先将 被 0 包裹的1转成0, 然后将3个区间的连续0变成1, 这就是一次操作能得到的连续1的
     * 2. 执行操作的前提的s 种存在两个及其以上的 0 区间, 否则无法进行操作, 此时返回s 中 最大的 连续1的个数
     * 3. 方便处理需要对 s 进行前缀和操作，方便后续处理
     * 4. 需要一个类似于滑动窗口, 窗口内最大有5个空间, 最少有3个区间, 3个为1的区间 和两个为 0 的区间
     * 5. 应该计算的是1的个数
     * AC: 112ms/58.1MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/07/21 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        List<int[]> list = new ArrayList<>();
        int cnt = 1;
        char prev = s.charAt(0);
        if (prev == '0') {
            list.add(new int[]{0, 1}); // 添加虚拟 1 区间, cnt = 0
        }
        int maxCnt = (prev == '1' ? 1 : 0);

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) != prev) {
                list.add(new int[]{cnt, prev == '1' ? 1 : 0});
                cnt = 1;
                prev = s.charAt(i);
            } else {
                cnt++;
            }
            if (s.charAt(i) == '1') {
                maxCnt++;
            }
        }
        // 添加最后一个区间
        list.add(new int[]{cnt, s.charAt(n - 1) == '1' ? 1 : 0});
        if (s.charAt(n - 1) == '0') {
            list.add(new int[]{0, 1}); // 添加虚拟 1 区间, cnt = 0
        }
        if (list.size() < 5) {
            return maxCnt;
        }
        // 创建滑动窗口, left, right, 窗口大小为 5 个元素, 每次向前移动两步
        int left = 1, right = 5;
        int sum = 0; // 计算区间中 0 的个数
        for (int i = 0; i < 5; i++) {
            if (i % 2 != 0) {
                sum += list.get(i)[0];
            }
        }
        int ans = sum;
        while (right + 2 <= list.size()) {
            sum += list.get(right)[0];
            right += 2;
            sum -= list.get(left)[0];
            left += 2;

            ans = Math.max(ans, sum);
        }

        return ans + maxCnt;
    }

}
