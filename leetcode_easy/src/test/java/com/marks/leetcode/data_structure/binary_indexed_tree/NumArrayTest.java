package com.marks.leetcode.data_structure.binary_indexed_tree;

import org.junit.jupiter.api.Test;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/17 16:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class NumArrayTest {

    @Test
    void update() {
        /*
        1. update() => i += i & -i; i < n

        i = 1, 0001 & -1 => 1111 = 0001 = 1
        i += 1, i = 2, 0010 & -2 的二进制是原码取反, 然后 + 1得到补码 1101 + 0001 => 1110
        0010 & 1110 = 0010 = 2 => i += 2 => i = 4

        i = 3, 0011 & -3 1100 + 0001 => 1101, 0011 & 1101 = 0001 = 1
        3 + 1 = 4,i = 4

        i = 5, 0101 & -5 1010 + 0001 => 1011, 0101 & 1011 = 0001 = 1
        i += 1, i = 6

        i = 6, 0110 & -6 1001 + 0001 => 1010, 0110 & 1010 = 0010 = 2
        i += 2, i = 8


        2.pre() ==> i &= i - 1; i > 0
        i = 10, i - 1 = 9; 10 => 1010, 9 => 1001, 1000 => 8, i = 8
        i = 8, i - 1 = 7; 1000 &= 0111 => 0000 = 0

        i = 11, i - 1 = 10; 1011 & 1010, 1010 = 10
         */
//        for (int i = 6; i < 100; i += i & -i) {
//            System.out.println("i =>" + i + ", i toBinary =>" + Integer.toBinaryString(i));
//            System.out.println("-i =>" + -i + ", -i toBinary =>" + Integer.toBinaryString(-i));
//            System.out.println("i & -i =>" + (i & -i));
//            System.out.println("i ===>" + i);
//        }

        NumArray numArray = new NumArray(new int[]{1, 1, 1, 1, 1, 1, 1, 1});
        int result = numArray.sumRange(0, 7);
        System.out.println(result);
        numArray.update(1, 10);
        result = numArray.sumRange(0, 7);
        System.out.println(result);
    }
}