package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2910 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/9 14:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2910 {

    /**
     * @Description:
     * 给你一组带编号的 balls 并要求将它们分类到盒子里，以便均衡地分配。你必须遵守两条规则：
     * 同一个盒子里的球必须具有相同的编号。但是，如果你有多个相同编号的球，你可以把它们放在不同的盒子里。
     * 最大的盒子只能比最小的盒子多一个球。
     * 返回遵循上述规则排列这些球所需要的盒子的最小数目。
     *
     * tips:
     * 1 <= balls.length <= 10^5
     * 1 <= balls[i] <= 10^9
     * @param: balls
     * @return int
     * @author marks
     * @CreateDate: 2026/06/09 14:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minGroupsForValidAssignment(int[] balls) {
        int result;
        result = method_01(balls);
        return result;
    }

    /**
     * @Description:
     * 1. 统计每个编号的球的数量
     * 2. 是否需要使用二分法来确定最小盒子中球的数量 int mid, 其中 mid 的取值范围是 [1, min(balls[i])],
     * 由于盒子容量越小, 所需盒子数目越多, 所以存在一个单调性.
     * 3. 如何基于 int mid, 来求出当前最小盒子容量是 mid, 所需盒子的数目?
     * 4. int currCnt = map.get(balls[i]); 其中 currCnt 表示当前编号的球数量, 要使得盒子最小容量是 mid, 最大容量是 mid + 1,
     * 求最小的数目, 基于贪心的策略, int cnt = currCnt / (mid + 1); (需要多少个盒子) int rest = currCnt % (mid + 1); (剩余了多少个球).
     * 需要分情况讨论:
     * 4.1 rest = 0, 说明没有剩余的球, 需要盒子总数是 cnt 个;
     * 4.2 rest != 0, cnt + rest < mid; 剩余的球 + 从每个大盒子中取出一个球, 构成的新的盒子, 但是数量小于目标值 mid, 无法成功, 即 mid 方案不可行, 返回 -1;
     * 4.3 rest != 0, cnt + rest >= mid; 剩余的球 + 从每个大盒子中取出一个球, 构成的新的盒子, 数量大于等于目标值 mid, 可行, 即 mid 方案可行; 此时需要的盒子总数是 cnt + 1;
     * 5. 基于上述规则可以得到map 中每一个key 所需的最小盒子数量, int sum = SUM(map); 如果所有球的数量都能放入盒子, 返回 sum.
     * 6. int ans; ans = allBallsCont, 即 ans 的初始值为 sum (所有球的数量), ans 与 rtn 进行对比, rtn 即为最小的盒子数量.
     * 如果 rtn == -1, 需要降低盒子容量 right = mid - 1;
     * 如果 rtn != -1, ans >= rtn, 需要增加盒子容量 left = mid + 1; 并且更新 ans 结果 ans = Math.min(ans, rtn);
     * 如果 rnt != -1, ans < rtn, 需要增加盒子容量 left = mid + 1;
     * 7. 但是会不会有一种可能性是i, j, 并且 j > i, 即在 i 的时候不满足, rtn = -1, 但是 在 j 的时候满足. 例如有一个球数量是 7,
     * i = 4, j = 7 时, i = 4时, rtn = -1, j = 7时, rtn = 1;
     * 8. 所以不能使用二分法, 而是使用遍历的方式, 从 min ~ 1 进行遍历, 如果找到 一个最小的盒子容量, 则返回;
     * AC: 52ms/127.46MB
     * @param: balls
     * @return int
     * @author marks
     * @CreateDate: 2026/06/09 14:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] balls) {
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        for (int ball : balls) {
            map.put(ball, map.getOrDefault(ball, 0) + 1);
            ans += map.get(ball); // 盒子容量为1是所需的盒子数量
        }
        int min = map.values().stream().min(Integer::compareTo).get();
        for (int i = min; i >= 1; i--) {
            int rtn = checkIsPossible(map, i);
            if (rtn != -1) {
                ans = Math.min(ans, rtn);
                break;
            }
        }

        return ans;
    }

    private int checkIsPossible(Map<Integer, Integer> map, int i) {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int currCnt = entry.getValue();
            int cnt = currCnt / (i + 1);
            int rest = currCnt % (i + 1);
            if (rest != 0 && cnt + rest < i) {
                return -1;
            }
            sum += (cnt + (rest != 0 ? 1 : 0));
        }
        return sum;
    }

}
