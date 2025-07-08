package com.marks.jvm.capter_3;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述:
 * 此代码演示了两点
 * 1. 对象可以在被GC时自我拯救。
 * 2. 这种拯救的机会只有一次, 因为一个对象的finalize() 方法最多只会被系统自动调用一次.
 *
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/8 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, i am still alive :) ");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method execute.");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new FinalizeEscapeGC();

        // 对象第一次拯救自己
        SAVE_HOOK = null;
        System.gc();

        // 因为Finalize 方法优先级低, 暂停0.5秒
        Thread.sleep(500);

        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :( ");
        }


        // 下面这段代码与上面代码一样, 但是输出结果不同
        SAVE_HOOK = null;
        System.gc();

        // 因为Finalize 方法优先级低, 暂停0.5秒
        Thread.sleep(500);

        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :( ");
        }

    }
}
