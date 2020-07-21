package com.huyq.evealarm.task;

import com.huyq.evealarm.EveAlarmApplication;
import com.huyq.evealarm.core.ConstantHolder;
import com.huyq.evealarm.enums.AudioFilesEnum;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * @author huyiqi
 * @date 2020/7/21
 */
@Component
public class AlarmTask {

    @Scheduled(cron = "*/1 * * * * ?")
    public void alarm() {
        try {
            if (ConstantHolder.NEED_ALARM) {
                if(!ConstantHolder.IS_ALARMING) {
                    ConstantHolder.IS_ALARMING = true;
                }
                this.playAudio(AudioFilesEnum.RED);
            } else if(!ConstantHolder.NEED_ALARM && ConstantHolder.IS_ALARMING) {
                this.playAudio(AudioFilesEnum.CANCEL);
                ConstantHolder.IS_ALARMING = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void playAudio(AudioFilesEnum audioType) throws UnsupportedAudioFileException, IOException {
        AudioInputStream audio = AudioSystem.getAudioInputStream(EveAlarmApplication.class.getClassLoader().getResourceAsStream(audioType.getPath()));
        AudioFormat format = audio.getFormat();
        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16, format.getChannels(), format.getChannels()*2, format.getSampleRate(), false);
            audio = AudioSystem.getAudioInputStream(format, audio);
        }
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, AudioSystem.NOT_SPECIFIED);
        SourceDataLine data = null;
        try {
            data = (SourceDataLine) AudioSystem.getLine(info);
            data.open(format);
            data.start();
            byte[] bt = new byte[1024];
            while (audio.read(bt) != -1) {
                data.write(bt, 0, bt.length);
            }
            data.drain();
            data.stop();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if(data!=null) {
                data.close();
            }
            audio.close();

        }
    }
}
