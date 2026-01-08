package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3387 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/8 9:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3387 {

    /**
     * @Description:
     * 给你一个字符串 initialCurrency，表示初始货币类型，并且你一开始拥有 1.0 单位的 initialCurrency。
     * 另给你四个数组，分别表示货币对（字符串）和汇率（实数）：
     * pairs1[i] = [startCurrencyi, targetCurrencyi] 表示在 第 1 天，可以按照汇率 rates1[i] 将 startCurrencyi 转换为 targetCurrencyi。
     * pairs2[i] = [startCurrencyi, targetCurrencyi] 表示在 第 2 天，可以按照汇率 rates2[i] 将 startCurrencyi 转换为 targetCurrencyi。
     * 此外，每种 targetCurrency 都可以以汇率 1 / rate 转换回对应的 startCurrency。
     * 你可以在 第 1 天 使用 rates1 进行任意次数的兑换（包括 0 次），然后在 第 2 天 使用 rates2 再进行任意次数的兑换（包括 0 次）。
     * 返回在两天兑换后，最大可能拥有的 initialCurrency 的数量。
     * 注意：汇率是有效的，并且第 1 天和第 2 天的汇率之间相互独立，不会产生矛盾。
     * @param: initialCurrency
     * @param: pairs1
     * @param: rates1
     * @param: pairs2
     * @param: rates2
     * @return double
     * @author marks
     * @CreateDate: 2026/01/08 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double maxAmount(String initialCurrency, List<List<String>> pairs1, double[] rates1, List<List<String>> pairs2, double[] rates2) {
        double result;
        result = method_01(initialCurrency, pairs1, rates1, pairs2, rates2);
        return result;
    }

    /**
     * @Description:
     * E1:initialCurrency = "EUR", pairs1 = [["EUR","USD"],["USD","JPY"]], rates1 = [2.0,3.0],
     * pairs2 = [["JPY","USD"],["USD","CHF"],["CHF","EUR"]], rates2 = [4.0,5.0,6.0]
     * mapDay1 = {{EUR, 1.0}, {USD, 2.0}, {JPY, 6.0} }, mapDay2 = {{EUR, 1.0}, {USD, 30.0}, {JPY, 120.0}, {CHF, 6.0} }
     * => EUR = 1.0, USD =  2.0 * 30.0 = 60.0, CHF = 0.0, JPY = 6.0 * 120.0 = 720.0, 所以最大是720.0
     * 1. 需要将货币抽象为节点, 初始货币类型为节点0, 其他类型节点按照下标依次递增, 存储到Map<String, Integer>中 int n = map.size(); // 记录货币种类数
     * 2. int[] days = new int[n]; 记录第一天结束后每种货币的最大数量, 有一个关键点是 汇率是有效的, 即 A0 -> B, B -> C, C -> A1 => A0 == A1 这样才是汇率有效
     * 3. 两天的汇率都需要从 0 开始 进行深度优先搜索或者广度优先搜索, 初始的 的数值为 1.0, int[] days2 存储 第二天结束后每种货币的最大数量
     * 4. double ans = Math.max(ans, days1[i] * days2[i]); 修改由于 第一天和第二天的货币类型不统一, 所以修改 int[] 为 map 来存储
     * 然后计算第一天和第二天共有的货币类型, 得到 ans 结果
     * AC: 8ms/48.95MB
     * spend time: 40min
     * @param: initialCurrency
     * @param: pairs1
     * @param: rates1
     * @param: pairs2
     * @param: rates2
     * @return double
     * @author marks
     * @CreateDate: 2026/01/08 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(String initialCurrency, List<List<String>> pairs1, double[] rates1, List<List<String>> pairs2, double[] rates2) {
        // 存储货币类型, 遍历pairs1, pairs2
        Map<String, Integer> map = new HashMap<>();
        // 添加初始货币类型
        int index = 0;
        map.put(initialCurrency, index++);
        index = getIndex(pairs1, map, index);
        getIndex(pairs2, map, index);
        int n = map.size();
        // 构建第一天的邻接表
        List<Node>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < pairs1.size(); i++) {
            int start = map.get(pairs1.get(i).get(0));
            int target = map.get(pairs1.get(i).get(1));
            double rate = rates1[i];
            graph[start].add(new Node(target, rate));
            graph[target].add(new Node(start, 1.0 / rate));
        }
        // 广度优先搜索获取第一天结束后每种货币的最大数量, 使用 mapDay1 存储
        Map<Integer, Double> mapDay1 = new HashMap<>();
        BFS(0, graph, mapDay1);
        // 构建第二天的邻接表
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < pairs2.size(); i++) {
            // 需要反过来进行汇率计算, 因为需要得到的是初始 initialCurrency 的数量, 所以需要反过来进行汇率计算
            int start = map.get(pairs2.get(i).get(1));
            int target = map.get(pairs2.get(i).get(0));
            double rate = rates2[i];
            graph[start].add(new Node(target, rate));
            graph[target].add(new Node(start, 1.0 / rate));
        }
        // 广度优先搜索获取第二天结束后每种货币的最大数量, 使用 mapDay2 存储
        Map<Integer, Double> mapDay2 = new HashMap<>();
        BFS(0, graph, mapDay2);
        double ans = 0.0;
        // 遍历货币类型, 获取最大数量
        for (int i = 0; i < n; i++) {
            if (mapDay1.containsKey(i) && mapDay2.containsKey(i)) {
                ans = Math.max(ans, mapDay1.get(i) * mapDay2.get(i));
            }
        }
        return ans;
    }

    private static int getIndex(List<List<String>> pairs1, Map<String, Integer> map, int index) {
        for (List<String> pair : pairs1) {
            String startCurrency = pair.get(0);
            String targetCurrency = pair.get(1);
            if (!map.containsKey(startCurrency)) {
                map.put(startCurrency, index++);
            }
            if (!map.containsKey(targetCurrency)) {
                map.put(targetCurrency, index++);
            }
        }
        return index;
    }

    private void BFS(int root, List<Node>[] graph, Map<Integer, Double> mapDay1) {
        Queue<Node> queue = new LinkedList<>();

        queue.offer(new Node(root, 1.0));
        // mapDay1 添加 root
        mapDay1.put(root, 1.0);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int currId = node.id;
            double currNumber = node.rate; // 当前货币数量
            for (Node nextNode : graph[currId]) {
                int nextId = nextNode.id;
                double nextRate = nextNode.rate;
                double nextNumber = currNumber * nextRate;
                if (!mapDay1.containsKey(nextId)) {
                    mapDay1.put(nextId, nextNumber);
                    queue.offer(new Node(nextId, nextNumber));
                } else {
                    mapDay1.put(nextId, Math.max(mapDay1.get(nextId), nextNumber));
                }
            }
        }

    }

    // 内部类, 存储下一个节点 和 汇率
    private class Node {
        int id;
        double rate;
        public Node(int id, double rate) {
            this.id = id;
            this.rate = rate;
        }
    }


}
