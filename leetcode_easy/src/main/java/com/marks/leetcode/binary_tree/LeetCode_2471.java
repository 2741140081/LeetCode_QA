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
     * @Description:
     *
     * AC: 67ms/58.84MB
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


    /**
     * @Description:
     * 通过排序对比 + HashMap来计算交换的最少次数
     * [7, 6, 8, 5]
     * [0, 1, 2, 3]
     *
     * [5, 6, 7, 8]
     *
     * =>
     * [7, 6, 8, 7]
     *
     * i = 2,
     * pos = 3,
     *
     * [7, 6, 8, 8]
     *
     *
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/5/30 17:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int getSwapsBySortArray(List<Integer> nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.size();
        int[] array = new int[n];
        for (int i = 0; i < nums.size(); i++) {
            array[i] = nums.get(i);
            map.put(nums.get(i), i);
        }
        // java 8 的stream api
//         int[] array = nums.stream().mapToInt(Integer::intValue).toArray();

        // get sort array
        int[] sorted = Arrays.copyOf(array, n);
        Arrays.sort(sorted);

        int ans = 0;
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i] != array[i]) {
                ans++;
                int pos = map.get(sorted[i]);
                map.put(array[i], pos);
                map.put(sorted[i], i);

                array[pos] = array[i];
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
