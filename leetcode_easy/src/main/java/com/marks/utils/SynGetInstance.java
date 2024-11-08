package com.marks.utils;

import com.marks.leetcode.partition_DP.LeetCode_5;

import java.util.HashSet;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/6 17:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SynGetInstance {
    private HashSet inUseInstanceSet = new HashSet();

    private HashSet freeInstanceSet = new HashSet();

    public synchronized void getInstance() {
        String instance = "";
        System.out.println("线程" + Thread.currentThread().getName() + "进入同步方法");
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (freeInstanceSet.size() == 0) {
            instance = Thread.currentThread().getName();
        } else {
            instance = (String) freeInstanceSet.iterator().next();
            freeInstanceSet.remove(instance);
        }
        inUseInstanceSet.add(instance);
    }

    public synchronized void releaseInstance() {
        String pInstance = Thread.currentThread().getName();
        pInstance = pInstance.substring(0, pInstance.length() - 1);
        System.out.println("线程" + Thread.currentThread().getName() + "进入同步方法, 释放" + pInstance);
        inUseInstanceSet.remove(pInstance);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        freeInstanceSet.add(pInstance);
    }

    public static void main(String[] args) {
        SynGetInstance instance_1 = new SynGetInstance();
        SynGetInstance instance_2 = new SynGetInstance();
        Runnable task = () -> instance_1.getInstance();
        Runnable release = () -> instance_2.releaseInstance();
        for (int i = 1; i <= 20; i++) {
            Thread thread = new Thread(task, "线程" + i);
            thread.start();
        }

        for (int i = 1; i <= 20; i++) {
            Thread thread = new Thread(release, "线程" + i + "P");
            thread.start();
        }

    }
}
