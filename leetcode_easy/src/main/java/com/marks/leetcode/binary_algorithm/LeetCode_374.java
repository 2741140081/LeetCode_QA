package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_374 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/27 9:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_374 {


    /**
     * @Description:
     * 我们正在玩猜数字游戏。猜数字游戏的规则如下：
     * 我会从 1 到 n 随机选择一个数字。 请你猜选出的是哪个数字。（我选的数字在整个游戏中保持不变）。
     * 如果你猜错了，我会告诉你，我选出的数字比你猜测的数字大了还是小了。
     *
     * 你可以通过调用一个预先定义好的接口 int guess(int num) 来获取猜测结果，返回值一共有三种可能的情况：
     *
     * -1：你猜的数字比我选出的数字大 （即 num > pick）。
     * 1：你猜的数字比我选出的数字小 （即 num < pick）。
     * 0：你猜的数字与我选出的数字相等。（即 num == pick）。
     * 返回我选出的数字。
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/27 9:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int guessNumber(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * AC: 0ms/41.39MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/27 9:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (guess(mid) == 0) {
                return mid;
            } else if (guess(mid) == 1) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
    private final int value = (int) (Math.random() * 100);
    private int guess(int num) {
        return Integer.compare(value, num);
    }

}
