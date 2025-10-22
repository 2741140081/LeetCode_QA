package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/17 16:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1227 {

    /**
     * @Description:
     * 有 n 位乘客即将登机，飞机正好有 n 个座位。第一位乘客的票丢了，他随便选了一个座位坐下。
     *
     * 剩下的乘客将会：
     *
     * 如果他们自己的座位还空着，就坐到自己的座位上，
     *
     * 当他们自己的座位被占用时，随机选择其他座位
     * 第 n 位乘客坐在自己的座位上的概率是多少？
     *
     * tips:
     * 1 <= n <= 10^5
     * @param n
     * @return double
     * @author marks
     * @CreateDate: 2025/10/17 16:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double nthPersonGetsNthSeat(int n) {
        double result;
        result = method_01(n);
        return result;
    }

    
    /**
     * @Description:
     * 1. p0, 恰好坐在s0, 第一个乘客坐在自己的位置上面, 后面的乘客都会坐在自己的位置上面, 此时的概率是 1/n
     * 2. p0, 恰好坐着sn - 1, 即第n位乘客的位置上面, 当前 第 n 位乘客能做到自己位置的概率是 0
     * 3. p0, 恰好坐着sn - 2, 1/n, 并且p1~pn-3 都可以对号入座, 对于 pn - 2, 剩余两个座位, 1/2 的概率选择s0, 1/2n
     * 4. p0, 恰好坐着sn - 3, 1/n, 并且p1~pn-4 都可以对号入座, 对于 pn - 3, 剩余三个座位, 2/3 的概率没有选择sn - 1,
     * 1/2 的概率选择s0, 1/3n; 选择了sn - 2, 概率 1/3, 对于pn - 2, 剩余两个座位, 1/2 的概率选择s0, 1/6n;
     * 5. 这种算太慢了, 假设 n = 3, 1/3 + 1/3 * 1/2 = 2/6 + 1/6 = 3/6 = 0.5
     * 6. n = 4, 1/4;
     * @param n 
     * @return double
     * @author marks
     * @CreateDate: 2025/10/17 16:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int n) {
        
        return n == 1 ? 1.0 : 0.5;
    }

}
