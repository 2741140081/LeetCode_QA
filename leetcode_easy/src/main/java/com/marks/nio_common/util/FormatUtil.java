package com.marks.nio_common.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: FormatUtil </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 16:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class FormatUtil {
    /**
     * 设置数字格式，保留有效小数位数为fractions
     *
     * @param fractions 保留有效小数位数
     * @return 数字格式
     */
    public static DecimalFormat decimalFormat(int fractions)
    {

        DecimalFormat df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(fractions);
        df.setMaximumFractionDigits(fractions);
        return df;
    }
}
