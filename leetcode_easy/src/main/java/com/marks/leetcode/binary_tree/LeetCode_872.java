package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/14 15:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_872 {
    /**
     * @Description: [功能描述]
     * @param root1
     * @param root2
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/14 15:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        boolean result;
        result = method_01(root1, root2);
        return result;
    }

    /**
     * @Description: [功能描述]
     * @param root1
     * @param root2
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/14 15:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(TreeNode root1, TreeNode root2) {
        List<Integer> list_1 = new ArrayList<>();
        List<Integer> list_2 = new ArrayList<>();

        dfs(root1, list_1);
        dfs(root2, list_2);

        if (list_1.size() != list_2.size()) {
            return false;
        } else {
            int n = list_1.size();
            for (int i = 0; i < n; i++) {

//                if (list_1.get(i) != list_2.get(i)) {
                /*
                 * 在Java中，Integer对象是通过值缓存机制来优化性能和内存使用的。
                 * 对于值在-128到127之间的Integer对象，Java会缓存这些对象，所以当你在这个范围内创建Integer对象时，实际上是在获取缓存中的对象引用。
                 * 然而，对于超出这个范围的值（如200），每次创建Integer对象时都会创建一个新的对象实例。
                 * 在你的代码中，list_1和list_2都添加了值2和200。
                 * 由于2在-128到127的缓存范围内，所以两个列表中的2很可能是同一个对象引用（尽管这取决于JVM的具体实现和状态，但在大多数情况下会是这样）。
                 * 然而，200不在缓存范围内，所以每次创建时都会是一个新的对象。
                 * 但是，这并不影响list_1和list_2的比较结果。
                 * 在Java中，当你使用==比较两个Integer对象时，你实际上是在比较它们的引用是否相同。
                 * 但是，当你使用!=或equals方法比较时，你比较的是它们的值是否相等。
                 * 在你的代码中，你使用了!=来比较list_1.get(i)和list_2.get(i)的值，这是正确的做法，因为你想比较的是它们的数值是否相等，而不是引用是否相同。
                 * */

                // 应该使用 equals 来进行比较值,
                if (!list_1.get(i).equals(list_2.get(i))) {
                    return false;
                }

            }
            return true;
        }
    }

    private void dfs(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            // 叶子节点
            list.add(root.val);
        }
        dfs(root.left, list);
        dfs(root.right, list);
    }
}
