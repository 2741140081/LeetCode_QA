package com.marks.tools.video;

import com.marks.utils.DBUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class ImagePlayer extends JFrame {
    private static String IMAGE_DIR = "";
    private static final int BATCH_SIZE = 15;

    private static final int CACHE_WINDOW_SIZE = BATCH_SIZE * 3;
    private static final int PRELOAD_THRESHOLD = 10;
    private List<ImageInfo> images = new ArrayList<>();
    private static int currentIndex = 0;
    private static int startIndex = 0;
    private JLabel imageLabel;
    private JLabel imageNameLabel;
    private JLabel tipLabel;
    private Timer autoPlayTimer;
    private JScrollPane scrollPane;
    private boolean autoPlay = false;
    public static int IMG_WIDTH = 1750;
    public static int IMG_HEIGHT = 1000;
    public static int IMG_MAX_INDEX = 0;
    private static boolean isRotate = true;

    private static double degree = 90;
    private JComboBox<String> folderComboBox;
    private static String currentImageType;
    private static String currentImageFormat;
    private static int currentImageTypeId = -1;

    private boolean scrollMode = false;
    private JPanel imagePanel;
    private Map<Integer, JLabel> imageLabels = new HashMap<>();
    private int scrollCurrentIndex = 0;
    private List<ImageInfo> scrollImages = new ArrayList<>();
    private static final int SCROLL_CACHE_WINDOW_SIZE = 15;
    private int scrollWindowStartIndex = 0;
    private boolean isLoadingMoreImages = false; // 添加标志防止重复加载

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        SwingUtilities.invokeLater(() -> {
            ImagePlayer player = new ImagePlayer();
            player.setVisible(true);
        });
    }

    public ImagePlayer() {
        setTitle("图片播放器");
        setSize(1750, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel();

        folderComboBox = new JComboBox<>();
        loadImageTypesFromDB();
        controlPanel.add(new JLabel("选择图片类型:"));
        controlPanel.add(folderComboBox);

        JComboBox<String> rotateComboBox = new JComboBox<>(new String[]{"0", "90", "-90"});
        rotateComboBox.setSelectedItem("0");
        rotateComboBox.addActionListener(e -> {
            String selectedDegree = (String) rotateComboBox.getSelectedItem();
            try {
                degree = Double.parseDouble(selectedDegree);
                isRotate = degree != 0;

                if(currentImageType != null) {
                    updateRotationDegree(currentImageType, degree);
                }

                if(currentImageType != null) {
                    loadInitialImages();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "角度设置错误");
            }
        });
        controlPanel.add(new JLabel("旋转角度:"));
        controlPanel.add(rotateComboBox);

        JButton refreshBtn = new JButton("刷新");
        refreshBtn.addActionListener(e -> {
            if(currentImageType != null) {
                loadInitialImages();
            }
        });
        controlPanel.add(refreshBtn);

        JButton saveBtn = new JButton("保存");
        saveBtn.addActionListener(e -> {
            if(currentImageType != null) {
                if (scrollMode) {
                    // 在滚动模式下，弹出对话框让用户输入要保存的图片ID
                    String input = JOptionPane.showInputDialog(ImagePlayer.this, "请输入要保存的图片ID:", "保存图片", JOptionPane.QUESTION_MESSAGE);
                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            int imageId = Integer.parseInt(input.trim());
                            if (imageId >= 0 && imageId < IMG_MAX_INDEX) {
                                updateLastIndex(currentImageType, imageId);
                                JOptionPane.showMessageDialog(ImagePlayer.this, "图片ID " + imageId + " 已保存", "保存成功", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(ImagePlayer.this, "图片ID超出范围，请输入0到" + (IMG_MAX_INDEX - 1) + "之间的数字", "输入错误", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(ImagePlayer.this, "请输入有效的数字", "输入错误", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    // 翻页模式下保持原有逻辑
                    updateLastIndex(currentImageType, currentIndex);
                }
            }
        });
        controlPanel.add(saveBtn);

        JButton modeBtn = new JButton("切换到滚动模式");
        modeBtn.addActionListener(e -> {
            scrollMode = !scrollMode;
            modeBtn.setText(scrollMode ? "切换到翻页模式" : "切换到滚动模式");
            if (currentImageType != null) {
                if (scrollMode) {
                    setupScrollMode();
                } else {
                    setupPageMode();
                }
                loadInitialImages();
            }
        });
        controlPanel.add(modeBtn);

        add(controlPanel, BorderLayout.NORTH);
        
        folderComboBox.addActionListener(e -> {
            currentImageType = (String) folderComboBox.getSelectedItem();
            loadImageTypeInfo(currentImageType);
            IMAGE_DIR = "D:\\spider\\data\\" + currentImageType + "\\result\\";

            int[] imageCountInfo = getLastIndexFromDB(currentImageType);
            startIndex = imageCountInfo[0];
            IMG_MAX_INDEX = imageCountInfo[1];

            if (scrollMode) {
                setupScrollMode();
            } else {
                setupPageMode();
            }

            loadInitialImages();
            setupAutoPlayController();
            setVisible(true);
        });
    }

    private void setupScrollMode() {
        removeAllComponents();
        
        imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        scrollPane = new JScrollPane(imagePanel) {
            @Override
            public Insets getInsets() {
                return new Insets(0, 0, 0, 0);
            }
        };
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setBlockIncrement(200);
        add(scrollPane, BorderLayout.CENTER);
        
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (!e.getValueIsAdjusting()) {
                    handleScrollEvent();
                }
            }
        });
        revalidate();
        repaint();
    }

    private void setupPageMode() {
        removeAllComponents();
        
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        tipLabel = new JLabel();
        tipLabel.setHorizontalAlignment(JLabel.LEFT);
        tipLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
        tipLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tipLabel.setForeground(Color.RED);
        
        imageNameLabel = new JLabel();
        imageNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        imageNameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 10));
        imageNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        imageNameLabel.setForeground(Color.GRAY);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(tipLabel, BorderLayout.WEST);
        bottomPanel.add(imageNameLabel, BorderLayout.EAST);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        setupMouseWheelListener();
        revalidate();
        repaint();
    }
    
    private void removeAllComponents() {
        if (scrollPane != null) {
            remove(scrollPane);
            scrollPane = null;
        }
        imageLabel = null;
        tipLabel = null;
        imageNameLabel = null;
        imagePanel = null;
        imageLabels.clear();
    }

    private void loadImageTypesFromDB() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT image_name FROM image_type_detail");

            while (rs.next()) {
                folderComboBox.addItem(rs.getString("image_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, stmt, rs);
        }
    }

    private void loadImageTypeInfo(String imageType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT image_id, image_rotate_degree, image_format FROM image_type_detail WHERE image_name = ?");
            pstmt.setString(1, imageType);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                currentImageTypeId = rs.getInt("image_id");
                degree = rs.getDouble("image_rotate_degree");
                currentImageFormat = rs.getString("image_format");
                isRotate = degree != 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }

    private int[] getLastIndexFromDB(String imageType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int[] res = new int[2];
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT image_his_index, image_max_count FROM image_type_detail WHERE image_name = ?");
            pstmt.setString(1, imageType);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                res[0] = rs.getInt("image_his_index");
                res[1] = rs.getInt("image_max_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return res;
    }

    private void updateRotationDegree(String imageType, double degree) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("UPDATE image_type_detail SET image_rotate_degree = ? WHERE image_name = ?");
            pstmt.setDouble(1, degree);
            pstmt.setString(2, imageType);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    private void loadInitialImages() {
        if (scrollMode) {
            loadInitialImagesForScrollMode();
        } else {
            loadInitialImagesForPageMode();
        }
    }

    private void loadInitialImagesForPageMode() {
        int startLoadIndex = Math.max(0, startIndex - 19);
        int endLoadIndex = Math.min(startIndex + 20, IMG_MAX_INDEX - 1);
        
        List<ImageInfo> firstBatch = loadImageInfos(startLoadIndex, endLoadIndex);
        SwingUtilities.invokeLater(() -> {
            images.clear();
            images.addAll(firstBatch);
            showImage(startIndex, 1);
        });
    }

    private void loadInitialImagesForScrollMode() {
        scrollWindowStartIndex = Math.max(0, startIndex);
        int startLoadIndex = scrollWindowStartIndex;
        int endLoadIndex = Math.min(startLoadIndex + SCROLL_CACHE_WINDOW_SIZE - 1, IMG_MAX_INDEX - 1);
        
        System.out.println("初始加载图片: 从索引 " + startLoadIndex + " 到 " + endLoadIndex);
        
        List<ImageInfo> firstBatch = loadImageInfos(startLoadIndex, endLoadIndex);
        SwingUtilities.invokeLater(() -> {
            synchronized (scrollImages) {
                scrollImages.clear();
                scrollImages.addAll(firstBatch);
            }
            
            if (imagePanel == null) {
                setupScrollMode();
            } else {
                imagePanel.removeAll();
                imageLabels.clear();
            }
            
            firstBatch.sort(Comparator.comparingInt(ImageInfo::getImageIndex));
            
            for (ImageInfo info : firstBatch) {
                addImageToScrollPanel(info);
                System.out.println("添加初始图片: 索引=" + info.getImageIndex() + ", 名称=" + info.getImageName());
            }
            imagePanel.revalidate();
            imagePanel.repaint();
            
            SwingUtilities.invokeLater(() -> scrollToIndex(startIndex));
        });
    }

    private void addImageToScrollPanel(ImageInfo info) {
        // 检查图片是否已经添加到界面中
        if (imageLabels.containsKey(info.getImageIndex() - 1)) {
            System.out.println("图片已存在，跳过添加: 索引=" + info.getImageIndex() + ", 名称=" + info.getImageName());
            return; // 如果已经存在，直接返回
        }
        
        BufferedImage image = info.getBufImg();
        if (image != null) {
            if (isRotate) {
                image = rotateImage(image);
            }
            
            JLabel label = new JLabel(new ImageIcon(image));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            
            JLabel nameLabel = new JLabel(info.getImageName());
            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            nameLabel.setForeground(Color.GRAY);
            nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            
            JPanel wrapperPanel = new JPanel();
            wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.X_AXIS));
            wrapperPanel.add(label);
            wrapperPanel.add(nameLabel);
            wrapperPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            wrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            
            wrapperPanel.setPreferredSize(new Dimension(IMG_WIDTH, IMG_HEIGHT));
            
            imagePanel.add(wrapperPanel);
            imageLabels.put(info.getImageIndex() - 1, label);
            System.out.println("成功添加图片到面板: 索引=" + info.getImageIndex() + ", 名称=" + info.getImageName());
        } else {
            System.out.println("图片为空，无法添加: 索引=" + info.getImageIndex() + ", 名称=" + info.getImageName());
        }
    }

    private void scrollToIndex(int index) {
        if (imagePanel == null || scrollPane == null) return;
        
        SwingUtilities.invokeLater(() -> {
            JLabel targetLabel = imageLabels.get(index);
            if (targetLabel != null) {
                Rectangle rect = new Rectangle(0, targetLabel.getY(), 1, targetLabel.getHeight());
                imagePanel.scrollRectToVisible(rect);
            }
        });
    }

    private void handleScrollEvent() {
        if (!scrollMode || scrollPane == null || imagePanel == null) return;
        
        // 如果正在加载更多图片，则不处理滚动事件
        if (isLoadingMoreImages) {
            System.out.println("正在加载更多图片，跳过滚动事件处理");
            return;
        }
        
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        int visibleBottom = verticalScrollBar.getValue() + verticalScrollBar.getVisibleAmount();
        int maximum = verticalScrollBar.getMaximum();
        
        // 使用600像素作为触发加载更多图片的阈值，与内存中的经验保持一致
        if (visibleBottom >= maximum - 100) {
            System.out.println("滚动条接近底部，触发加载更多图片");
            loadMoreImagesForScrollMode();
        }
    }

    private void loadMoreImagesForScrollMode() {
        // 如果正在加载更多图片，则不重复加载
        if (isLoadingMoreImages) {
            System.out.println("已在加载更多图片中，跳过本次加载请求");
            return;
        }
        
        // 设置正在加载标志
        isLoadingMoreImages = true;
        System.out.println("开始加载更多图片，设置加载标志");
        
        // 保存当前滚动位置
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        int oldValue = verticalScrollBar.getValue();
        
        // 获取当前窗口中的最后索引
        int currentLastIndex = -1;
        for (Map.Entry<Integer, JLabel> entry : imageLabels.entrySet()) {
            if (entry.getKey() > currentLastIndex) {
                currentLastIndex = entry.getKey();
            }
        }
        
        System.out.println("当前最后图片索引: " + currentLastIndex);
        
        // 计算新的加载范围，确保正确加载下一批图片
        int startLoadIndex = currentLastIndex + 1;
        int endLoadIndex = Math.min(startLoadIndex + SCROLL_CACHE_WINDOW_SIZE - 1, IMG_MAX_INDEX - 1);
        
        System.out.println("准备加载图片: 从索引 " + startLoadIndex + " 到 " + endLoadIndex);
        
        // 如果已经到达最后一张图片，则不加载更多
        if (startLoadIndex >= IMG_MAX_INDEX) {
            System.out.println("已到达最后一张图片，无需加载更多");
            isLoadingMoreImages = false;
            return;
        }
        
        // 重新加载界面
        SwingUtilities.invokeLater(() -> {
            // 移除所有组件
            imagePanel.removeAll();
            imageLabels.clear();
            
            // 加载新的图片批次
            List<ImageInfo> nextBatch = loadImageInfos(startLoadIndex, endLoadIndex);
            nextBatch.sort(Comparator.comparingInt(ImageInfo::getImageIndex));
            
            System.out.println("实际加载了 " + nextBatch.size() + " 张图片");
            
            for (ImageInfo info : nextBatch) {
                addImageToScrollPanel(info);
                System.out.println("添加图片: 索引=" + info.getImageIndex() + ", 名称=" + info.getImageName());
            }
            
            imagePanel.revalidate();
            imagePanel.repaint();
            
            // 恢复滚动位置
            SwingUtilities.invokeLater(() -> {
                // 滚动到顶部，而不是保持在底部
                verticalScrollBar.setValue(0);
                
                // 重置加载标志
                isLoadingMoreImages = false;
                
                System.out.println("完成加载更多图片，重置加载标志");
            });
        });
    }

    private void showImage(int index, int flag) {
        if (index < 0) {
            tipLabel.setText("当前为第一张图片，无法向前浏览");
            imageNameLabel.setText("");
            System.out.println("当前为第一张图片，无法向前浏览");
            return;
        }
        
        if (index >= IMG_MAX_INDEX) {
            tipLabel.setText("当前为最后一张图片，无法向后浏览");
            imageNameLabel.setText("");
            System.out.println("当前为最后一张图片，无法向后浏览");
            return;
        }
        
        tipLabel.setText("");

        ImageInfo targetImageInfo = null;
        int actualIndex = -1;
        
        for (int i = 0; i < images.size(); i++) {
            ImageInfo info = images.get(i);
            if ((info.getImageIndex() - 1) == index) {
                targetImageInfo = info;
                actualIndex = i;
                break;
            }
        }

        if (targetImageInfo != null && actualIndex >= 0) {
            currentIndex = index;
            BufferedImage image = targetImageInfo.getBufImg();
            if (image != null) {
                if (isRotate) {
                    image = rotateImage(image);
                }

                imageLabel.setIcon(new ImageIcon(image));
                imageNameLabel.setText(targetImageInfo.getImageName());
                System.out.println("图片正在显示, 当前图片index:" + currentIndex + ", 文件名:" + targetImageInfo.getImageName());
            } else {
                System.out.println("图片加载失败: " + targetImageInfo.getImageName());
            }

            if (flag == 1) {
                ImageInfo lastImgInfo = images.get(images.size() - 1);
                int lastIndex = lastImgInfo.getImageIndex();
                if (index % PRELOAD_THRESHOLD == 0 && lastIndex < IMG_MAX_INDEX) {
                    System.out.println("预加载下一批图片 当前 lastIndex 为:" + lastIndex);
                    loadNextBatch(lastIndex + 1);
                }
            } else if (flag == -1) {
                ImageInfo firstImgInfo = images.get(0);
                int firstIndex = firstImgInfo.getImageIndex();
                if (index % PRELOAD_THRESHOLD == 0 && firstIndex > 0) {
                    int prevBatchStart = Math.max(0, firstIndex - PRELOAD_THRESHOLD);
                    preloadPreviousBatch(prevBatchStart);
                }
            }
        } else {
            System.out.println("索引超出范围: " + index);
        }
    }

    private BufferedImage rotateImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotated = new BufferedImage(height, width, image.getType());
        Graphics2D g2d = rotated.createGraphics();

        g2d.rotate(Math.toRadians(degree), height / 2.0, width / 2.0);
        g2d.translate((height - width) / 2.0, (width - height) / 2.0);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

    private void loadNextBatch(int startIndex) {
        int endIndex = Math.min(startIndex + PRELOAD_THRESHOLD - 1, IMG_MAX_INDEX - 1);
        List<ImageInfo> nextBatch = loadImageInfos(startIndex, endIndex);
        SwingUtilities.invokeLater(() -> {
            images.addAll(nextBatch);
            
            if (images.size() > CACHE_WINDOW_SIZE) {
                int removeCount = images.size() - CACHE_WINDOW_SIZE;
                images.subList(0, removeCount).clear();
            }
            
            System.out.println("加载了下一批图片，当前缓存图片数量：" + images.size());
        });
    }

    private void preloadPreviousBatch(int startIndex) {
        int actualStartIndex = Math.max(0, startIndex);
        int endIndex = Math.min(actualStartIndex + PRELOAD_THRESHOLD - 1, IMG_MAX_INDEX - 1);
        
        List<ImageInfo> prevBatch = loadImageInfos(actualStartIndex, endIndex);
        SwingUtilities.invokeLater(() -> {
            images.addAll(0, prevBatch);
            if (images.size() > CACHE_WINDOW_SIZE) {
                int removeCount = images.size() - CACHE_WINDOW_SIZE;
                images.subList(images.size() - removeCount, images.size()).clear();
            }
            
            System.out.println("加载了上一批图片，当前缓存图片数量：" + images.size());
        });
    }

    private void setupMouseWheelListener() {
        scrollPane.addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                if (currentIndex <= 0) {
                    tipLabel.setText("当前为第一张图片，无法向前浏览");
                    imageNameLabel.setText("");
                    System.out.println("当前为第一张图片，无法向前浏览");
                } else {
                    showImage(currentIndex - 1, -1);
                }
            } else {
                if (currentIndex >= IMG_MAX_INDEX - 1) {
                    tipLabel.setText("当前为最后一张图片，无法向后浏览");
                    imageNameLabel.setText("");
                    System.out.println("当前为最后一张图片，无法向后浏览");
                } else {
                    showImage(currentIndex + 1, 1);
                }
            }
        });
    }

    private void setupAutoPlayController() {
        System.out.println("初始化自动播放控制器，当前模式: " + (scrollMode ? "滚动模式" : "翻页模式"));

        autoPlayTimer = new Timer();

        Runnable stopAction = () -> {
            if (autoPlay) {
                autoPlay = false;
                if (autoPlayTimer != null) {
                    autoPlayTimer.cancel();
                    autoPlayTimer.purge();
                }
                System.out.println("自动播放暂停");
            }
        };

        Runnable playAction = () -> {
            if (!autoPlay) {
                autoPlay = true;
                if (scrollMode) {
                    System.out.println("开始滚动模式自动播放, 每次滚动间隔50ms，实现更平滑的线性滚动");
                } else {
                    System.out.println("开始翻页模式自动播放, 每张图片显示5s");
                }

                autoPlayTimer = new Timer();
                autoPlayTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (autoPlay) {
                            if (scrollMode && scrollPane != null && imagePanel != null) {
                                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                                int currentValue = verticalScrollBar.getValue();
                                int maximum = verticalScrollBar.getMaximum() - verticalScrollBar.getModel().getExtent();
                                
                                if (currentValue < maximum) {
                                    // 减少滚动增量，从3改为5，降低滚动频率
                                    int increment = Math.min(2, maximum - currentValue);
                                    verticalScrollBar.setValue(currentValue + increment);
                                    handleScrollEvent();
                                } else {
                                    System.out.println("滚动到达底部，停止播放");
                                    stopAction.run();
                                }
                            } else if (!scrollMode && imageLabel != null) {
                                if (currentIndex < IMG_MAX_INDEX - 1) {
                                    SwingUtilities.invokeLater(() -> {
                                        showImage(currentIndex + 1, 1);
                                    });
                                } else {
                                    SwingUtilities.invokeLater(() -> {
                                        stopAction.run();
                                        if (tipLabel != null) {
                                            tipLabel.setText("当前为最后一张图片，自动播放结束");
                                        }
                                        if (imageNameLabel != null) {
                                            imageNameLabel.setText("");
                                        }
                                        System.out.println("自动播放已到达最后一张图片");
                                    });
                                }
                            } else {
                                if (autoPlayTimer != null) {
                                    autoPlayTimer.cancel();
                                }
                            }
                        } else {
                            System.out.println("自动播放已关闭");
                            if (autoPlayTimer != null) {
                                autoPlayTimer.cancel();
                            }
                        }
                    }
                }, 0, scrollMode ? 10 : 5000); // 增加滚动模式下的定时器间隔，从18改为50毫秒
            }
        };

        new AutoPlayController(playAction, stopAction);
    }

    @Override
    public void dispose() {
        System.out.println("进入 dispose 方法");
        if (currentImageType != null) {
            updateLastIndex(currentImageType, currentIndex);
        }
        super.dispose();
    }

    private void updateLastIndex(String imageType, int index) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            System.out.println("更新当前 image type 的 history, 当前index = " + index);
            pstmt = conn.prepareStatement("UPDATE image_type_detail SET image_his_index = ? WHERE image_name = ?");
            pstmt.setInt(1, index);
            pstmt.setString(2, imageType);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    private List<ImageInfo> loadImageInfos(int start, int end) {
        List<ImageInfo> imageInfos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT detail_id, image_type_id, image_detail_name, image_name_index FROM image_display_detail " +
                    "WHERE image_type_id = ? AND image_name_index BETWEEN ? AND ? " +
                    "ORDER BY image_name_index";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, currentImageTypeId);
            pstmt.setInt(2, start);
            pstmt.setInt(3, end);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ImageInfo info = new ImageInfo();
                info.setId(rs.getInt("detail_id"));
                info.setImageTypeId(rs.getInt("image_type_id"));
                info.setImageName(rs.getString("image_detail_name"));
                info.setImageIndex(rs.getInt("image_name_index"));

                try {
                    String path = IMAGE_DIR + "\\" + info.getImageName();
                    BufferedImage original = javax.imageio.ImageIO.read(new java.io.File(path));
                    if (original != null) {
                        BufferedImage resized = resizeImage(original, IMG_WIDTH, IMG_HEIGHT);
                        info.setBufImg(resized);
                        original.flush();
                    }
                } catch (Exception e) {
                    System.err.println("Error loading image file: " + info.getImageName() + " - " + e.getMessage());
                }

                imageInfos.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return imageInfos;
    }


    private BufferedImage resizeImage(BufferedImage original, int maxWidth, int maxHeight) {
        double widthRatio = (double)maxWidth / original.getWidth();
        double heightRatio = (double)maxHeight / original.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int)(original.getWidth() * ratio);
        int newHeight = (int)(original.getHeight() * ratio);

        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(original, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return resized;
    }
}