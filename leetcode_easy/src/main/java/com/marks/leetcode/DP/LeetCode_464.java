package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_464 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/6 10:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_464 {

    /**
     * @Description:
     * 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，
     * 先使得累计整数和 达到或超过  100 的玩家，即为胜者。
     *
     * 如果我们将游戏规则改为 “玩家 不能 重复使用整数” 呢？
     * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
     *
     * 给定两个整数 maxChoosableInteger （整数池中可选择的最大数）和 desiredTotal（累计和），
     * 若先出手的玩家能稳赢则返回 true ，否则返回 false 。假设两位玩家游戏时都表现 最佳 。
     * @param: maxChoosableInteger
     * @param: desiredTotal
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/06 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        boolean result;
        result = method_01(maxChoosableInteger, desiredTotal);
        return result;
    }

    /**
     * @Description:
     * 1，2，3，4 => 6
     * need todo.
     * 这题不是简单的选择数, 而是一种博弈?, 具体怎么处理还不清楚.
     * @param: maxChoosableInteger
     * @param: desiredTotal
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/06 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int maxChoosableInteger, int desiredTotal) {
        int left = 2;
        int right = maxChoosableInteger;
        int leftSum = 1;
        int rightSum = 0;
        if (leftSum >= desiredTotal) {
            return true;
        }
        while (left < right) {
            rightSum += right;
            if (rightSum >= desiredTotal) {
                return false;
            }
            leftSum += left;
            if (leftSum >= desiredTotal) {
                return true;
            }
            left++;
            right--;
        }

        return false;
    }

}
