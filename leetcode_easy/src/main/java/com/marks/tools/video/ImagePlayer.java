package com.marks.tools.video;


import com.marks.utils.DBUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.Timer;


public class ImagePlayer extends JFrame {
    private static String IMAGE_DIR = "";
    private static final int BATCH_SIZE = 20;

    // 添加缓存窗口大小
    private static final int CACHE_WINDOW_SIZE = BATCH_SIZE * 5;
    private static final int PRELOAD_THRESHOLD = 10;
    private List<ImageInfo> images = new ArrayList<>();
    private static int currentIndex = 0;
    private static int startIndex = 0;
    private JLabel imageLabel;
    private JLabel imageNameLabel; // 用于显示当前图片名称的标签
    private JLabel tipLabel; // 用于显示提示信息的标签
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
    private static int currentImageTypeId = -1; // 用于数据库查询


    public static void main(String[] args) {
        // 初始化数据库连接
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

        // 顶部控制面板
        JPanel controlPanel = new JPanel();

        // 图片类型
        folderComboBox = new JComboBox<>();
        loadImageTypesFromDB();
        controlPanel.add(new JLabel("选择图片类型:"));
        controlPanel.add(folderComboBox);

        // 旋转角度
        JComboBox<String> rotateComboBox = new JComboBox<>(new String[]{"0", "90", "-90"});
        rotateComboBox.setSelectedItem("0");  // 默认选择0度
        rotateComboBox.addActionListener(e -> {
            String selectedDegree = (String) rotateComboBox.getSelectedItem();
            try {
                degree = Double.parseDouble(selectedDegree);
                isRotate = degree != 0;

                // 保存到数据库
                if(currentImageType != null) {
                    updateRotationDegree(currentImageType, degree);
                }

                // 刷新当前图片
                if(currentImageType != null) {
                    loadInitialImages();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "角度设置错误");
            }
        });
        controlPanel.add(new JLabel("旋转角度:"));
        controlPanel.add(rotateComboBox);

        // 添加刷新按钮
        JButton refreshBtn = new JButton("刷新");
        refreshBtn.addActionListener(e -> {
            if(currentImageType != null) {
                loadInitialImages();
            }
        });
        controlPanel.add(refreshBtn);

        // 添加保存按钮
        JButton saveBtn = new JButton("保存");
        saveBtn.addActionListener(e -> {
            if(currentImageType != null) {
                updateLastIndex(currentImageType, currentIndex);
            }
        });
        controlPanel.add(saveBtn);

        add(controlPanel, BorderLayout.NORTH);

        // 添加下拉框选择监听
        folderComboBox.addActionListener(e -> {
            currentImageType = (String) folderComboBox.getSelectedItem();
            loadImageTypeInfo(currentImageType);
            IMAGE_DIR = "D:\\spider\\data\\" + currentImageType + "\\result\\";

            // 查询数据库给初始索引和最大索引
            int[] imageCountInfo = getLastIndexFromDB(currentImageType);
            startIndex = imageCountInfo[0];
            IMG_MAX_INDEX = imageCountInfo[1];

            // 创建显示面板
            imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            
            // 创建提示信息标签
            tipLabel = new JLabel();
            tipLabel.setHorizontalAlignment(JLabel.LEFT); // 靠左对齐
            tipLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5)); // 添加边距
            tipLabel.setFont(new Font("SansSerif", Font.PLAIN, 14)); // 设置字体
            tipLabel.setForeground(Color.RED); // 设置颜色为红色
            
            // 创建图片名称标签
            imageNameLabel = new JLabel();
            imageNameLabel.setHorizontalAlignment(JLabel.RIGHT); // 靠右对齐
            imageNameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 10)); // 添加边距
            imageNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14)); // 设置字体
            imageNameLabel.setForeground(Color.GRAY); // 设置颜色

            // 创建一个面板来包含图片和底部信息
            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(tipLabel, BorderLayout.WEST);
            bottomPanel.add(imageNameLabel, BorderLayout.EAST);
            
            // 创建一个面板来包含图片和图片名称
            JPanel imagePanel = new JPanel(new BorderLayout());
            imagePanel.add(imageLabel, BorderLayout.CENTER);
            imagePanel.add(bottomPanel, BorderLayout.SOUTH);

            scrollPane = new JScrollPane(imagePanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            add(scrollPane);

            loadInitialImages();

            setupMouseWheelListener();
            setupAutoPlayController();

            setVisible(true);
        });
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
                currentImageTypeId = rs.getInt("image_id"); // 获取image_type_detail表的主键
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
        new Thread(() -> {
            // 计算加载范围：前面19张，后面20张，总共40张图片
            int startLoadIndex = Math.max(0, startIndex - 19); // 确保不小于0
            int endLoadIndex = Math.min(startIndex + 20, IMG_MAX_INDEX - 1); // 确保不超过最大索引
            
            // 使用新的方法加载图片信息
            List<ImageInfo> firstBatch = loadImageInfos(startLoadIndex, endLoadIndex);
            SwingUtilities.invokeLater(() -> {
                images.clear();
                images.addAll(firstBatch);
                showImage(startIndex, 1);
            });
        }).start();
    }

    private void showImage(int index, int flag) {
        // 边界检查
        if (index < 0) {
            // 显示提示信息
            tipLabel.setText("当前为第一张图片，无法向前浏览");
            imageNameLabel.setText("");
            System.out.println("当前为第一张图片，无法向前浏览");
            return;
        }
        
        if (index >= IMG_MAX_INDEX) {
            // 显示提示信息
            tipLabel.setText("当前为最后一张图片，无法向后浏览");
            imageNameLabel.setText("");
            System.out.println("当前为最后一张图片，无法向后浏览");
            return;
        }
        
        // 清除提示信息
        tipLabel.setText("");

        // 查找与给定索引匹配的图片
        ImageInfo targetImageInfo = null;
        int actualIndex = -1;
        
        for (int i = 0; i < images.size(); i++) {
            ImageInfo info = images.get(i);
            if (info.getImageIndex() == index) {
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
                // 更新图片名称标签
                imageNameLabel.setText(targetImageInfo.getImageName());
                System.out.println("图片正在显示, 当前图片index:" + currentIndex + ", 文件名:" + targetImageInfo.getImageName());
            } else {
                System.out.println("图片加载失败: " + targetImageInfo.getImageName());
            }

            // 优化预加载逻辑
            if (flag == 1) {
                // 加载下一批图片
                ImageInfo lastImgInfo = images.get(images.size() - 1);
                int lastIndex = lastImgInfo.getImageIndex();
                if (index % PRELOAD_THRESHOLD == 0 && lastIndex < IMG_MAX_INDEX) {
                    System.out.println("预加载下一批图片 当前 lastIndex 为:" + lastIndex);
                    loadNextBatch(lastIndex + 1);
                }
            } else if (flag == -1) {
                // 向前滚动时的预加载
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

    // 顺时针旋转方法
    private BufferedImage rotateImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotated = new BufferedImage(height, width, image.getType());
        Graphics2D g2d = rotated.createGraphics();

        // 根据实际设置的角度进行旋转
        g2d.rotate(Math.toRadians(degree), height / 2.0, width / 2.0);
        g2d.translate((height - width) / 2.0, (width - height) / 2.0);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

    private void loadNextBatch(int startIndex) {
        new Thread(() -> {
            // 修正预加载逻辑：确保不超过BATCH_SIZE且不超过最大索引
            int endIndex = Math.min(startIndex + PRELOAD_THRESHOLD - 1, IMG_MAX_INDEX - 1);
            List<ImageInfo> nextBatch = loadImageInfos(startIndex, endIndex);
            SwingUtilities.invokeLater(() -> {
                // 先添加新批次
                images.addAll(nextBatch);
                
                // 然后确保总数量不超过缓存窗口大小
                if (images.size() > CACHE_WINDOW_SIZE) {
                    // 移除最旧的图片
                    int removeCount = images.size() - CACHE_WINDOW_SIZE;
                    images.subList(0, removeCount).clear();
                }
                
                // 调试信息
                System.out.println("加载了下一批图片，当前缓存图片数量：" + images.size());
            });
        }).start();
    }

    private void preloadPreviousBatch(int startIndex) {
        new Thread(() -> {
            // 修正索引计算逻辑，确保startIndex不小于0
            int actualStartIndex = Math.max(0, startIndex);
            int endIndex = Math.min(actualStartIndex + PRELOAD_THRESHOLD - 1, IMG_MAX_INDEX - 1);
            
            List<ImageInfo> prevBatch = loadImageInfos(actualStartIndex, endIndex);
            SwingUtilities.invokeLater(() -> {
                // 在列表前面插入图片
                images.addAll(0, prevBatch);
                // 如果缓存过大，移除尾部图片
                if (images.size() > CACHE_WINDOW_SIZE) {
                    int removeCount = images.size() - CACHE_WINDOW_SIZE;
                    images.subList(images.size() - removeCount, images.size()).clear();
                }
                
                // 调试信息
                System.out.println("加载了上一批图片，当前缓存图片数量：" + images.size());
            });
        }).start();
    }

    private void setupMouseWheelListener() {
        scrollPane.addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                // 向前滚动
                if (currentIndex <= 0) {
                    // 显示提示信息
                    tipLabel.setText("当前为第一张图片，无法向前浏览");
                    imageNameLabel.setText("");
                    System.out.println("当前为第一张图片，无法向前浏览");
                } else {
                    showImage(currentIndex - 1, -1);
                }
            } else {
                // 向后滚动
                if (currentIndex >= IMG_MAX_INDEX - 1) {
                    // 显示提示信息
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
                System.out.println("开始自动播放, 每张图片显示5s");
                
                // 使用单次定时器，每次播放后检查索引再决定是否继续
                autoPlayTimer = new Timer();
                autoPlayTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (currentIndex < IMG_MAX_INDEX - 1) {
                            SwingUtilities.invokeLater(() -> {
                                if (autoPlay) {  // 再次确认播放状态
                                    showImage(currentIndex + 1, 1);
                                }
                            });
                        } else {
                            // 达到最大索引时停止播放
                            SwingUtilities.invokeLater(() -> {
                                if (autoPlay) {
                                    stopAction.run();
                                    tipLabel.setText("当前为最后一张图片，自动播放结束");
                                    imageNameLabel.setText("");
                                    System.out.println("自动播放已到达最后一张图片");
                                }
                            });
                        }
                    }
                }, 0, 5000);
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
            System.out.println("更新当前 image type 的 history");
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

    // 新增方法：从数据库加载图片信息
    private List<ImageInfo> loadImageInfos(int start, int end) {
        List<ImageInfo> imageInfos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            // 查询指定范围内的图片信息，使用image_name_index字段
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

                // 加载实际的图片
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
        // 计算保持宽高比的缩放比例
        double widthRatio = (double)maxWidth / original.getWidth();
        double heightRatio = (double)maxHeight / original.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        // 计算新尺寸
        int newWidth = (int)(original.getWidth() * ratio);
        int newHeight = (int)(original.getHeight() * ratio);

        // 使用更高效的缩放方法
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(original, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return resized;
    }
}