package com.huyq.evealarm.api;

import com.huyq.evealarm.model.Coordinate;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author huyiqi
 * @date 2020/7/3
 */
public interface ImageFinder {

    /**
     * 查找匹配到的图片坐标
     * @param image 要查找的图片
     * @param percent 相似度
     * @return 返回找到的所有坐标
     */
    List<Coordinate> match(BufferedImage image, double percent);
}
