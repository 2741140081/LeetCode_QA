package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/9 15:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_679 {

    
    /**
     * @Description:
     * 给定一个长度为4的整数数组 cards 。你有 4 张卡片，每张卡片上都包含一个范围在 [1,9] 的数字。
     * 您应该使用运算符 ['+', '-', '*', '/'] 和括号 '(' 和 ')' 将这些卡片上的数字排列成数学表达式，以获得值24。
     *
     * 你须遵守以下规则:
     *
     * 除法运算符 '/' 表示实数除法，而不是整数除法。
     * 例如， 4 /(1 - 2 / 3)= 4 /(1 / 3)= 12 。
     * 每个运算都在两个数字之间。特别是，不能使用 “-” 作为一元运算符。
     * 例如，如果 cards =[1,1,1,1] ，则表达式 “-1 -1 -1 -1” 是 不允许 的。
     * 你不能把数字串在一起
     * 例如，如果 cards =[1,2,1,2] ，则表达式 “12 + 12” 无效。
     * 如果可以得到这样的表达式，其计算结果为 24 ，则返回 true ，否则返回 false 。
     *
     * @param cards
     * @return boolean
     * @author marks
     * @CreateDate: 2025/9/9 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean judgePoint24(int[] cards) {
        boolean result;
        result = method_01(cards);
        result = method_02(cards);
        return result;
    }

    private boolean method_02(int[] cards) {
        // 将int数组转换为double数组以支持实数运算
        double[] nums = new double[cards.length];
        for (int i = 0; i < cards.length; i++) {
            nums[i] = cards[i];
        }
        return backtrack(nums);
    }

    private boolean backtrack(double[] nums) {
        // 递归终止条件：只剩一个数时，判断是否接近24
        if (nums.length == 1) {
            return Math.abs(nums[0] - 24) < 1e-6;
        }

        // 枚举任意两个数进行运算
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                // 构造新的数组，包含除nums[i]和nums[j]外的其他数
                double[] next = new double[nums.length - 1];
                int idx = 0;
                for (int k = 0; k < nums.length; k++) {
                    if (k != i && k != j) {
                        next[idx++] = nums[k];
                    }
                }

                // 尝试四种运算
                // 加法：a + b
                next[next.length - 1] = nums[i] + nums[j];
                if (backtrack(next)) return true;

                // 乘法：a * b
                next[next.length - 1] = nums[i] * nums[j];
                if (backtrack(next)) return true;

                // 减法：a - b 和 b - a
                next[next.length - 1] = nums[i] - nums[j];
                if (backtrack(next)) return true;

                next[next.length - 1] = nums[j] - nums[i];
                if (backtrack(next)) return true;

                // 除法：a / b (需要检查除数不为0)
                if (Math.abs(nums[j]) > 1e-6) {
                    next[next.length - 1] = nums[i] / nums[j];
                    if (backtrack(next)) return true;
                }

                // 除法：b / a (需要检查除数不为0)
                if (Math.abs(nums[i]) > 1e-6) {
                    next[next.length - 1] = nums[j] / nums[i];
                    if (backtrack(next)) return true;
                }
            }
        }

        return false;
    }


    /**
     * @Description:
     * E1:
     * 输入：cards = [4,1,8,7]
     * 输出：true
     * 解释: ((7 - 4) * 8) / 1 = 24
     * 1. 枚举每一张卡片作为第一个输入, card[0], 例如 第一个数输入为 4, 添加一个visited[] 数组判断对应index是否已使用
     * 2. 对于这种取操作数的操作, 可以使用一个队列, 先进先出的方式来计算最终值, 另外需要实现一个实数的除法? 感觉不需要具体实现这个
     * 实数的除法, 因为前一个结果 int result, 当前值 int curr = cards[i]; 如果 result % curr != 0, 则直接可以判断当前不符合条件, 不进行递归调用
     * 例如 4 / (1/3) => 完全可以等价于 4 / 1 * 3 = 12
     * 3. 构建一个String[] operators = {"+", "-", "*", "/"}; 另外我们只是判断存在性, 也就是只要存在一个即可, 存在即退出。
     * 4. 不太对, 这种情况没有考虑,
     * E2: // 测试用例2: [3,3,8,8] -> 8/(3-8/3) = 24
     *
     * @param cards 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/9/9 15:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean ans;
    private String[] operators = {"+", "-", "*", "/"};
    private boolean method_01(int[] cards) {
        ans = false;
        boolean[] visited = new boolean[cards.length];
        for (int i = 0; i < cards.length; i++) {
            visited[i] = true;
            backtrack(cards, visited, cards[i], 24);
            visited[i] = false;
        }
        return ans;
    }

    private void backtrack(int[] cards, boolean[] visited, int res, int target) {
        if (ans) {
            // if ans is true, then we can return
            return;
        }
        if (check(visited)) {
            // if all cards are visited, then wo should check the result
            if (res == target) {
                ans = true;
            }
            return;
        }
        for (int i = 0; i < cards.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                for (String operator : operators) {
                    switch (operator) {
                        case "+":
                            backtrack(cards, visited, res + cards[i], target);
                            break;
                        case "-":
                            backtrack(cards, visited, res - cards[i], target);
                            break;
                        case "*":
                            backtrack(cards, visited, res * cards[i], target);
                            break;
                        case "/":
                            if (res % cards[i] == 0) {
                                backtrack(cards, visited, res / cards[i], target);
                            }
                    }
                }
                visited[i] = false;
            }
        }
    }

    private boolean check(boolean[] visited) {
        for (boolean flag : visited) {
            if (!flag) {
                // if there is a card not visited, is mean visited is false, then we can return false
                return false;
            }
        }
        // all cards are visited, and visited is true, then we can return true
        return true;
    }

}
