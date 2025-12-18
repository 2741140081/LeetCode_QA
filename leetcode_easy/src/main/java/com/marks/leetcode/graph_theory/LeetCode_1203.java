package com.marks.leetcode.graph_theory;

import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1203 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/18 11:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1203 {

    /**
     * @Description:
     * 有 n 个项目，每个项目或者不属于任何小组，或者属于 m 个小组之一。
     * group[i] 表示第 i 个项目所属的小组，如果第 i 个项目不属于任何小组，则 group[i] 等于 -1。
     * 项目和小组都是从零开始编号的。可能存在小组不负责任何项目，即没有任何项目属于这个小组。
     *
     * 请你帮忙按要求安排这些项目的进度，并返回排序后的项目列表：
     *
     * 同一小组的项目，排序后在列表中彼此相邻。
     * 项目之间存在一定的依赖关系，我们用一个列表 beforeItems 来表示，
     * 其中 beforeItems[i] 表示在进行第 i 个项目前（位于第 i 个项目左侧）应该完成的所有项目。
     * 如果存在多个解决方案，只需要返回其中任意一个即可。如果没有合适的解决方案，就请返回一个 空列表 。
     * @param: n
     * @param: m
     * @param: group
     * @param: beforeItems
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/18 11:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        int[] result;
        result = method_01(n, m, group, beforeItems);
        return result;
    }

    /**
     * @Description:
     * E1
     * 输入：n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3,6],[],[],[]]
     * 输出：[6,3,4,1,5,2,0,7]
     * 1. 先尝试模拟案例E1, 然后再来分析步骤
     * 2. -1: [0, 1, 7]; 0:[3, 4, 6]; 1:[2, 5]
     * 3. 处理beforeItems, 0: []; 1: [6]; 2: [5]; 3: [6]; 4: [3, 6]; 5: []; 6: []; 7: []
     * 可以根据步骤3，将beforeItems 创建一个邻接表, 并且记录节点的入度
     * 4. 还有一个问题是, 排序后需要同一个小组的项目在列表中相邻
     * 5. 考虑使用优先队列, 先将入度为0的节点添加到队列(需要判断该节点是否属于一个小组), 应该是一个双重关系嵌套的
     * 6. 将一个小组添加到结果集合前, 需要判断该小组是否与外部的存在入度关系
     * 7. 需要添加一个入度集合, 存储每一个小组的入度, 并且使用Map<Integer, Integer> map 存储key = 项目编号, value = 小组编号
     * 一个项目只能有一个小组, 一个小组可能有多个项目, 根据 beforeItems, 例如 4: [3, 6] vIn = group[4), vOut = group[3)
     * if vIn != vOut groupInDegree[vIn]++
     * 8. 只有在项目入度和小组入度都为0时, 才添加到结果队列中, 如果项目的入度为0 并且他的小组是-1, 同样添加到队列中
     * 9. 存在问题, 同一组下的项目没有在一起, 而是分开了, 需要重新考虑用一个List<Integer>[] 存储待添加的项目,
     * 只有满足 list[i].size == 小组的项目数, 才添加到结果队列中
     * AC: 37ms/67.15MB
     * @param: n
     * @param: m
     * @param: group
     * @param: beforeItems
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/18 11:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        for (int i = 0; i < n; i++) {
            if (group[i] == -1) {
                group[i] = m; // 先给没有组的项目一个组
                m++;
            }
        }
        // 创建项目邻接表和小组邻接表, 项目入度表, 小组入度表
        List<Integer>[] itemGraph = new List[n];
        for (int i = 0; i < n; i++) {
            itemGraph[i] = new ArrayList<>();
        }
        List<Integer>[] groupGraph = new List[m];
        for (int i = 0; i < m; i++) {
            groupGraph[i] = new ArrayList<>();
        }

        int[] itemInDegree = new int[n];
        int[] groupInDegree = new int[m];
        // 遍历 beforeItems
        for (int currItem = 0; currItem < n; currItem++) {
            for (int j = 0; j < beforeItems.get(currItem).size(); j++) {
                int before = beforeItems.get(currItem).get(j);
                itemGraph[before].add(currItem);
                itemInDegree[currItem]++;
                int currGroupId = group[currItem];
                int beforeGroupId = group[before];
                if (beforeGroupId != currGroupId) {
                    groupGraph[beforeGroupId].add(currGroupId);
                    groupInDegree[currGroupId]++;
                }
            }
        }
        int[] itemSort = new int[n];
        int index = 0;
        // 队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (itemInDegree[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            int currItem = queue.poll();
            itemSort[index++] = currItem;
            for (int nextItem : itemGraph[currItem]) {
                itemInDegree[nextItem]--;
                if (itemInDegree[nextItem] == 0) {
                    queue.add(nextItem);
                }
            }
        }
        List<Integer> itemList = new ArrayList<>();
        if (index == n) {
            itemList = Arrays.stream(itemSort).boxed().toList();
        } else {
            return new int[0];
        }
        int[] groupSort = new int[m];
        index = 0;
        queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            if (groupInDegree[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            int currGroup = queue.poll();
            groupSort[index++] = currGroup;
            for (int nextGroup : groupGraph[currGroup]) {
                groupInDegree[nextGroup]--;
                if (groupInDegree[nextGroup] == 0) {
                    queue.add(nextGroup);
                }
            }
        }
        List<Integer> groupList;
        if (index == m) {
            groupList = Arrays.stream(groupSort).boxed().toList();
        } else {
            return new int[0];
        }

        Map<Integer, List<Integer>> group2Items = new HashMap<>();
        for (int curr : itemSort) {
            int groupId = group[curr];
            group2Items.computeIfAbsent(groupId, k -> new ArrayList<>()).add(curr);
        }
        List<Integer> ans = new ArrayList<>();
        for (int groupId : groupList) {
            List<Integer> items = group2Items.get(groupId);
            if (items != null) {
                ans.addAll(items);
            }
        }


        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

}
