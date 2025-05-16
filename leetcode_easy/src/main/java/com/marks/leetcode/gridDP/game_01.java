package com.marks.leetcode.gridDP;

public class game_01 {
    public String getMaxDamage(){
        String result = "";
        // 基础攻击伤害 500万
        int baseAttack = 500;
        // 攻击次数
        int attackNumber = 2000;
        // 固定值, 只能携带5个物品
        int k = 5;
        // 物品A, 即基础攻击变成 750,0000
        double increaseRate = 0.5;
        // 物品B, 暴击倍率, 但是暴击几率为50%
        int magnification = 3;

        // 求5个物品栏分别携带A、B物品数量个多少, 能使得伤害最大化
        result = method_01(baseAttack, attackNumber, k, increaseRate, magnification);
        return null;
    }

    private String method_01(int baseAttack, int attackNumber, int k, double increaseRate, int magnification) {
        // 带物品A时的伤害量
        long[] dp = new long[k + 1];
        for (int i = 0; i <= k; i++) {
            int j = k - i;
            double currAttack = baseAttack + baseAttack * increaseRate * i;
            // 不暴击部分 + 暴击部分
            if (j == 0) {
                dp[i] = (long) (currAttack * attackNumber);
            }else {
                dp[i] =(long) (currAttack * attackNumber / 2 + currAttack * attackNumber / 2 * j * magnification);
            }
        }
        return null;
    }
}
