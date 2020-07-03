package com.huyq.evealarm.core;

import com.huyq.evealarm.model.Coordinate;
import com.huyq.evealarm.model.Point;

import java.awt.image.BufferedImage;

/**
 * @author huyiqi
 * @date 2020/7/3
 */
public class ColorBlockUtil {
    private int[][] rgbs;
    private int width;
    private int height;
    private Point leftTop;
    private Point rightTop;
    private Point leftBottom;
    private Point rightBottom;
    private Point center;

    public ColorBlockUtil(BufferedImage image){
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.rgbs = new int[this.width][this.height];

        for(int x=0;x<this.width;x++){
            for(int y=0; y<this.height;y++){
                this.rgbs[x][y] = image.getRGB(x,y);
            }
        }
        this.leftTop = Point.ZERO;
        this.leftTop.setRgb(rgbs);
        this.rightTop = new Point(this.width-1,0);
        this.rightTop.setRgb(rgbs);
        this.leftBottom = new Point(0,this.height-1);
        this.leftBottom.setRgb(rgbs);
        this.rightBottom = new Point(this.width-1,this.height-1);
        this.rightBottom.setRgb(rgbs);
        this.center = new Point(this.width/2,this.height/2);
        this.center.setRgb(rgbs);
    }

    public Point getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(Point leftTop) {
        this.leftTop = leftTop;
    }

    public Point getRightTop() {
        return rightTop;
    }

    public void setRightTop(Point rightTop) {
        this.rightTop = rightTop;
    }

    public Point getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(Point leftBottom) {
        this.leftBottom = leftBottom;
    }

    public Point getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(Point rightBottom) {
        this.rightBottom = rightBottom;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public boolean isPointMatch(int leftTop,int rightTop,int leftBottom,int rightBottom,int center,double percent){
        boolean centerMatches = Math.abs(this.center.getRgb()-center)/Math.abs(this.center.getRgb())<=percent;
        boolean leftTopMatches = Math.abs(this.leftTop.getRgb()-leftTop)/Math.abs(this.leftTop.getRgb())<=percent;
        boolean rightTopMatches = Math.abs(this.rightTop.getRgb()-rightTop)/Math.abs(this.rightTop.getRgb())<=percent;
        boolean leftBottomMatches = Math.abs(this.leftBottom.getRgb()-leftBottom)/Math.abs(this.leftBottom.getRgb())<=percent;
        boolean rightBottomMatches = Math.abs(this.rightBottom.getRgb()-rightBottom)/Math.abs(this.rightBottom.getRgb())<=percent;

        return centerMatches && leftTopMatches && rightTopMatches && leftBottomMatches && rightBottomMatches;
//        return  this.leftTop.getRgb()==leftTop&&
//                this.rightTop.getRgb()==rightTop&&
//                this.leftBottom.getRgb()==leftBottom&&
//                this.rightBottom.getRgb()==rightBottom&&
//                this.center.getRgb()==center;

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean allMatch(Coordinate coordinate, int[][] screen, double percent){
        // 检查x中心线
        int centerX = this.getWidth()/2;
        int centerY = this.getHeight()/2;
        for(int x=0;x<this.getWidth();x++){
            if(!isPointSimilar(this.rgbs[x][centerY], screen[x+coordinate.getX()][centerY+coordinate.getY()], percent)) {
                return false;
            }
        }
        //检查y 中心线
        for(int y=0;y<this.getHeight();y++){
            if(!isPointSimilar(this.rgbs[centerX][y], screen[coordinate.getX()+centerX][y+coordinate.getY()], percent)) {
                return false;
            }
        }
        for(int x = 0;x<this.getWidth();x++){
            for(int y=0;y<this.getHeight();y++){
                if(!isPointSimilar(this.rgbs[x][y], screen[x+coordinate.getX()][y+coordinate.getY()], percent)) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean isPointSimilar(int rgbTarget,int rgbSample,double percent) {
        return Math.abs(rgbTarget-rgbSample)/Math.abs(rgbTarget) <= percent;
    }
}
