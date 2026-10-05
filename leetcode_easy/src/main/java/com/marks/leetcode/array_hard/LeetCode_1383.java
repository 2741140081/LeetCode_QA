package com.marks.leetcode.array_hard;

import java.math.BigInteger;
import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1383 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/7 16:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1383 {

    /**
     * @Description:
     * 给定两个整数 n 和 k，以及两个长度为 n 的整数数组 speed 和 efficiency。
     * 现有 n 名工程师，编号从 1 到 n。其中 speed[i] 和 efficiency[i] 分别代表第 i 位工程师的速度和效率。
     * 从这 n 名工程师中最多选择 k 名不同的工程师，使其组成的团队具有最大的团队表现值。
     * 团队表现值 的定义为：一个团队中「所有工程师速度的和」乘以他们「效率值中的最小值」。
     * 请你返回该团队的最大团队表现值，由于答案可能很大，请你返回结果对 10^9 + 7 取余后的结果。
     *
     * tips:
     * 1 <= k <= n <= 10^5
     * speed.length == n
     * efficiency.length == n
     * 1 <= speed[i] <= 10^5
     * 1 <= efficiency[i] <= 10^8
     * @param: n
     * @param: speed
     * @param: efficiency
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/07 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        int result;
        result = method_01(n, speed, efficiency, k);
        result = method_02(n, speed, efficiency, k);
        return result;
    }
    class Staff {
        int s, e;

        public Staff(int s, int e) {
            this.s = s;
            this.e = e;
        }
    }

    private int method_02(int n, int[] speed, int[] efficiency, int k) {
        final int MODULO = 1000000007;
        List<Staff> list = new ArrayList<Staff>();
        PriorityQueue<Staff> queue = new PriorityQueue<Staff>(new Comparator<Staff>() {
            public int compare(Staff staff1, Staff staff2) {
                return staff1.s - staff2.s;
            }
        });
        for (int i = 0; i < n; ++i) {
            list.add(new Staff(speed[i], efficiency[i]));
        }
        Collections.sort(list, new Comparator<Staff>() {
            public int compare(Staff staff1, Staff staff2) {
                return staff2.e - staff1.e;
            }
        });
        long ans = 0, sum = 0;
        for (int i = 0; i < n; ++i) {
            Staff staff = list.get(i);
            long minE = staff.e;
            long sumS = sum + staff.s;
            ans = Math.max(ans, sumS * minE);
            queue.offer(staff);
            sum += staff.s;
            if (queue.size() == k) {
                sum -= queue.poll().s;
            }
        }
        return (int) (ans % MODULO);
    }

    private int MOD = 1000000007;
    /**
     * @Description:
     * 1. 假设按照效率进行降序排序, 前 k 个的最小效率是 minEfficiency
     * 2. 使用 有序队列存储前 k 个 speed, 小根堆, 堆顶元素最小
     * 3. 对于 i, 如果 speed[i] > pq.peek(), 则更新 pq, 删除堆顶元素, 添加 speed[i],
     * 计算此时的团队表现值, 更新结果, 如果小于或者等于则跳过处理下一个
     * 4. 由于可以少于 k 个工程师
     * Error: 46/55
     * AC: 42ms/65.5MB
     * @param: n
     * @param: speed
     * @param: efficiency
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/07 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[] speed, int[] efficiency, int k) {
        int[][] engineers = new int[n][2];
        for (int i = 0; i < n; i++) {
            engineers[i][0] = speed[i];
            engineers[i][1] = efficiency[i];
        }
        Arrays.sort(engineers, (a, b) -> b[1] - a[1]); // 降序排序
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        long sum = 0;
        int minEfficiency = 0;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            if (pq.size() < k) {
                sum += engineers[i][0];
                pq.offer(engineers[i][0]);
                minEfficiency = engineers[i][1];
                ans = Math.max(ans, sum * minEfficiency);
            } else {
                if (!pq.isEmpty() && engineers[i][0] > pq.peek()) {
                    int first = pq.peek();
                    sum -= first;
                    pq.poll();
                    sum += engineers[i][0];
                    pq.offer(engineers[i][0]);
                    minEfficiency = engineers[i][1];
                    ans = Math.max(ans, sum * minEfficiency);
                }
            }
        }
        return (int) (ans % MOD);
    }

}
