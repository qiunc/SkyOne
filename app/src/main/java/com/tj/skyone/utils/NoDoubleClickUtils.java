package com.tj.skyone.utils;

/**
 * description:防止多次点击工具类
 * Created by wdq
 * date: 2017/4/18  15:43
 */
public class NoDoubleClickUtils {
    private static long lastClickTime;
    private final static int SPACE_TIME = 100;

    public static void initLastClickTime() {
        lastClickTime = 0;
    }

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        isClick2 = currentTime - lastClickTime <= SPACE_TIME;
        lastClickTime = currentTime;
        return false;
    }
}
