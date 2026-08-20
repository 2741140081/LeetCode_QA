package com.mangareader.controller;

import com.mangareader.component.ComicVirtualFlow;
import com.mangareader.config.AutoplayConfig;
import com.mangareader.model.entity.Chapter;
import com.mangareader.model.entity.Manga;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.service.ChapterService;
import com.mangareader.service.MangaImageService;
import com.mangareader.service.MangaService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.util.Duration.millis;

/**
 * 漫画阅读器控制器
 */
@Component
public class ReaderController implements Initializable {
    @FXML
    private BorderPane testRoot;
    @FXML
    private StackPane comicContainer;
    @FXML
    private Label statusLabel;
    @FXML
    private Label pageCountLabel;
    @FXML
    private Label currentPageLabel;
    @FXML
    private Label autoPlayStatusLabel;
    @FXML
    private ListView<Chapter> chapterListView;
    @FXML
    private Button prevChapterBtn;
    @FXML
    private Button nextChapterBtn;
    @FXML
    private Button autoPlayBtn;
    @FXML
    private Button speedUpBtn;
    @FXML
    private Button speedDownBtn;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private MangaImageService mangaImageService;

    @Autowired
    private AutoplayConfig autoplayConfig;

    @Autowired
    private MainController mainController;

    @Autowired
    private MangaService mangaService;

    // 漫画ID，实际应用中应该从参数或上下文中获取
    private static final Long MANGA_ID = 1L;

    // 当前章节
    private Chapter currentChapter;

    // 虚拟滚动组件
    private ComicVirtualFlow virtualFlow;

    // 自动播放相关
    private boolean isAutoPlaying = false;
    private double scrollDistance;  // 每次滚动的距离(像素)
    private int scrollInterval;  // 滚动间隔时间(毫秒)
    private Timeline autoPlayTimeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusLabel.setText("当前加载状态：正在加载章节列表...");

        // 从 Spring 上下文获取 ComicVirtualFlow
        virtualFlow = applicationContext.getBean(ComicVirtualFlow.class);

        // 初始化自动播放参数
        scrollDistance = autoplayConfig.getDefaultScrollDistance();
        scrollInterval = autoplayConfig.getDefaultScrollInterval();

        // 将组件添加到容器中
        StackPane.setMargin(virtualFlow, new Insets(0));
        comicContainer.getChildren().add(virtualFlow);

        // 加载章节列表
        loadChapterList();

        // 设置章节列表点击事件
        setupChapterListViewListener();

        // 监听滚动事件
        setupScrollListener(virtualFlow);

