package com.huyq.evealarm.enums;

/**
 * @author huyiqi
 * @date 2020/7/21
 */
public enum AudioFilesEnum {
    RED("sound/alarm.mp3"),
    CANCEL("sound/cancel.mp3");

    AudioFilesEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    private String path;
}
