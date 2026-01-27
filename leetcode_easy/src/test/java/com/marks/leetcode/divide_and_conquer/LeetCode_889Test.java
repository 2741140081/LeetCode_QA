package com.marks.leetcode.divide_and_conquer;

import com.marks.utils.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_889Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/26 17:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_889Test {

    @Test
    void constructFromPrePost() {
        // 输入：preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
        // 输出：[1,2,3,4,5,6,7]
        int[] preorder = {1,2,4,5,3,6,7};
        int[] postorder = {4,5,2,6,7,3,1};
        LeetCode_889 leetCode_889 = new LeetCode_889();
        TreeNode treeNode = leetCode_889.constructFromPrePost(preorder, postorder);
    }
}