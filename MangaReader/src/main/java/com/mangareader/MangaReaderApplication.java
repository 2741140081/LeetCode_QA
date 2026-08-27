package com.mangareader;

import com.mangareader.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaReaderApplication </p>
 * <p>描述: 漫画阅读器应用主类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/14 10:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@SpringBootApplication
@MapperScan("com.mangareader.mapper")
@EnableConfigurationProperties
@EnableScheduling
public class MangaReaderApplication extends Application {

    private static ConfigurableApplicationContext springContext;


    public static void main(String[] args) {
        // 启动JavaFX应用
        launch(args);
    }

    @Override
    public void init() {
        System.out.println("[JavaFX] 正在启动 Spring Boot 容器...");
        // 初始化Spring容器，完成所有Bean的创建和依赖注入
        springContext = SpringApplication.run(MangaReaderApplication.class);
        System.out.println("[Spring] 容器启动完成，Bean 已就绪");
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("[JavaFX] 正在加载 FXML 界面...");

        // 从 Spring 容器中获取 MainController
        MainController mainController = springContext.getBean(MainController.class);

        // 初始化主窗口
        mainController.initMainStage(primaryStage);

        primaryStage.setTitle("漫画阅读器");
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("[JavaFX] 窗口关闭，停止 Spring 容器...");
            Platform.exit();
            int exitCode = SpringApplication.exit(springContext, () -> 0);
            System.exit(exitCode);
        });
        primaryStage.show();

        System.out.println("[JavaFX] 界面已渲染，可以开始测试");
    }

    @Override
    public void stop() {
        System.out.println("[JavaFX] 应用退出，释放资源...");
        if (springContext != null && springContext.isRunning()) {
            springContext.close();
        }
    }

    /**
     * 静态方法：供外部获取 Spring 容器
     */
    @SuppressWarnings("ignore")
    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
}
