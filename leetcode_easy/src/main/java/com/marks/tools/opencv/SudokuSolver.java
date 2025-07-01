package com.marks.tools.opencv;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/6/4 16:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SudokuSolver {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private int[] line = new int[9];
    private int[] column = new int[9];
    private int[][] block = new int[3][3];
    private boolean valid = false;
    private List<int[]> spaces = new ArrayList<int[]>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public void solveSudo(String imagePath) throws TesseractException {
        char[][] board = parseSudokuImage(imagePath);
        char[][] chars = parseSudokuImages(imagePath);
        System.out.println("first ===>");
//        char[][] solvedBoard = solveSudoku(board);

//        saveSudokuImage(solvedBoard);
    }

    public char[][] parseSudokuImages(String imagePath) throws TesseractException {
        // 读取图片
        Mat image = Imgcodecs.imread(imagePath);

        // 预处理图片以提高OCR识别率
        Mat gray = new Mat();
        Mat blurred = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(gray, blurred, new Size(5, 5), 0);
        Imgproc.threshold(blurred, image, 128, 255, Imgproc.THRESH_BINARY);

        // 将Mat转换为BufferedImage
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", image, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(byteArray));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 使用Tesseract OCR解析图片
        ITesseract instance = new Tesseract();
        instance.setDatapath("D:\\Tesseract_OCR\\tessdata"); // 设置Tesseract数据路径
        instance.setLanguage("eng"); // 设置语言为英语

        String result = instance.doOCR(bufferedImage);

        // 将结果转换为二维字符数组
        char[][] board = new char[9][9];
        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char currentChar = result.charAt(index++);
                if (Character.isDigit(currentChar)) {
                    board[i][j] = currentChar;
                } else {
                    board[i][j] = '.';
                }
            }
        }

        return board;
    }

    private char[][] parseSudokuImage(String imagePath) {
        Mat src = Imgcodecs.imread(imagePath);
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.adaptiveThreshold(gray, gray, 255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 11, 2);

        // 查找轮廓并提取最大轮廓（数独网格）
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(gray, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // 获取最大轮廓并提取数独网格
        MatOfPoint maxContour = contours.stream()
                .max((c1, c2) -> (int)(Imgproc.contourArea(c1) - Imgproc.contourArea(c2)))
                .orElse(null);

        Mat warped = warpSudokuGrid(src, maxContour);

        // 分割单元格并识别数字
        char[][] board = new char[9][9];
        int cellSize = warped.rows() / 9;

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:\\Tesseract_OCR\\tessdata"); // 设置tessdata路径
        tesseract.setTessVariable("tessedit_char_whitelist", "123456789");

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Rect cellRect = new Rect(j * cellSize, i * cellSize, cellSize, cellSize);
                Mat cell = new Mat(warped, cellRect);

                // 预处理单元格图像
                Mat processedCell = preprocessCell(cell);

                // 使用Tesseract识别数字
                String digit = recognizeDigit(processedCell, tesseract);
                board[i][j] = digit.isEmpty() ? '.' : digit.charAt(0);
            }
        }
        return board;
    }

    private Mat warpSudokuGrid(Mat src, MatOfPoint contour) {
        // 实现透视变换将数独网格转为正方形
        MatOfPoint2f approx = new MatOfPoint2f();
        MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());

        // 动态调整epsilon值直到获得4个点
        double epsilon = 0.01;
        int maxAttempts = 10;
        while (maxAttempts-- > 0) {
            Imgproc.approxPolyDP(contour2f, approx,
                    epsilon * Imgproc.arcLength(contour2f, true), true);
            if (approx.toArray().length == 4) break;
            epsilon += 0.005;
        }



        Point[] srcPoints = approx.toArray();
        Point[] dstPoints = new Point[]{
                new Point(0, 0), new Point(450, 0),
                new Point(450, 450), new Point(0, 450)
        };

        Mat warpMat = Imgproc.getPerspectiveTransform(
                new MatOfPoint2f(srcPoints), new MatOfPoint2f(dstPoints));
        Mat warped = new Mat();
        Imgproc.warpPerspective(src, warped, warpMat, new Size(450, 450));

        return warped;
    }

    private Mat preprocessCell(Mat cell) {
        Mat gray = new Mat();
        Imgproc.cvtColor(cell, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(gray, gray, 0, 255, Imgproc.THRESH_BINARY_INV | Imgproc.THRESH_OTSU);
        Core.copyMakeBorder(gray, gray, 10, 10, 10, 10, Core.BORDER_CONSTANT, new Scalar(0));
        return gray;
    }

    private String recognizeDigit(Mat cell, Tesseract tesseract) {
        try {
            File tempFile = File.createTempFile("cell", ".png");
            Imgcodecs.imwrite(tempFile.getAbsolutePath(), cell);
            String result = tesseract.doOCR(tempFile);
            tempFile.delete();
            return result.trim();
        } catch (Exception e) {
            return "";
        }
    }

    private void saveSudokuImage(char[][] board) {
        int imageSize = 900;
        int cellSize = imageSize / 9;
        Mat result = new Mat(imageSize, imageSize, CvType.CV_8UC3, new Scalar(255, 255, 255));

        // 绘制网格线
        for (int i = 0; i <= 9; i++) {
            int thickness = (i % 3 == 0) ? 3 : 1;
            Imgproc.line(result, new Point(0, i * cellSize), new Point(imageSize, i * cellSize),
                    new Scalar(0, 0, 0), thickness);
            Imgproc.line(result, new Point(i * cellSize, 0), new Point(i * cellSize, imageSize),
                    new Scalar(0, 0, 0), thickness);
        }

        // 填充数字
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    Point textPos = new Point(j * cellSize + cellSize/3, (i+1) * cellSize - cellSize/3);
                    Imgproc.putText(result, String.valueOf(board[i][j]), textPos,
                            Imgproc.FONT_HERSHEY_SIMPLEX, 1.5, new Scalar(0, 0, 255), 2);
                }
            }
        }
        // "solved_sudoku.jpg"
        String outputPath = "D:\\images\\result\\sudoku\\solved_sudoku_"+ LocalDateTime.now().format(formatter) + ".png";
        Imgcodecs.imwrite(outputPath, result);
    }

    public char[][] solveSudoku(char[][] board) {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[i][j] != '.') {
                    int digit = board[i][j] - '0' - 1;
                    flip(i, j, digit);
                }
            }
        }

        while (true) {
            boolean modified = false;
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    if (board[i][j] == '.') {
                        int mask = ~(line[i] | column[j] | block[i / 3][j / 3]) & 0x1ff;
                        if ((mask & (mask - 1)) == 0) {
                            int digit = Integer.bitCount(mask - 1);
                            flip(i, j, digit);
                            board[i][j] = (char) (digit + '0' + 1);
                            modified = true;
                        }
                    }
                }
            }
            if (!modified) {
                break;
            }
        }

        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[i][j] == '.') {
                    spaces.add(new int[]{i, j});
                }
            }
        }

        dfs(board, 0);
        return board;
    }

    private void dfs(char[][] board, int pos) {
        if (pos == spaces.size()) {
            valid = true;
            return;
        }

        int[] space = spaces.get(pos);
        int i = space[0], j = space[1];
        int mask = ~(line[i] | column[j] | block[i / 3][j / 3]) & 0x1ff;
        for (; mask != 0 && !valid; mask &= (mask - 1)) {
            int digitMask = mask & (-mask);
            int digit = Integer.bitCount(digitMask - 1);
            flip(i, j, digit);
            board[i][j] = (char) (digit + '0' + 1);
            dfs(board, pos + 1);
            flip(i, j, digit);
        }
    }

    private void flip(int i, int j, int digit) {
        line[i] ^= (1 << digit);
        column[j] ^= (1 << digit);
        block[i / 3][j / 3] ^= (1 << digit);
    }
}
