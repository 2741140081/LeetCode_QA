package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/15 10:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_682 {
    /**
     * @Description:
     * 你现在是一场采用特殊赛制棒球比赛的记录员。这场比赛由若干回合组成，过去几回合的得分可能会影响以后几回合的得分。
     *
     * 比赛开始时，记录是空白的。你会得到一个记录操作的字符串列表 ops，其中 ops[i] 是你需要记录的第 i 项操作，ops 遵循下述规则：
     *
     * 整数 x - 表示本回合新获得分数 x
     * "+" - 表示本回合新获得的得分是前两次得分的总和。题目数据保证记录此操作时前面总是存在两个有效的分数。
     * "D" - 表示本回合新获得的得分是前一次得分的两倍。题目数据保证记录此操作时前面总是存在一个有效的分数。
     * "C" - 表示前一次得分无效，将其从记录中移除。题目数据保证记录此操作时前面总是存在一个有效的分数。
     * 请你返回记录中所有得分的总和。
     *
     * @param operations
     * @return int
     * @author marks
     * @CreateDate: 2025/1/15 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int calPoints(String[] operations) {
        int result;
        result = method_01(operations);
        return result;
    }

    /**
     * @Description:
     * 使用 Stack 解决
     * AC:2ms/40.75MB
     * @param operations
     * @return int
     * @author marks
     * @CreateDate: 2025/1/15 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] operations) {
        Deque<String> stack = new ArrayDeque<>();
        int ans = 0;
        for (String operation : operations) {
            switch (operation) {
                case "+":
                    String s_one = stack.poll();
                    String s_two = stack.peek();
                    int sum = Integer.parseInt(s_one) + Integer.parseInt(s_two);
                    ans += sum;
                    stack.push(s_one);
                    stack.push(String.valueOf(sum));
                    break;
                case "D":
                    String s_top = stack.peek();
                    int num = Integer.parseInt(s_top) * 2;
                    ans += num;
                    stack.push(String.valueOf(num));
                    break;
                case "C":
                    String s_pre = stack.poll();
                    ans = ans - (Integer.parseInt(s_pre));
                    break;
                default:
                    stack.push(operation);
                    ans = ans + (Integer.parseInt(operation));
                    break;
            }
        }
        return ans;
    }
}
