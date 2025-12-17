package com.marks.netty.nio_demo;

import com.marks.netty.nio_common.util.Logger;

import java.nio.IntBuffer;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: UseBuffer </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/2 16:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class UseBuffer {
    private static IntBuffer intBuffer;

    public static void main(String[] args) {
        UseBuffer ub = new UseBuffer();
        ub.method_01();
        ub.method_02();
        ub.method_03();
        ub.method_04();
        ub.method_05();
    }

    private void method_05() {
        intBuffer.rewind(); // 倒带
        Logger.debug("====== after rewind =========");
        Logger.debug("limit: " + intBuffer.limit());
        Logger.debug("capacity: " + intBuffer.capacity());
        Logger.debug("position: " + intBuffer.position());

    }

    private void method_04() {
        Logger.debug("====== after get 2 =========");
        for (int i = 0; i < 2; i++) {
            Logger.debug("i:" + intBuffer.get());
        }
        Logger.debug("limit: " + intBuffer.limit());
        Logger.debug("capacity: " + intBuffer.capacity());
        Logger.debug("position: " + intBuffer.position());
        Logger.debug("====== after get 3 =========");
        for (int i = 0; i < 3; i++) {
            Logger.debug("i:" + intBuffer.get());
        }
        Logger.debug("limit: " + intBuffer.limit());
        Logger.debug("capacity: " + intBuffer.capacity());
        Logger.debug("position: " + intBuffer.position());
    }

    private void method_03() {
        intBuffer.flip();
        Logger.debug("====== after flip =========");
        Logger.debug("capacity: " + intBuffer.capacity());
        Logger.debug("limit: " + intBuffer.limit());
        Logger.debug("position: " + intBuffer.position());
    }

    private void method_02() {

        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        Logger.debug("====== after put =========");
        Logger.debug("capacity: " + intBuffer.capacity());
        Logger.debug("limit: " + intBuffer.limit());
        Logger.debug("position: " + intBuffer.position());
    }

    private void method_01() {
        intBuffer = IntBuffer.allocate(20);
        Logger.debug("====== after allocate =========");
        Logger.debug("capacity: " + intBuffer.capacity());
        Logger.debug("limit: " + intBuffer.limit());
        Logger.debug("position: " + intBuffer.position());
    }
}
