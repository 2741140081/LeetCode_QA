package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/26 16:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2471Test {

    @Test
    void minimumOperations() {
        TreeNode root = new TreeNode(1);


        LeetCode_2471 lt = new LeetCode_2471();
        int count = lt.minimumOperations(root);
        System.out.println(count);

    }
}