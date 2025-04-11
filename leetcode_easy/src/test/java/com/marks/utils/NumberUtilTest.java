package com.marks.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/11 15:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class NumberUtilTest {

    @Test
    void gcd() {
        int x = 18;
        int y = 6;
        int gcd = NumberUtil.gcd(x, y);
        System.out.println(gcd);

        System.out.println(NumberUtil.gcd(y, x));

    }
}