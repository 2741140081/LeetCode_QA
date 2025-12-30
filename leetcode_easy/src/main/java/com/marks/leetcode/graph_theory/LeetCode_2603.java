package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2603 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/30 14:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2603 {

    /**
     * @Description:
     * 给你一个 n 个节点的无向无根树，节点编号从 0 到 n - 1 。
     * 给你整数 n 和一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间有一条边。
     * 再给你一个长度为 n 的数组 coins ，其中 coins[i] 可能为 0 也可能为 1 ，1 表示节点 i 处有一个金币。
     *
     * 一开始，你需要选择树中任意一个节点出发。你可以执行下述操作任意次：
     *
     * 收集距离当前节点距离为 2 以内的所有金币，或者
     * 移动到树中一个相邻节点。
     * 你需要收集树中所有的金币，并且回到出发节点，请你返回最少经过的边数。
     *
     * 如果你多次经过一条边，每一次经过都会给答案加一。
     * tips:
     * n == coins.length
     * 1 <= n <= 3 * 10^4
     * 0 <= coins[i] <= 1
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * ai != bi
     * edges 表示一棵合法的树。
     * @param: coins
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2025/12/30 14:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int collectTheCoins(int[] coins, int[][] edges) {
        int result;
//        result = method_01(coins, edges);
        result = method_02(coins, edges);
        return result;
    }

    /**
     * @Description:
     * 1. 删除无用的叶子节点, 入度为1 并且 不含有金币 的节点
     * 2. 删除两次, 因为可以在距离为2的节点处收集金币, 所以只需要删除最外层的叶子节点两次即可
     * 3. 最终剩余的节点数 rest, 从剩余节点的任意一点出发, 访问所有其它节点, 并且返回开始节点, 总的步数为
     * 2 * (剩余的节点数 - 1), 因为这是一颗树, 树中不存在环路
     * @param: coins
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2025/12/30 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] coins, int[][] edges) {
        int n = coins.length;
        List<Integer>[] graph = new ArrayList[n]; // 构建邻接表
        int[] degree = new int[n]; // 节点的入度
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            graph[x].add(y);
            graph[y].add(x);
            degree[x]++;
            degree[y]++;
        }
        // 删除无用的叶子节点, 入度为1, coins[i] == 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1 && coins[i] == 0) {
                queue.offer(i);
            }
        }
        int rest = n; // 剩余的节点数
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            degree[curr]--;
            rest--;
            for (int next_i : graph[curr]) {
                degree[next_i]--;
                if (degree[next_i] == 1 && coins[next_i] == 0) {
                    queue.offer(next_i);
                }
            }
        }
        // 连续删除两次, 因为可以在距离为2的节点处收集金币, 所以只需要删除最外层的叶子节点两次即可
        for (int i = 0; i < 2; i++) {
            queue = new LinkedList<>();
            for (int j = 0; j < n; j++) {
                if (degree[j] == 1) {
                    queue.offer(j);
                }
            }
            while (!queue.isEmpty()) {
                int curr = queue.poll();
                degree[curr]--;
                rest--;
                for (int next_i : graph[curr]) {
                    // 更新节点的入度
                    degree[next_i]--;
                }
            }
        }
        return rest == 0 ? 0 : (rest - 1) * 2; // (剩余的节点数 - 1) * 2, 因为这个是树, 不存在环路
    }

    /**
     * @Description:
     * 1. 创建邻接表, 收集金币是不需要消耗步数的。int target 记录sum(coins[i])
     * 2. 是否从任意一点出发都是可行的最小值, 感觉应该不是, 所以我需要for 循环遍历所有节点
     * 3. 使用广度优先搜索, 使用step 记录步数, int[] ans 记录节点的最小步数, 最终返回结果为 ans[i] 的最小值
     * 4. 当遇到coins[i] == 1 时, ans[i] += (step - 2) * 2(如果step > 2), 并且更新 lastStep = step(上一次收集金币的步数)
     * 5. 队列中存储元素 int[] curr = {next_i, curr_step, last_step}, last_step 初始化为2,
     * 当step <= last_step 时, 直接添加金币而不用给ans[i] 进行增加值
     * 6. 存在问题, A, B 节点都有金币, 并且他们的公共父节点 C, 可以在C处收集A B 的金币, 但是当前代码会重复计算A B 的消耗的路程
     * 7. 怎么解决公共父节点的问题? 查看官方题解, 见method_02
     * @param: coins
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2025/12/30 14:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] coins, int[][] edges) {
        int n = coins.length; // n 个节点
        int[] ans = new int[n]; // 记录节点的最小步数
        int target = Arrays.stream(coins).sum(); // 目标金币数量, 如果当前sum 金币数量 == target, 则返回
        // 构建邻接表
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            // 无向图
            graph[x].add(y);
            graph[y].add(x);
        }
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            // 从每一个节点出发, 找到最小节点的步数
            int step = 0; // 广度优先搜索的步数
            int sum = 0; // 记录金币数量
            int lastStep = 2; // 记录上一次收集时的步数
            Queue<int[]> queue = new LinkedList<>(); // 构建队列
            queue.offer(new int[]{i, step, lastStep});
            boolean[] visited = new boolean[n]; // 节点是否访问过
            visited[i] = true;
            while (!queue.isEmpty() && sum < target) {
                int[] curr = queue.poll();
                int curr_i = curr[0];
                step = curr[1];
                lastStep = curr[2];
                if (coins[curr_i] == 1) {
                    if (step > lastStep) {
                        ans[i] += (step - lastStep) * 2;
                        lastStep = step;
                        sum++; // 添加金币
                    }
                }
                for (int next_i : graph[curr_i]) {
                    // 处理下一个
                    if (!visited[next_i]) {
                        visited[next_i] = true;
                        queue.offer(new int[]{next_i, step + 1, lastStep});
                    }
                }
            }

            result = Math.min(result, ans[i]);
        }
        return result;
    }

}
