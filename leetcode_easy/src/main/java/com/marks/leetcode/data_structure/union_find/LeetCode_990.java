package com.marks.leetcode.data_structure.union_find;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/10 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_990 {
    /**
     * @Description:
     * 给定一个由表示变量之间关系的字符串方程组成的数组，每个字符串方程 equations[i] 的长度为 4，并采用两种不同的形式之一：
     * "a==b" 或 "a!=b"。在这里，a 和 b 是小写字母（不一定不同），表示单字母变量名。
     *
     * 只有当可以将整数分配给变量名，以便满足所有给定的方程时才返回 true，否则返回 false。
     * @param equations
     * @return boolean
     * @author marks
     * @CreateDate: 2025/3/10 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean equationsPossible(String[] equations) {
        boolean result;
        result = method_01(equations);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：["a==b","b!=c","c==a"]
     * 输出：false
     *
     * 并查集:
     * AC: 1ms/41.10MB
     * @param equations
     * @return boolean
     * @author marks
     * @CreateDate: 2025/3/10 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String[] equations) {
        List<String> neq = new ArrayList<>();
        UF uf = new UF(26);
        for (String equation : equations) {
            int x = equation.charAt(0) - 'a';
            int y = equation.charAt(3) - 'a';
            char op = equation.charAt(1);
            if (op == '=') {
                uf.unite(x, y);
                uf.unite(y, x);
            } else {
                neq.add(equation);
            }
        }
        for (String equation : neq) {
            int x = equation.charAt(0) - 'a';
            int y = equation.charAt(3) - 'a';
            if (uf.isUnite(x, y) || uf.isUnite(y, x)) {
                return false;
            }
        }
        return true;
    }

    static class UF {
        private int[] parent;

        public UF(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public void unite(int x, int y) {
            parent[findSet(y)] = findSet(x);
        }

        public int findSet(int x) {
            return parent[x] == x ? x : (parent[x] = findSet(parent[x]));
        }

        public boolean isUnite(int x, int y) {
            int parentX = findSet(x);
            int parentY = findSet(y);

            if (parentX == parentY) {
                return true;
            } else {
                return false;
            }
        }
    }
}
