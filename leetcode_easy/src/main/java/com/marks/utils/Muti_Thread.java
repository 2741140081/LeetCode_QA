package com.marks.utils;

/**
 * <p>项目名称:
 * 单线程不会存在同步问题，
 * 1.3sCD, 这边100不会存在问题
 * 2.30sCD,
 * 获取当前数量, 开启多线程, 执行方法
 *
 * </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/11 14:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Muti_Thread implements Runnable {
    private int tickets = 100;

    @Override
    public void run() {
        while (true) {

            if (tickets > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "卖票：" + tickets);
                tickets--;

            } else {
                break; // 如果票已卖完，则退出循环
            }
        }
    }

    public static void main(String[] args) {
        Muti_Thread test = new Muti_Thread();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(test, "线程" + i);
            thread.start();
        }
    }


}
