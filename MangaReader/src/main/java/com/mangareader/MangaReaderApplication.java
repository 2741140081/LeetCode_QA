package com.mangareader;

import com.mangareader.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaReaderApplication </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/14 10:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@SpringBootApplication
public class MangaReaderApplication extends Application {

    private static ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        // 初始化Spring容器，完成所有Bean的创建和依赖注入
        springContext = SpringApplication.run(MangaReaderApplication.class);
    }

    @Override
    public void start(Stage primaryStage) {
        // 注册全局未捕获异常兜底处理
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            springContext.getBean(com.mangareader.handler.GlobalExceptionHandler.class)
                    .handleUncaughtException(thread, throwable);
        });
        // 从Spring容器获取MainController，加载主界面
        MainController mainController = springContext.getBean(MainController.class);
        mainController.initMainStage(primaryStage);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // 关闭Spring容器，释放所有资源
        springContext.close();
        Platform.exit();
        System.exit(0);
    }

    public static Object getSpringBean(Class<?> beanClass) {
        return springContext.getBean(beanClass);
    }

    public static void main(String[] args) {
        // 启动JavaFX应用
        launch(args);
    }
}
