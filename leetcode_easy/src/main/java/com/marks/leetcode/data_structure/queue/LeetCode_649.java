package com.marks.leetcode.data_structure.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/22 14:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_649 {
    /**
     * @Description:
     * Dota2 的世界里有两个阵营：Radiant（天辉）和 Dire（夜魇）
     *
     * Dota2 参议院由来自两派的参议员组成。现在参议院希望对一个 Dota2 游戏里的改变作出决定。他们以一个基于轮为过程的投票进行。在每一轮中，每一位参议员都可以行使两项权利中的 一 项：
     * 禁止一名参议员的权利：参议员可以让另一位参议员在这一轮和随后的几轮中丧失 所有的权利 。
     * 宣布胜利：如果参议员发现有权利投票的参议员都是 同一个阵营的 ，他可以宣布胜利并决定在游戏中的有关变化。
     * 给你一个字符串 senate 代表每个参议员的阵营。字母 'R' 和 'D'分别代表了 Radiant（天辉）和 Dire（夜魇）。然后，如果有 n 个参议员，给定字符串的大小将是 n。
     * 以轮为基础的过程从给定顺序的第一个参议员开始到最后一个参议员结束。这一过程将持续到投票结束。所有失去权利的参议员将在过程中被跳过。
     * 假设每一位参议员都足够聪明，会为自己的政党做出最好的策略，你需要预测哪一方最终会宣布胜利并在 Dota2 游戏中决定改变。输出应该是 "Radiant" 或 "Dire" 。
     * @param senate
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/22 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String predictPartyVictory(String senate) {
        String result;
        result = method_01(senate);
        return result;
    }

    /**
     * @Description:
     *
     * AC:11ms/43.67MB
     * @param senate
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/22 15:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String senate) {
        int countR = 0;
        int countD = 0;
        int sumR = 0;
        int sumD = 0;
        Queue<Character> queue = new LinkedList<>();
        for (int i = 0; i < senate.length(); i++) {
            if (senate.charAt(i) == 'R') {
                sumR++;
            }else {
                sumD++;
            }
            queue.offer(senate.charAt(i));
        }
        while (!queue.isEmpty() && sumR > 0 && sumD > 0) {
            if (queue.peek() == 'R') {
                if (countD > 0) {
                    queue.poll();
                    countD--;
                    sumR--;
                } else {
                    queue.offer(queue.poll());
                    countR++;
                }
            } else {
                // 'D'
                if (countR > 0) {
                    queue.poll();
                    countR--;
                    sumD--;
                } else {
                    queue.offer(queue.poll());
                    countD++;
                }
            }
        }
        return sumR == 0 ? "Dire" : "Radiant";
    }
}
