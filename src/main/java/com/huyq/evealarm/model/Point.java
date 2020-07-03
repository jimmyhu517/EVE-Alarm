package com.huyq.evealarm.model;

/**
 * 像素点
 * @author huyiqi
 * @date 2020/7/3
 */
public class Point{
    public static final Point ZERO = new Point(0,0);
    private int x;
    private int y;
    private int rgb;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int rgb) {
        this(x,y);
        this.rgb = rgb;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRgb() {
        return rgb;
    }

    public void setRgb(int rgb) {
        this.rgb = rgb;
    }
    public void setRgb(int[][] block){
        this.rgb = block[this.getX()][this.getY()];
    }
}