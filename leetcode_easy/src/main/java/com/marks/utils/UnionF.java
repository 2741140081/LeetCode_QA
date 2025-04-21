package com.marks.utils;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/17 16:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class UnionF {
    private int[] root;
    private int[] rank;
    private int count;

    public UnionF(int size) {
        root = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            root[i] = i;
            rank[i] = 1;
        }
        count = size;
    }

    public int find(int x) {
        if (root[x] == x) {
            return x;
        }
        return root[x] = find(root[x]);
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                root[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                root[rootX] = rootY;
            } else {
                root[rootY] = rootX;
                rank[rootX] += 1;
            }
            count--;
        }
    }

    public int getCount() {
        return count;
    }

    public boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }

    public int[] getRoot() {
        return root;
    }

    public int[] getRank() {
        return rank;
    }
}
