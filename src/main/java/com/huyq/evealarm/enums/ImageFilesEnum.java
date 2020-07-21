package com.huyq.evealarm.enums;

/**
 * @author huyiqi
 * @date 2020/7/21
 */
public enum ImageFilesEnum {
    IMAGE_0(""),
    IMAGE_5(""),
    IMAGE_10(""),
    IMAGE_WAR("");


    ImageFilesEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    private String path;
}
