package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2288 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/22 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2288 {
    
    /**
     * @Description:
     * 句子 是由若干个单词组成的字符串，单词之间用单个空格分隔，其中每个单词可以包含数字、小写字母、和美元符号 '$' 。
     * 如果单词的形式为美元符号后跟着一个非负实数，那么这个单词就表示一个 价格 。
     * 例如 "$100"、"$23" 和 "$6" 表示价格，而 "100"、"$" 和 "$1e5 不是。
     * 给你一个字符串 sentence 表示一个句子和一个整数 discount 。对于每个表示价格的单词，都在价格的基础上减免 discount% ，并 更新 该单词到句子中。
     * 所有更新后的价格应该表示为一个 恰好保留小数点后两位 的数字。
     * 返回表示修改后句子的字符串。
     * 注意：所有价格 最多 为 10 位数字。
     *
     * tips:
     * 1 <= sentence.length <= 10^5
     * sentence 由小写英文字母、数字、' ' 和 '$' 组成
     * sentence 不含前导和尾随空格
     * sentence 的所有单词都用单个空格分隔
     * 所有价格都是 正 整数且不含前导零
     * 所有价格 最多 为  10 位数字
     * 0 <= discount <= 100
     * @param: sentence
     * @param: discount
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/22 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String discountPrices(String sentence, int discount) {
        String result;
        result = method_01(sentence, discount);
        return result;
    }
    
    /**
     * @Description:
     * 1. 只处理合法的金额, 即 $ + num + 空格 的形式.
     * 2. 直接进行字符串分割, 然后对分割后的字符串判断是合法的金额格式.
     * 3. 处理合法的金额, 对其进行打折操作, 并且将结果保存。
     * AC: 95ms/51.7MB
     * @param: sentence
     * @param: discount
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/22 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String sentence, int discount) {
        String[] split = sentence.split(" ");
        for (int i = 0; i < split.length; i++) {
            String word = split[i];
            if (word.startsWith("$")) {
                if (word.length() < 2) {
                    continue;
                }
                double price = 0;
                boolean isPrice = true;
                for (int j = 1; j < word.length(); j++) {
                    if (word.charAt(j) >= '0' && word.charAt(j) <= '9') {
                        price = price * 10 + (word.charAt(j) - '0');
                    } else {
                        isPrice = false;
                        break;
                    }
                }
                if (isPrice) {
                    price = price * (1 - discount * 1.0 / 100);
                    split[i] = "$" + String.format("%.2f", price);
                }

            }
        }
        return String.join(" ", split);
    }

}
