package com.marks.leetcode.bfs_algorithm;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_690 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/23 10:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_690 {

    /**
     * @Description:
     * 你有一个保存员工信息的数据结构，它包含了员工唯一的 id ，重要度和直系下属的 id 。
     *
     * 给定一个员工数组 employees，其中：
     *
     * employees[i].id 是第 i 个员工的 ID。
     * employees[i].importance 是第 i 个员工的重要度。
     * employees[i].subordinates 是第 i 名员工的直接下属的 ID 列表。
     * 给定一个整数 id 表示一个员工的 ID，返回这个员工和他所有下属的重要度的 总和。
     * @param: employees
     * @param: id
     * @return int
     * @author marks
     * @CreateDate: 2025/12/23 11:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getImportance(List<Employee> employees, int id) {
        int result;
        result = method_01(employees, id);
        return result;
    }

    /**
     * @Description:
     * 1. 每一个员工的 id 是唯一的。所以需要用Map<id, Employee>来存储员工信息
     * 2. 有点担心员工直接的关系是否会构成环路, 所以用一个boolean数组来记录员工是否被访问过
     * AC: 5ms/47.3MB
     * @param: employees
     * @param: id
     * @return int
     * @author marks
     * @CreateDate: 2025/12/23 11:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<Employee> employees, int id) {
        Map<Integer, Employee> employeeInfo = new HashMap<>();
        int maxId = 0;
        for (Employee employee : employees) {
            employeeInfo.put(employee.id, employee);
            maxId = Math.max(maxId, employee.id);
        }
        boolean[] visited = new boolean[maxId + 1];
        // 创建一个队列
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(id);
        visited[id] = true;
        int ans = 0; // 保存结果
        while (!queue.isEmpty()) {
            int curId = queue.poll();
            Employee employee = employeeInfo.get(curId);
            ans += employee.importance;
            for (Integer subId : employee.subordinates) {
                if (visited[subId]) {
                    continue;
                }
                queue.offer(subId);
                visited[subId] = true;
            }
        }

        return ans;
    }

    class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    }

}
