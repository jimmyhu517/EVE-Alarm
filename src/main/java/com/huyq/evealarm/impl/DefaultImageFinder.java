package com.huyq.evealarm.impl;

import com.huyq.evealarm.EveAlarmApplication;
import com.huyq.evealarm.api.ImageFinder;
import com.huyq.evealarm.core.ColorBlockUtil;
import com.huyq.evealarm.model.Coordinate;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author huyiqi
 * @date 2020/7/3
 */
public class DefaultImageFinder implements ImageFinder {

    private Robot robot ;
    private BufferedImage screen;
    private Dimension dimension;

    public DefaultImageFinder() {
        try {
            dimension = Toolkit.getDefaultToolkit().getScreenSize();
            robot = new Robot();
            screen = robot.createScreenCapture(new Rectangle(0,0,2560,1600));
            File f = new File("/Users/huyiqi/Downloads/screen3.png");
            ImageIO.write(screen, "png", f);
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Coordinate> match(BufferedImage image, double percent) {
        int[][] screenRgbs = getRGB(screen);
        ColorBlockUtil block = new ColorBlockUtil(image);
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

    private boolean colorCompare(int source,int compare){
        return source==compare;
    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public BufferedImage getScreen() {
        return screen;
    }

    public void setScreen(BufferedImage screen) {
        this.screen = screen;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
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

    public BufferedImage grab(BufferedImage image, double gamma) {
        // 创建转换缓存，且图像类别使用TYPE_3BYTE_BGR。这是摄像头图像使用的格式
        // 默认获取的图像类别是TYPE_INT_RGB，这个直接转换后颜色会错误！
        // 另外，若对Frame使用OpenCV的图像处理接口（opencv_imgproc），一定要转换成这个类别，否则会报错！
        BufferedImage copyImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        // 将原图绘制到缓存上（实现拷贝和类别转换）
        Graphics g = copyImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        // 创建转换工具
        Java2DFrameConverter converter = new Java2DFrameConverter();
        // 由于获取的图片没有进行Gamma处理，亮度会特别高，需要进行校正
        return converter.convert(converter.getFrame(copyImage, gamma));
    }
}
