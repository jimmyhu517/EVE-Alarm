package com.huyq.evealarm.task;

import com.huyq.evealarm.EveAlarmApplication;
import com.huyq.evealarm.api.ImageFinder;
import com.huyq.evealarm.core.ConstantHolder;
import com.huyq.evealarm.enums.ImageFilesEnum;
import com.huyq.evealarm.impl.DefaultImageFinder;
import com.huyq.evealarm.model.Coordinate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @author huyiqi
 * @date 2020/7/21
 */
@Component
@Order(1)
public class CheckTask implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        check0();
        check5();
        check10();
        checkWar();
    }

    @Async("taskExecutor")
    private void check0() {
        ConstantHolder.STATUS_0 = this.checkImage(ImageFilesEnum.IMAGE_0);
        sleep();
    }
    @Async("taskExecutor")
    private void check5() {
        ConstantHolder.STATUS_5 = this.checkImage(ImageFilesEnum.IMAGE_5);
        sleep();
    }
    @Async("taskExecutor")
    private void check10() {
        ConstantHolder.STATUS_10 = this.checkImage(ImageFilesEnum.IMAGE_10);
        sleep();
    }
    @Async("taskExecutor")
    private void checkWar() {
        ConstantHolder.STATUS_WAR = this.checkImage(ImageFilesEnum.IMAGE_WAR);
        sleep();
    }

    private boolean checkImage(ImageFilesEnum imageType) {
        boolean result = false;
        try {
            BufferedImage image = ImageIO.read(EveAlarmApplication.class.getClassLoader().getResourceAsStream(imageType.getPath()));
            ImageFinder finder = new DefaultImageFinder(image);
            List<Coordinate> list = finder.match(0.9);
            result = list!=null && !list.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
