package com.mangareader.handler;


import javafx.application.Platform;
import javafx.scene.control.Alert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: GlobalExceptionHandler </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/14 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Slf4j
@Component
public class GlobalExceptionHandler {

    public void handleUncaughtException(Thread thread, Throwable throwable) {
        log.error("全局未捕获异常，线程:{}", thread.getName(), throwable);
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("程序异常");
            alert.setContentText("程序遇到意外错误，已自动记录日志，可继续正常使用其他功能");
            alert.show();
        });
    }

    @org.aspectj.lang.annotation.AfterThrowing(pointcut = "execution(* com.mangareader.service.*.*(..))", throwing = "e")
    public void handleServiceException(Exception e) {
        log.error("业务服务异常", e);
        // 根据异常类型弹出对应友好提示
    }
}
