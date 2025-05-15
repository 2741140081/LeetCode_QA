package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/14 17:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2471 {

    /**
     * @Description:
     * 给你一个 值互不相同 的二叉树的根节点 root 。
     *
     * 在一步操作中，你可以选择 同一层 上任意两个节点，交换这两个节点的值。
     *
     * 返回每一层按 严格递增顺序 排序所需的最少操作数目。
     *
     * 节点的 层数 是该节点和根节点之间的路径的边数。
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/5/14 17:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumOperations(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description: [功能描述]
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/5/14 17:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode currNode = queue.poll();
                if (currNode.left != null) {
                    queue.offer(currNode.left);
                    list.add(currNode.left.val);
                }

                if (currNode.right != null) {
                    queue.offer(currNode.right);
                    list.add(currNode.right.val);
                }
            }
            if (list.size() > 1) {
                int count = minSwapsForStrictlyIncreasing(list);
                ans += count;
            }
        }

        return ans;
    }

    public int minSwapsForStrictlyIncreasing(List<Integer> nums) {
        int n = nums.size();

        // Step 1: Sort the array to get the target order
        int[] sortedNums = nums.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(sortedNums);

        // Step 2: Create a position map from sorted array to original array indices
        Map<Integer, Integer> posMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            posMap.put(sortedNums[i], i);
        }

        // Step 3: Create a list of positions based on original array order
        List<Integer> positions = new ArrayList<>();
        for (int num : nums) {
            positions.add(posMap.get(num));
        }

        // Step 4: Find cycles in the permutation and count swaps
        List<List<Integer>> cycles = new ArrayList<>();
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> cycle = new ArrayList<>();
                int j = i;
                while (!visited[j]) {
                    visited[j] = true;
                    cycle.add(j);
                    j = positions.get(j);
                }
                cycles.add(cycle);
            }
        }

        // Step 5: Calculate the number of swaps needed for each cycle
        int swaps = 0;
        for (List<Integer> cycle : cycles) {
            if (cycle.size() > 1) { // Only consider cycles of length > 1
                swaps += cycle.size() - 1;
            }
        }

        return swaps;
    }
}
