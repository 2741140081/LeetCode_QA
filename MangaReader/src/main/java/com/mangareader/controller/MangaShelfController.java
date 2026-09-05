package com.mangareader.controller;

import com.mangareader.model.entity.Manga;
import com.mangareader.service.MangaService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 漫画书架控制器
 */

@Slf4j
@Component
public class MangaShelfController implements Initializable {

    @FXML
    private TilePane mangaGrid;

    @Autowired
    private MangaService mangaService;

    @Autowired
    private MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 加载漫画列表
        loadMangaList();
    }

    /**
     * 加载漫画列表到书架
     */
    private void loadMangaList() {
        // 清空当前网格
        mangaGrid.getChildren().clear();

        // 获取漫画列表
        List<Manga> mangaList = mangaService.getAllManga();

        // 为每本漫画创建一个卡片
        for (Manga manga : mangaList) {
            // 创建漫画卡片
            VBox mangaCard = createMangaCard(manga);

            // 添加到网格
            mangaGrid.getChildren().add(mangaCard);
        }
    }

    /**
     * 创建漫画卡片
     * @param manga 漫画对象
     * @return 漫画卡片
     */
    private VBox createMangaCard(Manga manga) {
        // 创建卡片容器
        VBox card = new VBox();
        card.getStyleClass().add("manga-card");
        card.setSpacing(5);

        // 创建封面图片
        ImageView coverView = new ImageView();
        coverView.setFitWidth(150);
        coverView.setFitHeight(200);
        coverView.setPreserveRatio(true);
        coverView.setSmooth(true);

        // 加载封面图片
        // 加载封面图片
        try {
            String coverPath = manga.getCoverImage();
            if (coverPath != null) {
                File coverFile = new File(coverPath);
                if (coverFile.exists()) {
                    Image coverImage = new Image(coverFile.toURI().toString());
                    coverView.setImage(coverImage);
                } else {
                    // 如果封面不存在，使用默认封面
                    Image defaultCover = new Image(getClass().getResourceAsStream("/images/default_cover.png"));
                    coverView.setImage(defaultCover);
                }
            } else {
                // coverImage 为 null，使用默认封面
                Image defaultCover = new Image(getClass().getResourceAsStream("/images/default_cover.png"));
                coverView.setImage(defaultCover);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 如果加载失败，使用默认封面
            try {
                Image defaultCover = new Image(getClass().getResourceAsStream("/images/default_cover.png"));
                coverView.setImage(defaultCover);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // 创建标题标签
        Label titleLabel = new Label(manga.getMangaName());
        titleLabel.getStyleClass().add("manga-title");
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(150);

        // 添加点击事件
        card.setOnMouseClicked(event -> handleMangaClick(manga));

        // 添加组件到卡片
        card.getChildren().addAll(coverView, titleLabel);

        return card;
    }

    /**
     * 处理漫画点击事件
     * @param manga 被点击的漫画
     */
    private void handleMangaClick(Manga manga) {
        // 通知主控制器切换到阅读界面
        mainController.switchToReader(manga.getMangaId());
    }

    /**
     * 处理添加漫画按钮点击事件
     */
    @FXML
    private void handleAddManga() {
        // TODO: 实现添加漫画功能
        System.out.println("添加漫画功能待实现");
    }

    /**
     * 处理刷新按钮点击事件
     */
    @FXML
    private void handleRefresh() {
        // 重新加载漫画列表
        loadMangaList();
    }
}

