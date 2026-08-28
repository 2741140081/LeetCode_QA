package com.mangareader.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 主控制器，管理应用的主要界面切换
 */

@Slf4j
@Component
public class MainController {

    private Stage primaryStage;
    private Parent mangaShelfView;
    private Parent readerView;
    private Parent downloadCenterView; // 添加下载中心视图

    @Autowired
    private ApplicationContext applicationContext;

    private ReaderController readerController;

    private Scene scene;

    /**
     * 初始化主窗口
     * @param primaryStage 主窗口
     */
    public void initMainStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

        try {
            // 加载书架界面
            FXMLLoader shelfLoader = new FXMLLoader(getClass().getResource("/fxml/manga_shelf.fxml"));
            // 设置控制器工厂，让 Spring 创建 Controller 实例
            shelfLoader.setControllerFactory(applicationContext::getBean);
            mangaShelfView = shelfLoader.load();

            // 加载阅读界面
            FXMLLoader readerLoader = new FXMLLoader(getClass().getResource("/fxml/manga_reader.fxml"));
            // 设置控制器工厂，让 Spring 创建 Controller 实例
            readerLoader.setControllerFactory(applicationContext::getBean);
            readerView = readerLoader.load();
            // 获取阅读器控制器
            readerController = readerLoader.getController();

            // 加载下载中心界面
            FXMLLoader downloadLoader = new FXMLLoader(getClass().getResource("/fxml/download_center.fxml"));
            // 设置控制器工厂，让 Spring 创建 Controller 实例
            downloadLoader.setControllerFactory(applicationContext::getBean);
            downloadCenterView = downloadLoader.load();

            // 修改开始：创建一次 Scene，设置初始 Root 为书架视图
            this.scene = new Scene(mangaShelfView, 1200, 800);
            this.scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            primaryStage.setScene(this.scene);
            // 修改结束

            primaryStage.setTitle("漫画阅读器");
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载界面失败: {}", e.getMessage());
        }
    }

    /**
     * 切换到阅读界面
     * @param mangaId 漫画ID
     */
    public void switchToReader(Long mangaId) {
        try {
            // 加载漫画
            readerController.loadManga(mangaId);

            // 修改开始：直接设置 Scene 的 Root
            this.scene.setRoot(readerView);
            // 修改结束

        } catch (Exception e) {
            e.printStackTrace();
            log.error("切换到阅读界面失败: {}", e.getMessage());
        }
    }

    /**
     * 切换回书架界面
     */
    public void switchToShelf() {
        // 修改开始：直接设置 Scene 的 Root，而不是创建新 Scene
        this.scene.setRoot(mangaShelfView);
    }

    /**
     * 切换到下载中心
     */
    public void switchToDownloadCenter() {
        this.scene.setRoot(downloadCenterView);
    }
}
