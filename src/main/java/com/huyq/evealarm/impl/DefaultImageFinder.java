package com.huyq.evealarm.impl;

import com.huyq.evealarm.api.ImageFinder;
import com.huyq.evealarm.core.ColorBlockUtil;
import com.huyq.evealarm.core.ConstantHolder;
import com.huyq.evealarm.model.Coordinate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author huyiqi
 * @date 2020/7/3
 */
public class DefaultImageFinder implements ImageFinder {

    private Robot robot;
    private ColorBlockUtil block;

    public DefaultImageFinder(BufferedImage image) {
        try {
            robot = new Robot();
            block = new ColorBlockUtil(image);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Coordinate> match(double percent) {
        BufferedImage screen = robot.createScreenCapture(new Rectangle(0,0, ConstantHolder.DIMENSION.width,ConstantHolder.DIMENSION.height));
        int[][] screenRgbs = getRGB(screen);
        List<Coordinate> coordinates = new ArrayList<>();
        for(int x=0;x<screen.getWidth()-block.getWidth();x++){
            for(int y=0;y<screen.getHeight()-block.getHeight();y++){
                int topLeft = screenRgbs[x][y];
                int topRight= screenRgbs[x+ block.getRightTop().getX()][y+block.getRightTop().getY()];
                int bottomLeft= screenRgbs[x+ block.getLeftBottom().getX()][y+block.getLeftBottom().getY()];
                int bottomRight= screenRgbs[x+ block.getRightBottom().getX()][y+block.getRightBottom().getY()];
                int center= screenRgbs[x+ block.getCenter().getX()][y+block.getCenter().getY()];
                if(!block.isPointMatch(topLeft,topRight,bottomLeft,bottomRight,center,percent)) {
                    continue;
                }
                coordinates.add(new Coordinate(x,y));
            }
        }

        return coordinates.stream().filter(coordinate -> block.allMatch(coordinate,screenRgbs,percent)).collect(Collectors.toList());
    }

    private int[][] getRGB(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] rgb = new int[width][height];
        for(int i=img.getMinX(); i<width; i++) {
            for(int j=img.getMinY(); j<height; j++) {
                rgb[i][j] = img.getRGB(i,j);
            }
        }
        return rgb;
    }
}
