package com.mangareader.controller;

import com.mangareader.component.ComicVirtualFlow;
import com.mangareader.service.ImageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: TestReaderController </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/17 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Component
public class TestReaderController implements Initializable {
    @FXML
    private BorderPane testRoot;
    @FXML
    private StackPane comicContainer;
    @FXML
    private ScrollPane testScrollPane;
    @FXML
    private ComicVirtualFlow testVirtualFlow;
    @FXML
    private Label statusLabel;
    @FXML
    private Label pageCountLabel;
    @FXML
    private Label currentPageLabel;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ApplicationContext applicationContext;

    // 测试图片存放路径，替换成你本地100张测试图的实际目录
    private static final String TEST_IMAGE_DIR = "D:/images/TestManga";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusLabel.setText("当前加载状态：正在扫描测试图片...");

        // 从 Spring 上下文获取 ComicVirtualFlow
        ComicVirtualFlow virtualFlow = applicationContext.getBean(ComicVirtualFlow.class);

        // 将组件添加到容器中
        StackPane.setMargin(virtualFlow, new Insets(0));
        comicContainer.getChildren().add(virtualFlow);

        // 扫描本地100张测试图，生成路径列表
        List<String> testImagePaths = scanTestImages(TEST_IMAGE_DIR);
        pageCountLabel.setText("总图片数：" + testImagePaths.size());

        ObservableList<String> observablePaths = FXCollections.observableArrayList(testImagePaths);
        virtualFlow.setImageData(observablePaths, 1.0);

        // 监听滚动事件
        virtualFlow.skinProperty().addListener((obs, oldVal, newVal) -> {
            int currentIdx = virtualFlow.getCurrentVisibleIndex();
            currentPageLabel.setText("当前浏览：第 " + (currentIdx + 1) + " 张");
        });

        statusLabel.setText("当前加载状态：" + testImagePaths.size() + "张测试图已就绪，可以开始下拉滚动测试");
    }

    // 扫描目录下所有支持的漫画图片，按文件名排序保证顺序正确
    private List<String> scanTestImages(String dirPath) {
        List<String> pathList = new ArrayList<>();
        File dir = new File(dirPath);
        if(!dir.exists() || !dir.isDirectory()) {
            statusLabel.setText("错误：测试图片目录不存在，请检查路径配置");
            return pathList;
        }

        File[] files = dir.listFiles();
        if(files == null) return pathList;

        for(File f : files) {
            String name = f.getName().toLowerCase();
            if(name.endsWith(".jpg") || name.endsWith(".jpeg")
                    || name.endsWith(".png") || name.endsWith(".webp")
                    || name.endsWith(".gif")) {
                pathList.add(f.getAbsolutePath());
            }
        }

        // 按文件名数字排序，保证图片顺序和命名序号一致
        pathList.sort(Comparator.comparing(File::new, (f1, f2) -> {
            int n1 = extractNumber(f1.getName());
            int n2 = extractNumber(f2.getName());
            return Integer.compare(n1, n2);
        }));
        return pathList;
    }

    // 提取文件名中的数字，解决1.jpg、10.jpg排序错乱问题
    private int extractNumber(String fileName) {
        StringBuilder numSb = new StringBuilder();
        for(char c : fileName.toCharArray()) {
            if(Character.isDigit(c)) {
                numSb.append(c);
            }
        }
        return numSb.length() == 0 ? 0 : Integer.parseInt(numSb.toString());
    }
}

