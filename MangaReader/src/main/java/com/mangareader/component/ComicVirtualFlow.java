package com.mangareader.component;


import com.mangareader.service.ImageService;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.CacheHint;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ComicVirtualFlow </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/14 11:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Component
public class ComicVirtualFlow extends ListView<String> {

    private final ImageService imageService;
    private double currentScale = 1.0;

    public ComicVirtualFlow(ImageService imageService) {
        this.imageService = imageService;
        initialize();
    }

    private void initialize() {
        // 设置单元格工厂
        setCellFactory(param -> new ImageCell());

        // 设置背景透明
        setStyle("-fx-background-color: transparent;");
        setCache(true);
        setCacheHint(CacheHint.SPEED);
    }

    /**
     * 设置图片路径列表
     * @param imagePaths 图片路径列表
     * @param scale 缩放比例
     */
    public void setImageData(ObservableList<String> imagePaths, double scale) {
        this.currentScale = scale;
        setItems(imagePaths);
    }

    public void updateScale(double newScale) {
        this.currentScale = newScale;
        refresh();
    }

    /**
     * 自定义图片单元格
     */
    private class ImageCell extends ListCell<String> {
        private final ImageView imageView = new ImageView();
        private final ProgressIndicator loadingIndicator = new ProgressIndicator(30);
        private final StackPane container = new StackPane();
        private CompletableFuture<?> currentLoadTask;

        public ImageCell() {
            imageView.setPreserveRatio(true);
            imageView.fitWidthProperty().bind(widthProperty());
            container.getChildren().addAll(imageView, loadingIndicator);
            loadingIndicator.setVisible(false);
        }

        @Override
        protected void updateItem(String imagePath, boolean empty) {
            super.updateItem(imagePath, empty);

            // 取消之前的加载任务
            if (currentLoadTask != null && !currentLoadTask.isDone()) {
                currentLoadTask.cancel(true);
            }

            if (empty || imagePath == null) {
                setGraphic(null);
                return;
            }

            // 显示加载状态
            loadingIndicator.setVisible(true);
            imageView.setImage(null);
            setGraphic(container);

            final String currentPath = imagePath;
            currentLoadTask = imageService.loadImageAsync(currentPath, currentScale)
                    .thenAcceptAsync(image -> {
                        if (image != null && currentPath.equals(getItem()) && !isEmpty()) {
                            loadingIndicator.setVisible(false);
                            imageView.setImage(image);
                        }
                    }, Platform::runLater)
                    .exceptionally(e -> {
                        if (currentPath.equals(getItem())) {
                            loadingIndicator.setVisible(false);
                            // 显示错误占位图
                        }
                        return null;
                    });
        }
    }
}
