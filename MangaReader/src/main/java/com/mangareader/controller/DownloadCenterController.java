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

        // 验证输入
        if (mangaName.isEmpty()) {
            showAlert("错误", "请输入漫画名称");
            return;
        }

        if (mangaUrl.isEmpty()) {
            showAlert("错误", "请输入漫画目录网址");
            return;
        }

        // 创建 Manga 实体并保存到数据库，状态为待下载(0)
        Manga manga = mangaService.addManga(mangaName, mangaUrl);
        if (manga == null) {
            showAlert("错误", "初始化下载任务失败，请检查数据库或磁盘权限");
            return;
        }

        // 标记为待下载状态
        mangaService.updateMangaStatus(manga.getMangaId(), 0);

        showAlert("成功", "漫画已添加到下载队列，系统将自动处理");
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
