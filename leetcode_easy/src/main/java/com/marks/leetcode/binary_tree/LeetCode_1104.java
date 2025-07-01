package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/6/4 15:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1104 {
    /**
     * @Description:
     * 在一棵无限的二叉树上，每个节点都有两个子节点，树中的节点 逐行 依次按 “之” 字形进行标记。
     * 如下图所示，在奇数行（即，第一行、第三行、第五行……）中，按从左到右的顺序进行标记；
     * 而偶数行（即，第二行、第四行、第六行……）中，按从右到左的顺序进行标记。
     * 给你树上某一个节点的标号 label，请你返回从根节点到该标号为 label 节点的路径，该路径是由途经的节点标号所组成的。
     *
     * tips:
     * 1 <= label <= 10^6
     * @param label
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/6/4 15:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> pathInZigZagTree(int label) {
        List<Integer> result;
        result = method_01(label);
        return result;
    }

    /**
     * @Description:
     * 1. 我需要找出当前lable所在的层, 假设第m层
     * 2. 需要判断当前层是奇数层还是偶数层
     * 3. 假设层数从1开始, 第i层最后一个 label = 2^n - 1;
     * 4.
     *
     * 查看官解 0ms/40.13MB
     * @param label
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/6/4 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int label) {
        int row = 1, rowStart = 1;
        while (rowStart * 2 <= label) {
            row++;
            rowStart *= 2;
        }
        if (row % 2 == 0) {
            label = getReverse(label, row);
        }
        List<Integer> path = new ArrayList<Integer>();
        while (row > 0) {
            if (row % 2 == 0) {
                path.add(getReverse(label, row));
            } else {
                path.add(label);
            }
            row--;
            label >>= 1;
        }
        Collections.reverse(path);
        return path;
    }

    public int getReverse(int label, int row) {
        return (1 << row - 1) + (1 << row) - 1 - label;
    }
}
