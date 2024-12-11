package com.marks.utils;

import java.util.Arrays;

/**
 * <p>项目名称: 并查集 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/11 11:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class UnionFind {
    private int[] parent;
    private int[] size;
    private int n;
    private int setCount;

    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        size = new int[n];
        Arrays.fill(size, 1);
        this.n = n;
        setCount = n;
    }

    public int findSet(int x) {
        return parent[x] == x ? x : (parent[x] = findSet(parent[x]));
    }

    public void unite(int x, int y) {
        if (size[x] < size[y]) {
            int temp = x;
            x = y;
            y = temp;
        }
        parent[y] = x;
        size[x] += size[y];
        --setCount;
    }


    public boolean findAndUnite(int x, int y) {
        int parentX = findSet(x);
        int parentY = findSet(y);
        if (parentX != parentY) {
            unite(parentX, parentY);
            return true;
        }
        return false;
    }

}
