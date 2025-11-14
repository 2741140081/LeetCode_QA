package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2305 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/14 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2305 {

    /**
     * @Description:
     * 给你一个整数数组 cookies ，其中 cookies[i] 表示在第 i 个零食包中的饼干数量。
     * 另给你一个整数 k 表示等待分发零食包的孩子数量，所有 零食包都需要分发。
     * 在同一个零食包中的所有饼干都必须分发给同一个孩子，不能分开。
     *
     * 分发的 不公平程度 定义为单个孩子在分发过程中能够获得饼干的最大总数。
     *
     * 返回所有分发的最小不公平程度。
     * tips:
     * 2 <= cookies.length <= 8
     * 1 <= cookies[i] <= 10^5
     * 2 <= k <= cookies.length
     * @param: cookies
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/11/14 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int distributeCookies(int[] cookies, int k) {
        int result;
        result = method_01(cookies, k);
        return result;
    }

    /**
     * @Description:
     * 用回溯法来解决
     * AC: 133ms/45.89MB
     * @param: cookies
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/11/14 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans;
    private int n;
    private int method_01(int[] cookies, int k) {
        int sum = Arrays.stream(cookies).sum();
        ans = sum;
        n = cookies.length;
        int target = sum / k;
        int[] kid = new int[k];
        dfs(cookies, k, target, n - 1, kid);
        return ans;
    }

    private void dfs(int[] cookies, int k, int target, int index, int[] kid) {
        if (index < 0) {
            int max = Arrays.stream(kid).max().getAsInt();
            ans = Math.min(ans, max);
            return;
        }
        for (int i = 0; i < k; i++) {
            if (index == n - 1 && i != 0) {
                return;
            }
            if (kid[i] > target) {
                continue;
            }
            kid[i] += cookies[index];
            dfs(cookies, k, target, index - 1, kid);
            kid[i] -= cookies[index];
        }
    }
}
