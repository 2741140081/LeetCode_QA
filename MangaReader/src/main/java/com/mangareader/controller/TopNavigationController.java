package com.mangareader.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 顶部导航栏控制器
 */
@Component
public class TopNavigationController {

    @Autowired
    private MainController mainController;

    @FXML
    private Button shelfButton;

    @FXML
    private Button downloadCenterButton;

    @FXML
    private void handleSwitchToShelf() {
        mainController.switchToShelf();
        updateActiveButton(shelfButton);
    }

    @FXML
    private void handleSwitchToDownloadCenter() {
        mainController.switchToDownloadCenter();
        updateActiveButton(downloadCenterButton);
    }

    private void updateActiveButton(Button activeButton) {
        // 重置所有按钮样式
        shelfButton.getStyleClass().remove("active");
        downloadCenterButton.getStyleClass().remove("active");

        // 设置当前按钮为活动状态
        activeButton.getStyleClass().add("active");
    }
}
