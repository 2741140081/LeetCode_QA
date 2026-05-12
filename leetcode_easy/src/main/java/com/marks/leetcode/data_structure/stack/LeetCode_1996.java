package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1996 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/11 17:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1996 {

    /**
     * @Description:
     * 你正在参加一个多角色游戏，每个角色都有两个主要属性：攻击 和 防御 。给你一个二维整数数组 properties ，其中 properties[i] = [attacki, defensei] 表示游戏中第 i 个角色的属性。
     * 如果存在一个其他角色的攻击和防御等级 都严格高于 该角色的攻击和防御等级，则认为该角色为 弱角色 。更正式地，如果认为角色 i 弱于 存在的另一个角色 j ，那么 attackj > attacki 且 defensej > defensei 。
     * 返回 弱角色 的数量。
     *
     * tips:
     * 2 <= properties.length <= 10^5
     * properties[i].length == 2
     * 1 <= attacki, defensei <= 10^5
     * @param: properties
     * @return int
     * @author marks
     * @CreateDate: 2026/05/11 17:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfWeakCharacters(int[][] properties) {
        int result;
        result = method_01(properties);
        return result;
    }

    /**
     * @Description:
     * 1. 查看题解, 由于单调栈只能处理一个方面的单调性, 无法同时对 attack 和 defense 进行单调性判断
     * 2. 所以先对数组进行排序, 按照 attack 升序排序, p[i][0] <= p[i+1][0]
     * 3. 分情况讨论, 当 p[i][0] = p[i+1][0] 时, 考虑 p[i][1] 和 p[i+1][1] 的大小关系, 当我们对 p[i][1] 进行降序排序时,
     * 可以过滤掉 p[i][0] 相同的值, 这些值都是不符合条件的, p[i + 1][1] > p[i][1] && 排序后 的 p[i + 1][0] > p[i][0],
     * 此时 p[i] 是弱角色
     * 4. 排序规则如下: 对 p[i][0] 进行升序排序, 在此基础上, 对 p[i][1] 进行降序排序
     * 5. 然后通过单调栈, 对 p[i][1] 进行单调栈处理, 栈顶元素小于 当前 p[i][1], 则弹出栈顶元素, ans++;
     * 6. 何种情况下向栈中添加元素, 直接向栈中添加元素即可
     * AC: 107ms/148.35MB
     * @param: properties
     * @return int
     * @author marks
     * @CreateDate: 2026/05/11 17:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] properties) {

        // 排序, attack 升序, defense 降序
        Arrays.sort(properties, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        // 创建单调栈
        Deque<Integer> stack = new ArrayDeque<>();
        int ans = 0;
        for (int[] property : properties) {
            while (!stack.isEmpty() && stack.peek() < property[1]) {
                stack.pop();
                ans++;
            }
            stack.push(property[1]);
        }
        return ans;
    }

}
