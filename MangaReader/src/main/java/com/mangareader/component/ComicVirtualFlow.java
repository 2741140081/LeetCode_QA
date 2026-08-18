package com.mangareader.component;


import com.mangareader.service.ImageService;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.skin.VirtualFlow;
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
     * 获取当前可见的图片索引
     * @return 当前可见的图片索引
     */
    public int getCurrentVisibleIndex() {
        ObservableList<String> items = getItems();
        if (items == null || items.isEmpty()) {
            return 0;
        }

        // 优先使用实际可见单元格
        ListCell<String> firstVisibleCell = getFirstVisibleCell();
        if (firstVisibleCell != null) {
            return firstVisibleCell.getIndex();
        }

        // 备用方案：从滚动位置估算
        return estimateVisibleIndexFromScroll();
    }

    /**
     * 获取第一个可见的单元格
     * @return 第一个可见的单元格，如果不存在返回 null
     */
    private ListCell<String> getFirstVisibleCell() {
        if (getSkin() instanceof ListViewSkin<?> skin) {
            try {
                @SuppressWarnings("unchecked")
                VirtualFlow<ListCell<String>> virtualFlow =
                        (VirtualFlow<ListCell<String>>) skin.getChildren().get(0);

                // 获取第一个可见的单元格
                int firstVisibleIndex = virtualFlow.getFirstVisibleCell().getIndex();
                return virtualFlow.getCell(firstVisibleIndex);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 从滚动位置估算可见索引
     * @return 估算的可见索引
     */
    private int estimateVisibleIndexFromScroll() {
        double scrollRatio = getScrollTop();
        ObservableList<String> items = getItems();

        if (items == null || items.isEmpty()) {
            return 0;
        }

        // 使用第一个单元格的高度作为参考
        double estimatedCellHeight = estimateCellHeight();
        if (estimatedCellHeight <= 0) {
            return 0;
        }

        // 获取实际渲染的单元格数量
        int visibleCellCount = getVisibleCellCount();
        if (visibleCellCount <= 0) {
            return 0;
        }

        // 计算实际可见区域的高度
        double viewportHeight = getHeight();

        // 计算滚动位置对应的索引
        // 使用可见单元格的平均高度作为参考
        double avgCellHeight = viewportHeight / visibleCellCount;
        int estimatedIndex = (int) (scrollRatio * items.size());

        // 边界约束
        return Math.max(0, Math.min(estimatedIndex, items.size() - 1));
    }

    /**
     * 获取可见单元格数量
     * @return 可见单元格数量
     */
    private int getVisibleCellCount() {
        if (getSkin() instanceof ListViewSkin<?> skin) {
            try {
                @SuppressWarnings("unchecked")
                VirtualFlow<ListCell<String>> virtualFlow =
                        (VirtualFlow<ListCell<String>>) skin.getChildren().get(0);

                // 获取第一个和最后一个可见单元格
                int firstIndex = virtualFlow.getFirstVisibleCell().getIndex();
                int lastIndex = virtualFlow.getLastVisibleCell().getIndex();

                return lastIndex - firstIndex + 1;
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 估算单元格高度
     * @return 估算的单元格高度
     */
    private double estimateCellHeight() {
        for (int i = 0; i < Math.min(10, getItems().size()); i++) {
            ListCell<String> cell = lookupCell(i);
            if (cell != null && cell.getHeight() > 0) {
                return cell.getHeight();
            }
        }
        return 100;
    }

    /**
     * 获取当前滚动位置
     * @return 滚动位置
     */
    private double getScrollTop() {
        for (Node node : lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar scrollBar &&
                    scrollBar.getOrientation() == Orientation.VERTICAL) {
                return scrollBar.getValue();
            }
        }
        return 0;
    }

    /**
     * 查找指定索引的单元格
     * @param index 单元格索引
     * @return 单元格对象，如果不存在返回 null
     */
    private ListCell<String> lookupCell(int index) {
        if (getSkin() instanceof ListViewSkin<?> skin) {
            try {
                @SuppressWarnings("unchecked")
                VirtualFlow<ListCell<String>> virtualFlow =
                        (VirtualFlow<ListCell<String>>) skin.getChildren().get(0);
                return virtualFlow.getCell(index);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
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
