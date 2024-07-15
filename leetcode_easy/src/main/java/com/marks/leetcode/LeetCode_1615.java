package com.marks.leetcode;

public class LeetCode_1615 {
    public static void main(String[] args) {
        int n = 4;
        int[][] roads = {{0,1},{0,3},{1,2},{1,3}};
        System.out.println(maximalNetworkRank(n, roads));
    }

    public static int maximalNetworkRank(int n, int[][] roads) {
        if (roads.length == 0){
            return 0;
        }
        boolean[][] cont = new boolean[n][n];
        int[] degree = new int[n];
        for (int[] road : roads) {
            cont[road[0]][road[1]] = true;
            cont[road[1]][road[0]] = true;
            degree[road[0]]++;
            degree[road[1]]++;
        }

        int maxSum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int count = degree[i] + degree[j] - (cont[i][j] ? 1 : 0);
                maxSum = Math.max(maxSum, count);
            }
        }
        return maxSum;
    }
}


