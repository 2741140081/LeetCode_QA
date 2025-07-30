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
    private static String IMAGE_DIR = "D:\\spider\\data\\doc_01\\result\\";
    private static final int BATCH_SIZE = 100;
    private static final int PRELOAD_THRESHOLD = 50;
    private List<BufferedImage> images = new ArrayList<>();
    private static int currentIndex = 0;
    private static int startIndex = 0;
    private JLabel imageLabel;
    private Timer autoPlayTimer;
    private JScrollPane scrollPane;
    private boolean autoPlay = false;
    public static int IMG_WIDTH = 1750;
    public static int IMG_HEIGHT = 1000;
    public static int IMG_MAX_INDEX = 393;
    private static boolean isRotate = true;

    private static double degree = 90;
    private JComboBox<String> folderComboBox;
    private static String currentImageType;
    private static String currentImageFormat;


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


        // 创建显示面板
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        scrollPane = new JScrollPane(imageLabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        // 添加下拉框选择监听
        folderComboBox.addActionListener(e -> {
            currentImageType = (String) folderComboBox.getSelectedItem();
            loadImageTypeInfo(currentImageType);
            IMAGE_DIR = "D:\\spider\\data\\" + currentImageType + "\\result\\";
            startIndex = getLastIndexFromDB(currentImageType);

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
            pstmt = conn.prepareStatement("SELECT * FROM image_type_detail WHERE image_name = ?");
            pstmt.setString(1, imageType);
            rs = pstmt.executeQuery();

            if (rs.next()) {
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

    private int getLastIndexFromDB(String imageType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT image_his_index FROM image_type_detail WHERE image_name = ?");
            pstmt.setString(1, imageType);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("image_his_index");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return 0;
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
            List<BufferedImage> firstBatch = ImageLoader.loadImages(IMAGE_DIR, startIndex, startIndex + BATCH_SIZE - 1, IMG_WIDTH, IMG_HEIGHT);
            SwingUtilities.invokeLater(() -> {
                images.clear(); // 重置images 的内容, 如果我们选择其他 image type时
                images.addAll(firstBatch);
                showImage(startIndex);
            });
        }).start();
    }

    private void showImage(int index) {
        // 计算实际在images列表中的索引位置
        int actualIndex = index - startIndex;

        if (actualIndex >= 0 && actualIndex < images.size()) {
            currentIndex = index;
            BufferedImage image = images.get(actualIndex);
            if (isRotate) {
                image = rotateImage(image);
            }

            imageLabel.setIcon(new ImageIcon(image));

            System.out.println("图片正在显示, 当前图片index:" + currentIndex );
            // 预加载下一批图片
            if (index % BATCH_SIZE == PRELOAD_THRESHOLD &&
                    (index + BATCH_SIZE - PRELOAD_THRESHOLD) < IMG_MAX_INDEX) {
                System.out.println("预加载下一批图片 当前index 为:" + currentIndex );
                loadNextBatch(index + BATCH_SIZE - PRELOAD_THRESHOLD);
            }
        } else {
            System.out.println("索引超出范围: " + index + " (实际索引:" + actualIndex + ")");
        }
    }

    // 顺时针旋转90度方法
    private BufferedImage rotateImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotated = new BufferedImage(height, width, image.getType());
        Graphics2D g2d = rotated.createGraphics();

        g2d.rotate(Math.toRadians(90), height / 2.0, width / 2.0);
        g2d.translate((height - width) / 2.0, (width - height) / 2.0);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

    private void loadNextBatch(int startIndex) {
        new Thread(() -> {
            int endIndex = Math.min(startIndex + BATCH_SIZE - 1, IMG_MAX_INDEX - 1);
            List<BufferedImage> nextBatch = ImageLoader.loadImages(IMAGE_DIR, startIndex, endIndex, IMG_WIDTH, IMG_HEIGHT);
            SwingUtilities.invokeLater(() -> {
                images.addAll(nextBatch);
            });
        }).start();
    }

    private void setupMouseWheelListener() {
        scrollPane.addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                showImage(currentIndex - 1);
            } else {
                showImage(currentIndex + 1);
            }
        });
    }

    private void setupAutoPlayController() {
        autoPlayTimer = new Timer();

        Runnable playAction = () -> {
            if (!autoPlay) {
                autoPlay = true;
                System.out.println("开始自动播放, 每张图片显示5s");
                autoPlayTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(() -> showImage(currentIndex + 1));
                    }
                }, 0, 5000);
            }
        };

        Runnable stopAction = () -> {
            if (autoPlay) {
                autoPlay = false;
                autoPlayTimer.cancel();
                System.out.println("自动播放暂停");
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
}

