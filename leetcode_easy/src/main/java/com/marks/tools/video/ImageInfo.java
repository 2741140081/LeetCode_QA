package com.marks.tools.video;

import java.awt.image.BufferedImage;

public class ImageInfo {
    private int id;
    private int imageTypeId;
    private String imageName;
    private BufferedImage bufImg;
    private int imageIndex; // 从图片名称中提取的索引，如img10.webp中的10

    public ImageInfo() {
    }

    public ImageInfo(int id, int imageTypeId, String imageName, BufferedImage bufferedImage, int imageIndex) {
        this.id = id;
        this.imageTypeId = imageTypeId;
        this.imageName = imageName;
        this.bufImg = bufferedImage;
        this.imageIndex = imageIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageTypeId() {
        return imageTypeId;
    }

    public void setImageTypeId(int imageTypeId) {
        this.imageTypeId = imageTypeId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public BufferedImage getBufImg() {
        return bufImg;
    }

    public void setBufImg(BufferedImage bufImg) {
        this.bufImg = bufImg;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
}
