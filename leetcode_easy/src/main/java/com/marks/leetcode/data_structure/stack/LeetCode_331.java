package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_331 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/29 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_331 {

    /**
     * @Description:
     * 序列化二叉树的一种方法是使用 前序遍历 。当我们遇到一个非空节点时，我们可以记录下这个节点的值。如果它是一个空节点，我们可以使用一个标记值记录，例如 #。
     * 例如，上面的二叉树可以被序列化为字符串 "9,3,4,#,#,1,#,#,2,#,6,#,#"，其中 # 代表一个空节点。
     * 给定一串以逗号分隔的序列，验证它是否是正确的二叉树的前序序列化。编写一个在不重构树的条件下的可行算法。
     * 保证 每个以逗号分隔的字符或为一个整数或为一个表示 null 指针的 '#' 。
     * 你可以认为输入格式总是有效的
     * 例如它永远不会包含两个连续的逗号，比如 "1,,3" 。
     * 注意：不允许重建树。
     * @param: preorder
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/29 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isValidSerialization(String preorder) {
        boolean result;
        result = method_01(preorder);
        return result;
    }

    /**
     * @Description:
     * 1. 先把preorder字符串分割为数组 String[]
     * 2. 前序遍历 根 -> 左子树 -> 右子树 str[i].equals("#")
     * 3. "9,3,4,#,#,1,#,#,2,#,6,#,#"
     * 4. 差不多能理解, 当遇到一个数字时, 这个数字需要占据一个槽位(相当于左右子树中的一个, 并且需要2个槽位作为这个数字的左右子树), 所以需要先减掉一个槽位, 然后添加2个槽位
     * @param: preorder
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/29 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String preorder) {
        // 将preorder进行分割
        String[] str = preorder.split(",");
        int n = str.length;
        // 构建一个栈, 用于模拟树的前序遍历
        int i = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1); // 槽位
        while (i < n) {
            if (stack.isEmpty()) {
                return false;
            }
            if (str[i].equals("#")) {
                int top = stack.pop() - 1;
                if (top > 0) {
                    stack.push(top);
                }
            } else {
                // 数字
                int top = stack.pop() - 1; // 消耗一个槽位
                if (top > 0) {
                    stack.push(top);
                }
                stack.push(2); // 补充2个槽位
            }
            i++;
        }
        return stack.isEmpty();
    }

}
