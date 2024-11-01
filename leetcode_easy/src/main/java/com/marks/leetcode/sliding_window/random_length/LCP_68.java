package com.marks.leetcode.sliding_window.random_length;

import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 11:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_68 {
    public final int MOD = (int) 1e9 + 7;

    /**
     * @Description: [
     * 力扣嘉年华的花店中从左至右摆放了一排鲜花，记录于整型一维矩阵 flowers 中每个数字表示该位置所种鲜花的品种编号。
     * 你可以选择一段区间的鲜花做成插花，且不能丢弃。 在你选择的插花中，如果每一品种的鲜花数量都不超过 cnt 朵，那么我们认为这束插花是 「美观的」。
     *
     * 例如：[5,5,5,6,6] 中品种为 5 的花有 3 朵， 品种为 6 的花有 2 朵，每一品种 的数量均不超过 3
     * 请返回在这一排鲜花中，共有多少种可选择的区间，使得插花是「美观的」。
     *
     * 注意：
     *
     * 答案需要以 1e9 + 7 (1000000007) 为底取模，如：计算初始结果为：1000000008，请返回 1
     *
     * tips:
     * 1 <= flowers.length <= 10^5
     * 1 <= flowers[i] <= 10^5
     * 1 <= cnt <= 10^5
     * ]
     * @param flowers
     * @param cnt
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int beautifulBouquet(int[] flowers, int cnt) {
        int result = 0;
        result = method_01(flowers, cnt);
        return result;
    }

    /**
     * @Description: [
     * 输入：flowers = [1,2,3,2], cnt = 1
     *
     * 输出：8
     * AC:61ms/57.13MB
     * ]
     * @param flowers
     * @param cnt
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] flowers, int cnt) {
        int n = flowers.length;
        int ans = 0;
        int left = 0;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int right = 0; right < n; right++) {
            map.merge(flowers[right], 1, Integer::sum);
            while (map.get(flowers[right]) > cnt && left <= right) {
                int temp = flowers[left];
                if (map.get(temp) == 1) {
                    map.remove(temp);
                }else {
                    map.merge(temp, -1, Integer::sum);
                }
                left++;
            }
            if (map.get(flowers[right]) <= cnt) {
                ans = (ans + (right - left + 1)) % MOD;
            }
        }
        return ans;
    }
}
