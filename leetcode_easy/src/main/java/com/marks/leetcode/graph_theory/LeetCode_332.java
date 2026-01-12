package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_332 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/12 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_332 {

    /**
     * @Description:
     * 给你一份航线列表 tickets ，其中 tickets[i] = [fromi, toi] 表示飞机出发和降落的机场地点。请你对该行程进行重新规划排序。
     * 所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，所以该行程必须从 JFK 开始。
     * 如果存在多种有效的行程，请你按字典排序返回最小的行程组合。
     * 例如，行程 ["JFK", "LGA"] 与 ["JFK", "LGB"] 相比就更小，排序更靠前。
     * 假定所有机票至少存在一种合理的行程。且所有的机票 必须都用一次 且 只能用一次。
     * tips:
     * 1 <= tickets.length <= 300
     * tickets[i].length == 2
     * fromi.length == 3
     * toi.length == 3
     * fromi 和 toi 由大写英文字母组成
     * fromi != toi
     * @param: tickets
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/01/12 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> findItinerary(List<List<String>> tickets) {
        List<String> result;
//        result = method_01(tickets);
        result = method_02(tickets);
        return result;
    }



    Map<String, PriorityQueue<String>> map = new HashMap<String, PriorityQueue<String>>();
    List<String> itinerary = new LinkedList<String>();

    /**
     * @Description:
     * 1. 欧拉回路, 后续需要自行学习 need todo
     * AC: 8ms/46.37MB
     * @param: tickets
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/01/12 14:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_02(List<List<String>> tickets) {

        for (List<String> ticket : tickets) {
            String src = ticket.get(0), dst = ticket.get(1);
            if (!map.containsKey(src)) {
                map.put(src, new PriorityQueue<String>());
            }
            map.get(src).offer(dst);
        }
        dfs("JFK");
        Collections.reverse(itinerary);
        return itinerary;
    }

    public void dfs(String curr) {
        while (map.containsKey(curr) && !map.get(curr).isEmpty()) {
            String tmp = map.get(curr).poll();
            dfs(tmp);
        }
        itinerary.add(curr);
    }

    /**
     * @Description:
     * 1. 每张机票都需要被使用, 并且初始的机场是 JFK, 将机场名称和 index 进行对应
     * 2. 构建邻接表, 构建有向图, 节点编号从 0 到 n - 1.
     * 3. 进行深度优先搜索, 从 JFK 开始进行搜索, 用一个栈存储结果 stack. 结束条件是 stack.size() == n
     * 4. 同时也需要考虑重复遍历的问题, 所以需要一个 visited 数组进行标记, 因为可能构成环, 所以不能单纯只使用 parent 进行判断
     * 5. 超时!!! 指数的时间的时间复杂度还是太高了,
     * 6. 查看官方题解. method_02()
     * @param: tickets
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/01/12 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int n;
    private List<String> ans = null;

    private List<String> method_01(List<List<String>> tickets) {
        this.n = tickets.size() + 1; // 方便进行判断是否完成所有机票

        // 添加visited, visited[][] 存在问题, 无法处理多个相同的机票
        Map<String, TreeMap<String, Integer>> ticketCount = new HashMap<>();
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            ticketCount.putIfAbsent(from, new TreeMap<>());
            ticketCount.get(from).put(to, ticketCount.get(from).getOrDefault(to, 0) + 1);
        }

        List<String> stack = new ArrayList<>(); // List 模拟栈, 存储结果
        stack.add("JFK");
        // 深度优先搜索 + 回溯
        backTrackDFS(ticketCount, stack);

        return ans;
    }

    private void backTrackDFS(Map<String, TreeMap<String, Integer>> ticketCount, List<String> stack) {
        if (ans != null) {
            // 已经找到解, 不用继续遍历了, 提前剪枝
            return;
        }
        if (stack.size() == n) {
            ans = new ArrayList<>(stack);
            return;
        }

        String curr = stack.get(stack.size() - 1); // 当前所在机场
        TreeMap<String, Integer> destinations = ticketCount.get(curr);

        if (destinations != null) {
            for (Map.Entry<String, Integer> entry : destinations.entrySet()) {
                String next = entry.getKey();
                if (entry.getValue() > 0) {  // 还有可用票
                    stack.add(next);
                    entry.setValue(entry.getValue() - 1);  // 使用一张票

                    backTrackDFS(ticketCount, stack);

                    if (ans != null) return;  // 找到答案提前退出

                    stack.remove(stack.size() - 1);
                    entry.setValue(entry.getValue() + 1);  // 回溯恢复票数
                }
            }
        }
    }

}
