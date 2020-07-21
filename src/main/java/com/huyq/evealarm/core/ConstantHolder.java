package com.huyq.evealarm.core;

import java.awt.*;

/**
 * @author huyiqi
 * @date 2020/7/21
 */
public class ConstantHolder {

    /**
     * 是否需要报警
     */
    public static boolean NEED_ALARM = false;

    /**
     * 是否正在报警
     */
    public static boolean IS_ALARMING = false;

    /**
     * 白名状态
     */
    public static boolean STATUS_0 = false;
    /**
     * -5声望状态
     */
    public static boolean STATUS_5 = false;
    /**
     * -10声望状态
     */
    public static boolean STATUS_10 = false;
    /**
     * 红星状态
     */
    public static boolean STATUS_WAR = false;


    public static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
}
