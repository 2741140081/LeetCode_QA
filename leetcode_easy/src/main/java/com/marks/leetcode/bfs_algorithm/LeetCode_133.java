package com.marks.leetcode.bfs_algorithm;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_133 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/19 10:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_133 {

    /**
     * @Description:
     * 给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
     *
     * 图中的每个节点都包含它的值 val（int） 和其邻居的列表（list[Node]）。
     *
     * @param: node
     * @return com.marks.utils.Node
     * @author marks
     * @CreateDate: 2025/12/19 10:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Node cloneGraph(Node node) {
        Node result;
        result = method_01(node);
        return result;
    }

    /**
     * @Description:
     * 1. 广度优先搜索
     * 2. 由于可能存在环, 所以需要记录访问过的节点, Set<Node> visited;
     * AC: 26ms/43.88MB
     * @param: node
     * @return com.marks.utils.Node
     * @author marks
     * @CreateDate: 2025/12/19 10:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private Node method_01(Node node) {
        if (node == null) {
            // 如果节点为空, 创建一个空节点
            return null;
        }
        Map<Integer, Node> map = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Node head = new Node();
        head.val = node.val;
        visited.add(node);
        map.put(node.val, head);
        // 队列执行广度优先搜索
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                Node newNode = map.get(cur.val);
                newNode.neighbors = new ArrayList<>();
                for (Node neighbor : cur.neighbors) {
                    // 这里从新构建了节点, 但是有些节点是引用关系, 所以需要判断节点是否存在
                    int neighborVal = neighbor.val;
                    Node tempNode = null;
                    if (map.containsKey(neighborVal)) {
                        newNode.neighbors.add(map.get(neighborVal));
                    } else {
                        tempNode = new Node();
                        tempNode.val = neighbor.val;
                        newNode.neighbors.add(tempNode);
                    }
                    if (!visited.contains(neighbor)) {
                        queue.offer(neighbor);
                        visited.add(neighbor);
                        map.put(neighbor.val, tempNode);
                    }
                }

            }
        }
        return head;
    }

    static class Node {
        public int val;
        public List<Node> neighbors;
    }
}
