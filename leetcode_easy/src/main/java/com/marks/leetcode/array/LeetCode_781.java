package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_781 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/8 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_781 {

    /**
     * @Description:
     * 森林中有未知数量的兔子。提问其中若干只兔子 "还有多少只其它兔子与你（指被提问的兔子）颜色相同?" ，
     * 将答案收集到一个整数数组 answers 中，其中 answers[i] 是第 i 只兔子的回答。
     * 给你数组 answers ，返回森林中兔子的最少数量。
     *
     * tips:
     * 1 <= answers.length <= 1000
     * 0 <= answers[i] < 1000
     * @param: answers
     * @return int
     * @author marks
     * @CreateDate: 2026/05/08 15:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numRabbits(int[] answers) {
        int result;
        result = method_01(answers);
        return result;
    }

    /**
     * @Description:
     * 1. 排序后的思路有问题, 不是这么简单的
     * 2. 排序还是需要的, 但是由于answers 只是森林中部分兔子的回答, 所有需要对数据进行特殊处理
     * 3. answers[i] == 0, 说明这是一个单独的颜色, 对结果的共享为1
     * 4. answers[i] > 0, 说明有i+1只兔子是同一种颜色, 对结果的共享为i+1, 并且需要考虑当前有多少个相同数量的 answers[i] 下标,
     * 使用 count 记录 相同数量的 answers[i], 接下来需要分析 count 与 answers[i] 的关系
     * 5. if answers[i] + 1 == count; 那么贡献为 answers[i] + 1
     * 6. if answers[i] + 1 < count; 那么贡献为 (count + answers[i]) / (answers[i] + 1) * (answers[i] + 1), 这个化简后为 count + answers[i] + 1.
     * 假设 answers[i] = 1, count = 10; 总共享是 10 + 1 + 1 = 12;
     * 7. if answers[i] + 1 > count; 那么贡献为 answers[i] + 1;
     * 8. 现在不需要进行排序, 而是使用 map 存储值和数量, 然后进行遍历
     * 9. 犯了一个经验的错误, (count + answers[i]) / (answers[i] + 1) * (answers[i] + 1), 不能直接约分, 这样导致错误
     * AC: 4ms/43.53MB
     * @param: answers
     * @return int
     * @author marks
     * @CreateDate: 2026/05/08 15:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] answers) {
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int answer : answers) {
            // 特殊处理 0
            if (answer == 0) {
                ans++;
            } else {
                map.merge(answer, 1, Integer::sum);
            }
        }
        // 遍历map
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();
            if (num + 1 >= count) {
                ans += (num + 1);
            } else {
                int sum = count + num;
                sum -= sum % (num + 1);
                ans += sum;
                // 等价与 ans += ((count + num)/(num + 1)) * (num + 1);
            }
        }
        
        return ans;
    }

}
