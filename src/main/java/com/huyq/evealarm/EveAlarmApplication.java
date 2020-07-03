package com.huyq.evealarm;

import com.huyq.evealarm.api.ImageFinder;
import com.huyq.evealarm.impl.DefaultImageFinder;
import com.huyq.evealarm.model.Coordinate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import org.bytedeco.javacpp.*;

@SpringBootApplication
public class EveAlarmApplication {

	public static void main(String[] args) throws IOException {

		ImageFinder finder = new DefaultImageFinder();

		SpringApplication.run(EveAlarmApplication.class, args);
		BufferedImage search = ImageIO.read(EveAlarmApplication.class.getClassLoader().getResourceAsStream("image/over.png"));
		long start = System.currentTimeMillis();
		List<Coordinate> coordinateList = finder.match(search,0);
		System.out.println("耗时:"+(System.currentTimeMillis()-start));
		coordinateList.stream().findAny().ifPresent(coordinate -> System.out.println(String.format("find help image x:%d,y:%d",coordinate.getX(),coordinate.getY())));
	}

}
