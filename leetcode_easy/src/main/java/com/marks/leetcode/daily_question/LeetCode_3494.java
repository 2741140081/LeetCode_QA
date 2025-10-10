package com.marks.leetcode.daily_question;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/9 15:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3494 {

    
    /**
     * @Description:
     * 给你两个长度分别为 n 和 m 的整数数组 skill 和 mana 。
     * 在一个实验室里，有 n 个巫师，他们必须按顺序酿造 m 个药水。
     * 每个药水的法力值为 mana[j]，并且每个药水 必须 依次通过 所有 巫师处理，才能完成酿造。
     * 第 i 个巫师在第 j 个药水上处理需要的时间为 timeij = skill[i] * mana[j]。
     *
     * 由于酿造过程非常精细，药水在当前巫师完成工作后 必须 立即传递给下一个巫师并开始处理。
     * 这意味着时间必须保持 同步，确保每个巫师在药水到达时 马上 开始工作。
     *
     * 返回酿造所有药水所需的 最短 总时间。
     * @param skill 
     * @param mana
     * @return long
     * @author marks
     * @CreateDate: 2025/10/9 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minTime(int[] skill, int[] mana) {
        long result;
        result = method_01(skill, mana);
//        result = method_02(skill, mana);
        return result;
    }


    /**
     * @Description:
     * 超时 O(n ^ 3)
     * @param skill
     * @param mana
     * @return long
     * @author marks
     * @CreateDate: 2025/10/9 17:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(int[] skill, int[] mana) {
        int n = skill.length;
        int m = mana.length;
        long[] prev = new long[n + 1]; // 添加一个开始时间 存储在下标0处
        long[] curr = new long[n + 1];

        for (int i = 0; i < n; i++) {
            // 制作第0个药水的时间
            prev[i + 1] = prev[i] + (long) skill[i] * mana[0];
        }
        long sub;
        for (int i = 1; i < m; i++) {
            // 制作第i个药水需要的时间
            curr[0] = prev[1];
            sub = 0;
            boolean flag = true;
            while (flag) {
                curr[0] += sub;
                for (int j = 0; j < n; j++) {
                    curr[j + 1] = curr[j] + (long) skill[j] * mana[i];
                    if (j < n - 1 && curr[j + 1] < prev[j + 2]) {
                        // 无法进入下一个巫师处, 因为还在制作中
                        sub = prev[j + 2] - curr[j + 1];
                        break;
                    }

                }
                if (curr[n] != 0) {
                    flag = false;
                }
            }
            prev = curr;
            curr = new long[n + 1];
        }
        return prev[n];
    }


    /**
     * @Description:
     * E1:
     * 输入： skill = [1,5,2,4], mana = [5,1,4,2]
     * 输出： 110
     * 药水编号	开始时间	巫师 0 完成时间	巫师 1 完成时间	巫师 2 完成时间	巫师 3 完成时间
     * 0	0	5	30	40	60
     * 1	52	53	58	60	64
     * 2	54	58	78	86	102
     * 3	86	88	98	102	110
     * 举个例子，为什么巫师 0 不能在时间 t = 52 前开始处理第 1 个药水，假设巫师们在时间 t = 50 开始准备第 1 个药水。
     * 时间 t = 58 时，巫师 2 已经完成了第 1 个药水的处理，但巫师 3 直到时间 t = 60 仍在处理第 0 个药水，无法马上开始处理第 1个药水。
     * 1. 感觉是动态规划, 但是完全没有思路
     * 2. 画了一下图， 感觉稍微理解了一点, 药水的制造期间是无法停止的, 也就说假设巫师1在制造完成药水1后需要立即交给巫师2, 没有等待时间
     * 3. 下一个开始的药水, 假设药水2在巫师1出完成时间是 53s, 那么药水3的开始时间不是53s, 而是 54s 开始进行制作
     * 4. 只需要关注与相邻的两个药水的制作过程, 当我们要制作药水2时, 药水2只需要与药水1的制作时间没有交集即可, 不需要管其他药水的制作
     * 5. 当药水2与药水1的时间存在交集时, 需要推迟药水2的制作时间, 例如药水1在第4个巫师处的完成时间是 60s, 但是药水2在第三个巫师完成时间是42s,
     * 药水2无法到达巫师4处进行制作, 此时需要让药水2的完成时间与药水1在巫师4处完成时间相同, 即药水2的开始制作时间需要推迟 60 - 42 = 18s,
     * 即药水2的开始时间 t = 34 + 18 = 52s
     *
     * 错误!!1
     * @param skill 
     * @param mana 
     * @return long
     * @author marks
     * @CreateDate: 2025/10/9 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] skill, int[] mana) {
        int n = skill.length;
        int m = mana.length;
        long[] prev = new long[n + 1]; // 添加一个开始时间 存储在下标0处
        long[] curr = new long[n + 1];

        for (int i = 0; i < n; i++) {
            // 制作第0个药水的时间
            prev[i + 1] = prev[i] + (long) skill[i] * mana[0];
        }
        for (int i = 1; i < m; i++) {
            if (mana[i] > mana[i - 1]) {
                // 直接基于prev[1] 进行计算, 不可能存在交集
                curr[0] = prev[1];
                for (int j = 0; j < n - 1; j++) {
                    curr[j + 1] = Math.max(prev[j + 2], curr[j] + (long) skill[j] * mana[i]);
                }
                curr[n] = curr[n - 1] + (long) skill[n - 1] * mana[i];
            } else {
                // 制作第i个药水需要的时间
                curr[n] = prev[n] + (long) skill[n - 1] * mana[i];

                for (int j = n - 1; j >= 0; j--) {
                    curr[j] = curr[j + 1] - (long) skill[j] * mana[i];
                }
            }

            prev = curr;
            curr = new long[n + 1];
        }
        return prev[n];
    }

}
