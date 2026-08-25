package com.mangareader.controller;

import com.mangareader.model.entity.Manga;
import com.mangareader.service.MangaDownloadService;
import com.mangareader.service.MangaService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 下载中心控制器
 */
@Component
public class DownloadCenterController implements Initializable {

    @FXML
    private TextField mangaNameField;

    @FXML
    private TextField mangaUrlField;

    @FXML
    private Spinner<Integer> threadCountSpinner;

    @FXML
    private VBox downloadProgressContainer;

    @Autowired
    private MangaDownloadService mangaDownloadService;

    @Autowired
    private MangaService mangaService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化线程数选择器
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3);
        threadCountSpinner.setValueFactory(valueFactory);
    }

    /**
     * 处理开始下载按钮点击事件
     */
    @FXML
    private void handleStartDownload() throws IOException {
        String mangaName = mangaNameField.getText().trim();
        String mangaUrl = mangaUrlField.getText().trim();
        int threadCount = threadCountSpinner.getValue();

        // 验证输入
        if (mangaName.isEmpty()) {
            showAlert("错误", "请输入漫画名称");
            return;
        }

        if (mangaUrl.isEmpty()) {
            showAlert("错误", "请输入漫画目录网址");
            return;
        }
        // 根据 mangaName 和 mangaUrl 创建 Manga 实体类, 并且保存到数据库, 并且使用 mangaName 创建本地目录
        Manga manga = mangaService.addManga(mangaName, mangaUrl);
        if (manga == null) {
            showAlert("错误", "初始化下载任务失败，请检查数据库或磁盘权限");
            return;
        }

        // 创建下载进度条
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(800);

        Label statusLabel = new Label("准备中...");
        Label progressLabel = new Label("0%");

        Button cancelButton = new Button("取消");
        cancelButton.setOnAction(event -> {
            // TODO: 实现取消下载功能
            statusLabel.setText("已取消");
        });

        HBox progressBox = new HBox(10);
        progressBox.getChildren().addAll(progressBar, statusLabel, progressLabel, cancelButton);

        VBox downloadItem = new VBox(5);
        Label titleLabel = new Label(mangaName);
        titleLabel.setStyle("-fx-font-weight: bold;");
        downloadItem.getChildren().addAll(titleLabel, progressBox);

        downloadProgressContainer.getChildren().add(downloadItem);

        // 开始下载
        mangaDownloadService.downloadManga(manga, threadCount
//                progress -> {
//                    Platform.runLater(() -> {
//                        progressBar.setProgress(progress);
//                        progressLabel.setText(String.format("%.1f%%", progress * 100));
//                        statusLabel.setText("下载中...");
//                    });
//                },
//                () -> {
//                    Platform.runLater(() -> {
//                        statusLabel.setText("下载完成");
//                        cancelButton.setDisable(true);
//                        cancelButton.setText("完成");
//                    });
//                },
//                error -> {
//                    Platform.runLater(() -> {
//                        statusLabel.setText("下载失败: " + error.getMessage());
//                        progressBar.setStyle("-fx-accent: red;");
//                    });
//                }
        );
    }

    /**
     * 显示警告对话框
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