        // 默认加载第一章
        if (!chapterListView.getItems().isEmpty()) {
            chapterListView.getSelectionModel().select(0);
            loadChapter(chapterListView.getItems().get(0));
        }
    }

    /**
     * 加载章节列表
     */
    private void loadChapterList() {
        List<Chapter> chapters = chapterService.getChaptersByMangaId(MANGA_ID);

        // 设置章节列表的单元格工厂，显示章节标题
        chapterListView.setCellFactory(param -> new ListCell<Chapter>() {
            @Override
            protected void updateItem(Chapter chapter, boolean empty) {
                super.updateItem(chapter, empty);
                if (empty || chapter == null) {
                    setText(null);
                } else {
                    setText(chapter.getTitle());
                }
            }
        });

        // 添加章节到列表
        chapterListView.getItems().addAll(chapters);

        statusLabel.setText("当前加载状态：已加载 " + chapters.size() + " 个章节");
    }

    /**
     * 设置章节列表点击事件
     */
    private void setupChapterListViewListener() {
        chapterListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadChapter(newVal);
            }
        });
    }

    /**
     * 加载章节内容
     * @param chapter 章节对象
     */
    private void loadChapter(Chapter chapter) {
        // 如果正在自动播放，先停止
        if (isAutoPlaying) {
            toggleAutoPlay();
        }

        currentChapter = chapter;
        statusLabel.setText("当前加载状态：正在加载 " + chapter.getTitle() + "...");

        // 获取章节图片列表
        List<MangaImage> images = mangaImageService.getImagesByChapterId(chapter.getChapterId());

        // 转换为图片路径列表
        ObservableList<String> imagePaths = FXCollections.observableArrayList();
        for (MangaImage image : images) {
            // 使用MangaImageService获取完整的图片路径
            String fullPath = mangaImageService.getFullImagePath(image.getImageUrl());
            imagePaths.add(fullPath);
        }

        // 设置图片数据
        virtualFlow.setImageData(imagePaths, 1.0);

        // 更新页面信息
        pageCountLabel.setText("总图片数：" + images.size());
        currentPageLabel.setText("当前浏览：第 1 张");

        statusLabel.setText("当前加载状态：" + chapter.getTitle() + " 已加载完成");

        // 更新按钮状态
        updateButtonStates();
    }

    /**
     * 设置滚动监听器
     * @param virtualFlow 虚拟滚动组件
     */
    private void setupScrollListener(ComicVirtualFlow virtualFlow) {
        // 监听 Skin 创建事件
        virtualFlow.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                // Skin 创建后查找滚动条并添加监听器
                setupScrollBarListener(virtualFlow);
            }
        });

        // 如果 Skin 已经存在，直接设置监听器
        if (virtualFlow.getSkin() != null) {
            setupScrollBarListener(virtualFlow);
        }
    }

    /**
     * 设置滚动条监听器
     * @param virtualFlow 虚拟滚动组件
     */
    private void setupScrollBarListener(ComicVirtualFlow virtualFlow) {
        // 查找垂直滚动条
        for (Node node : virtualFlow.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar scrollBar &&
                    scrollBar.getOrientation() == Orientation.VERTICAL) {

                // 确保滚动条属于漫画图片区域，而不是章节目录
                if (isScrollBarBelongToVirtualFlow(scrollBar, virtualFlow)) {
                    // 监听滚动条值变化
                    scrollBar.valueProperty().addListener((scrollObs, oldVal, newVal) -> {
                        updateCurrentPageLabel(virtualFlow);

                        // 如果正在自动播放，检查是否已经滚动到最后一张
                        if (isAutoPlaying) {
                            checkAutoPlayEnd(virtualFlow);
                        }
                    });

                    break; // 找到正确的滚动条后退出循环
                }
            }
        }
    }

    /**
     * 更新当前页码标签
     * @param virtualFlow 虚拟滚动组件
     */
    private void updateCurrentPageLabel(ComicVirtualFlow virtualFlow) {
        int currentIdx = virtualFlow.getCurrentVisibleIndex();
        currentPageLabel.setText("当前浏览：第 " + (currentIdx + 1) + " 张");
    }

    /**
     * 更新按钮状态
     */
    private void updateButtonStates() {
        // 检查是否有上一章
        Chapter prevChapter = chapterService.getPrevChapter(MANGA_ID, currentChapter.getChapterNum());
        prevChapterBtn.setDisable(prevChapter == null);

        // 检查是否有下一章
        Chapter nextChapter = chapterService.getNextChapter(MANGA_ID, currentChapter.getChapterNum());
        nextChapterBtn.setDisable(nextChapter == null);
    }

    /**
     * 处理上一章按钮点击事件
     */
    @FXML
    private void handlePrevChapter() {
        Chapter prevChapter = chapterService.getPrevChapter(MANGA_ID, currentChapter.getChapterNum());
        if (prevChapter != null) {
            // 在章节列表中选中上一章
            chapterListView.getSelectionModel().select(prevChapter);
        }
    }

    /**
     * 处理下一章按钮点击事件
     */
    @FXML
    private void handleNextChapter() {
        Chapter nextChapter = chapterService.getNextChapter(MANGA_ID, currentChapter.getChapterNum());
        if (nextChapter != null) {
            // 在章节列表中选中下一章
            chapterListView.getSelectionModel().select(nextChapter);
        }
    }

    /**
     * 处理自动播放按钮点击事件
     */
    @FXML
    private void handleAutoPlay() {
        toggleAutoPlay();
    }

    /**
     * 切换自动播放状态
     */
    private void toggleAutoPlay() {
        isAutoPlaying = !isAutoPlaying;
        if (isAutoPlaying) {
            // 显示加速/减速按钮
            speedUpBtn.setVisible(true);
            speedDownBtn.setVisible(true);
            // 更新状态标签
            autoPlayStatusLabel.setText("自动播放：开启 (滚动距离: " + scrollDistance + "px, 间隔: " + scrollInterval + "毫秒");
            // 创建自动播放时间轴
            autoPlayTimeline = new Timeline(
                    new KeyFrame(millis(scrollInterval),
                            event -> scrollToNextImage())
            );
            autoPlayTimeline.setCycleCount(Animation.INDEFINITE);
            autoPlayTimeline.play();
        } else {
            // 隐藏加速/减速按钮
            speedUpBtn.setVisible(false);
            speedDownBtn.setVisible(false);
            // 更新状态标签
            autoPlayStatusLabel.setText("自动播放：关闭");
            // 停止自动播放
            if (autoPlayTimeline != null) {
                autoPlayTimeline.stop();
                autoPlayTimeline = null;
            }
        }
    }

    /**
     * 滚动到下一张图片（模拟鼠标滚轮滚动）
     */
    private void scrollToNextImage() {
        // 查找垂直滚动条
        for (Node node : virtualFlow.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar scrollBar &&
                    scrollBar.getOrientation() == Orientation.VERTICAL) {
                // 确保滚动条属于漫画图片区域，而不是章节目录
                // 通过检查滚动条的父节点是否为virtualFlow来判断
                if (isScrollBarBelongToVirtualFlow(scrollBar, virtualFlow)) {
                    // 模拟滚轮事件，向下滚动一定距离
                    // 使用Platform.runLater确保在JavaFX应用线程上执行
                    Platform.runLater(() -> {
                        double value = scrollBar.getValue();
                        scrollBar.setUnitIncrement(scrollDistance);
                        scrollBar.increment();
                    });
                    break; // 找到正确的滚动条后退出循环
                }
            }
        }
    }

    /**
     * 检查自动播放是否结束
     * @param virtualFlow 虚拟滚动组件
     */
    private void checkAutoPlayEnd(ComicVirtualFlow virtualFlow) {
        int currentIdx = virtualFlow.getCurrentVisibleIndex();
        int totalImages = virtualFlow.getImageCount();

        // 如果已经滚动到最后一张图片
        if (currentIdx >= totalImages - 1) {
            // 检查是否有下一章
            Chapter nextChapter = chapterService.getNextChapter(MANGA_ID, currentChapter.getChapterNum());
            if (nextChapter != null) {
                // 自动跳转到下一章
                chapterListView.getSelectionModel().select(nextChapter);
            } else {
                // 没有下一章，退出自动播放模式
                toggleAutoPlay();
            }
        }
    }

    /**
     * 处理加速按钮点击事件
     */
    @FXML
    private void handleSpeedUp() {
        // 增加滚动距离或减少滚动间隔，加快播放速度
        if (scrollDistance < autoplayConfig.getMaxScrollDistance()) {
            scrollDistance = Math.min(scrollDistance + autoplayConfig.getScrollDistanceStep(), autoplayConfig.getMaxScrollDistance());
        }
        // 更新状态标签
        autoPlayStatusLabel.setText("自动播放：开启 (滚动距离: " + scrollDistance + "px, 间隔: " + scrollInterval + "毫秒");
        // 如果正在自动播放，需要重启时间轴以应用新的速度
        if (isAutoPlaying) {
            autoPlayTimeline.stop();
            autoPlayTimeline = new Timeline(
                    new KeyFrame(millis(scrollInterval),
                            event -> scrollToNextImage()
                    )
            );
            autoPlayTimeline.setCycleCount(Animation.INDEFINITE);
            autoPlayTimeline.play();
        }
    }


    /**
     * 处理减速按钮点击事件
     */
    @FXML
    private void handleSpeedDown() {
        // 减少滚动距离
        if (scrollDistance > autoplayConfig.getMinScrollDistance()) {
            scrollDistance = Math.max(scrollDistance - autoplayConfig.getScrollDistanceStep(), autoplayConfig.getMinScrollDistance());
        }
        // 更新状态标签
        autoPlayStatusLabel.setText("自动播放：开启 (滚动距离: " + scrollDistance + "px, 间隔: " + scrollInterval + "毫秒");
        // 如果正在自动播放，需要重启时间轴以应用新的速度
        if (isAutoPlaying) {
            autoPlayTimeline.stop();
            autoPlayTimeline = new Timeline(
                    new KeyFrame(millis(scrollInterval),
                            event -> scrollToNextImage()
                    )
            );
            autoPlayTimeline.setCycleCount(Animation.INDEFINITE);
            autoPlayTimeline.play();
        }
    }


    /**
     * 检查滚动条是否属于指定的虚拟滚动组件
     * @param scrollBar 要检查的滚动条
     * @param virtualFlow 虚拟滚动组件
     * @return 如果滚动条属于虚拟滚动组件则返回true，否则返回false
     */
    private boolean isScrollBarBelongToVirtualFlow(ScrollBar scrollBar, ComicVirtualFlow virtualFlow) {
        // 获取滚动条的父节点
        Parent parent = scrollBar.getParent();

        // 向上遍历父节点链，检查是否包含virtualFlow
        while (parent != null) {
            if (parent == virtualFlow) {
                return true;
            }
            parent = parent.getParent();
        }

        return false;
    }

    /**
     * 加载漫画
     * @param mangaId 漫画ID
     */
    public void loadManga(Long mangaId) {
        // 获取漫画信息
        Manga manga = mangaService.getMangaById(mangaId);

        if (manga != null) {
            // 更新状态标签
            statusLabel.setText("当前漫画: " + manga.getMangaName());

            // 加载漫画的第一章
            List<Chapter> chapters = chapterService.getChaptersByMangaId(mangaId);

            // 清空章节列表
            chapterListView.getItems().clear();

            // 添加章节到列表
            chapterListView.getItems().addAll(chapters);

            if (!chapters.isEmpty()) {
                // 加载第一章
                loadChapter(chapters.get(0));
            }
        } else {
            statusLabel.setText("加载漫画失败: 找不到ID为 " + mangaId + " 的漫画");
        }
    }

    /**
     * 处理返回书架按钮点击事件
     */
    @FXML
    private void handleBackToShelf() {
        // 通知主控制器切换回书架界面
        mainController.switchToShelf();
    }
}
