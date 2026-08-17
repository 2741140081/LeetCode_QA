package com.mangareader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: TestMainApp </p>
 * <p>描述: 测试应用主类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/17 15:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class TestMainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/test_reader.fxml"));

        Scene scene = new Scene(loader.load(), 1200, 800);
        primaryStage.setTitle("下拉式漫画阅读器测试");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

