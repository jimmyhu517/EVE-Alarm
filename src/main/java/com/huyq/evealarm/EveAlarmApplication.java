package com.huyq.evealarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * @author huyiqi
 */
@SpringBootApplication
@EnableScheduling
public class EveAlarmApplication {

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
		SpringApplication.run(EveAlarmApplication.class, args);
	}

}
