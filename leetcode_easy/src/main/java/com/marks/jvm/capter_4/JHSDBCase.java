package com.marks.jvm.capter_4;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 * JHSDBCase$Case JHSDBCase$ObjectHolder
 * @author marks
 * @version v1.0
 * @date 2025/7/9 15:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class JHSDBCase {
    static class Case {
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done!");
        }
    }

    private static class ObjectHolder{

    }

    public static void main(String[] args) {
        Case cs = new JHSDBCase.Case();
        cs.foo();
    }
}
