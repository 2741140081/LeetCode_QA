package com.marks.leetcode.binary_tree;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/16 15:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2509 {
    private int count;
    
    /**
     * @Description:
     * 给你一个整数 n ，表示你有一棵含有 2^n - 1 个节点的 完全二叉树 。
     * 根节点的编号是 1 ，树中编号在[1, 2^(n - 1) - 1] 之间，编号为 val 的节点都有两个子节点，满足：
     *
     * 左子节点的编号为 2 * val
     * 右子节点的编号为 2 * val + 1
     * 给你一个长度为 m 的查询数组 queries ，它是一个二维整数数组，其中 queries[i] = [ai, bi] 。对于每个查询，求出以下问题的解：
     *
     * 在节点编号为 ai 和 bi 之间添加一条边。
     * 求出图中环的长度。
     * 删除节点编号为 ai 和 bi 之间新添加的边。
     * 注意：
     *
     * 环 是开始和结束于同一节点的一条路径，路径中每条边都只会被访问一次。
     * 环的长度是环中边的数目。
     * 在树中添加额外的边后，两个点之间可能会有多条边。
     * 请你返回一个长度为 m 的数组 answer ，其中 answer[i] 是第 i 个查询的结果。
     *
     * tips:
     * 2 <= n <= 30
     * m == queries.length
     * 1 <= m <= 10^5
     * queries[i].length == 2
     * 1 <= ai, bi <= 2^n - 1
     * ai != bi
     * @param n 
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/7/16 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] cycleLengthQueries(int n, int[][] queries) {
        int[] result;
//        result = method_01(n, queries);
        result = method_02(n, queries);
        return result;
    }

    /**
     * @Description:
     * 对 method_01 方法的简化
     * 1. 通过前导0来计算层数, Integer.numberOfLeadingZeros(x)
     * 2. 直接将 d 进行右移, 使得 x, y 处于同一高度 y >>= d;
     *
     * AC: 7ms(100%)/90.33MB(82.14%)
     * @param n
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/7/16 17:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int n, int[][] queries) {
        int len = queries.length;
        int[] ans = new int[len];
        for(int i = 0; i < len; i++){
            int[] q = queries[i];
            int x = q[0], y = q[1];
            if(x > y){
                // 将x, y 交换值, 保证 x <= y
                int temp = x;
                x = y;
                y = temp;
            }
            // 因为 x <= y, 所以x的前导0 >= y的前导0, 此处的前导0相当于二叉树中的层数
            int d = Integer.numberOfLeadingZeros(x) - Integer.numberOfLeadingZeros(y);

            // >>=, 右移d位, 相当于是将y 移动到 与 x 相同的高度, 并且 将 y 右移 d位后的父节点值 赋值给 y
            y >>= d;
            while(x != y){
                // 此时 x, y 处于同一高度, while循环找到x, y 的最近公共父节点, 环的长度 += 2
                d += 2;
                x >>= 1;
                y >>= 1;
            }
            ans[i] = d + 1;
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 需要先处理a1, b1 节点所在层次是否相同, 1 , {2, 3}, {4,5,6,7}, {8,9,...,14,15}, n层{2^(n - 1), ..., 2^n - 1}
     * 2. 给完全二叉树添加一条任意边, 应该有且只能构成一个唯一的圆环,
     * 3. 我们要计算的就是a1, b1 到最近一个公共父节点的距离
     * 4. 我们应该从子节点进行倒序推理, 例如子节点a1, b1, 首先判断a1, b1是否处于同一层次(即树的高度), 可以写一个方法求当前节点的高度
     * 5. 如果a1, b1 不处于同一高度, 那么a1 > b1, 即a1 比 b1 低 m 层, 此时将a1拔高一层, 即找到a1的父节点, a1 = a1 / 2, 同时环的长度加1。
     * 6. 此时再次判断a1 和 b1 是否同一高度(并且判断a1 和 b1 是否相等, 如果相等, 则按照 7 进行处理), 如果处于同一高度后, 将两者同时拔高找到其对应父节点 a1 = a1 / 2, b1 = b1 / 2, 环的长度 count += 2
     * 7. 判断此时a1 和 b1 是否相同, 如果不同, 则按照6的步骤继续执行, 如果相同, 则此时已经构成了一个环, 该节点是a1, b1 的最近的公共父节点, 并且count += 1, 外置的查询链路
     *
     * AC: 148ms(7.14%)/90.75MB(21.43%)
     *
     * 感觉又是最垃圾的解法
     * @param n 
     * @param queries 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/7/16 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[][] queries) {
        int len = queries.length;
        int[] ans = new int[len];
        for (int i = 0; i < queries.length; i++) {
            int a = queries[i][0];
            int b = queries[i][1];
            count = 0;
            dfs(a, b);
            ans[i] = count;
        }
        return ans;
    }

    private void dfs(int a, int b) {
        if (a == b) {
            count++;
            // a, b 最近公共父节点
            return;
        } else {
            // a != b
            int aLevel = getHeightOfTreeNode(a);
            int bLevel = getHeightOfTreeNode(b);
            if (aLevel == bLevel) {
                // a, b 的父节点
                count += 2;
                a = a / 2;
                b = b / 2;
            } else if (aLevel < bLevel) {
                b = b / 2;
                count++;
            } else {
                a = a / 2;
                count++;
            }
            dfs(a, b);
        }
    }

    private int getHeightOfTreeNode(int value) {
        int n = 1;
        int currMaxValue = (1 << n) - 1;
        while (value > currMaxValue) {
            n++;
            currMaxValue = (1 << n) - 1;
        }
        return n - 1;
    }
}
