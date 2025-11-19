package com.marks.schedule.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: SimpleScheduleTask </p>
 * <p>描述: 定时任务案例 - 每天上午10:20执行 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/18 9:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SimpleScheduleTask {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * 启动定时任务 - 每天上午10:20执行
     */
    public void startDailyTask() {
        Runnable task = () -> {
            System.out.println("定时任务执行时间: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            // 在这里添加您需要执行的具体业务逻辑
            executeBusinessLogic();
        };

        // 计算下次执行时间（今天或明天的10:20）
        long initialDelay = getNextExecutionTime();

        // 每天执行一次（24小时）
        scheduler.scheduleAtFixedRate(task, initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);

        System.out.println("定时任务已启动，将在每天上午10:20执行");
    }

    /**
     * 计算距离下次执行的时间（秒）
     * @return 延迟秒数
     */
    private long getNextExecutionTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withHour(10).withMinute(20).withSecond(0);

        // 如果当前时间已经过了今天的10:20，则设置为明天的10:20
        if (now.compareTo(nextRun) > 0) {
            nextRun = nextRun.plusDays(1);
        }

        return java.time.Duration.between(now, nextRun).getSeconds();
    }

    /**
     * 实际业务逻辑执行方法
     */
    private void executeBusinessLogic() {
        // 示例业务逻辑
        System.out.println("正在执行具体的业务任务...");
        // 添加您的实际业务代码
    }

    /**
     * 停止定时任务
     */
    public void stopTask() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("定时任务已停止");
        }
    }

    /**
     * 主方法 - 测试定时任务
     */
    public static void main(String[] args) {
        SimpleScheduleTask scheduleTask = new SimpleScheduleTask();
        scheduleTask.startDailyTask();

        // 添加关闭钩子，程序退出时停止定时任务
        Runtime.getRuntime().addShutdownHook(new Thread(scheduleTask::stopTask));
    }
}
