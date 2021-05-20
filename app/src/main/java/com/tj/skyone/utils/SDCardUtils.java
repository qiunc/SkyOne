package com.tj.skyone.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by suntongwei on 2016/5/10.
 */
public final class SDCardUtils {

    // 应用程序根目录
    public static final String Root = File.separator + "zoomlion";

    /**
     * 返回应用程序目录
     *
     * @return
     * @throws FileNotFoundException
     */
    public static String getSDCardRoot(String directory) {
        try {
            String path = null;
            try {
                path = getSDCardPath() + Root + File.separator + directory;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            File file = new File(path);
            if (!file.exists()) {
                try {
                    file.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return path;
        } catch (NullPointerException e) {
            throw e;
        }
    }

    /**
     * 判断SD卡是否存在
     *
     * @return true存在, false不存在
     */
    public static boolean existsSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 返回内置SD卡目录
     *
     * @return
     */
    public static String getSDCardPath() throws FileNotFoundException {
        if (!existsSDCard()) {
            throw new FileNotFoundException("SD Card not exist.");
        }
        return Environment.getExternalStorageDirectory().getPath();
    }


    /**
     * 获取文件夹对象
     *
     * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
     */
    public static File getSaveFolder(String folderName) throws FileNotFoundException {
        File file = new File(getSDCardPath() + File.separator + folderName + File.separator);
        try {
            file.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 获取SD卡下指定文件夹的绝对路径
     *
     * @return 返回SD卡下的指定文件夹的绝对路径
     */
    public static String getSavePath(String folderName) throws FileNotFoundException {
        return getSaveFolder(folderName).getAbsolutePath();
    }

    /**
     * 从指定文件夹获取文件
     *
     * @return 如果文件不存在则创建, 如果如果无法创建文件或文件名为空则返回null
     */
    public static File getSaveFile(String folderPath, String fileNmae) {
        File file = null;

        try {
            file = new File(getSavePath(folderPath) + File.separator + fileNmae);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File writeToSDCardFile(String directory, String fileName, String content, boolean isAppend) {
        return writeToSDCardFile(directory, fileName, content, "", isAppend);
    }

    /**
     * @param directory (you don't need to begin with
     *                  Environment.getExternalStorageDirectory()+File.separator)
     * @param fileName
     * @param content
     * @param encoding  (UTF-8...)
     * @param isAppend  : Context.MODE_APPEND
     * @return
     */
    public static File writeToSDCardFile(String directory, String fileName, String content, String encoding, boolean isAppend) {
        // mobile SD card path +path
        File file = null;
        OutputStream os = null;
        try {
            file = new File(getSDCardRoot(directory) + File.separator + fileName);
            os = new FileOutputStream(file, isAppend);
            if (encoding.equals("")) {
                os.write(content.getBytes());
            } else {
                os.write(content.getBytes(encoding));
            }
            os.flush();
        } catch (IOException e) {
            Log.e("FileUtil", "writeToSDCardFile:" + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * write data from inputstream to SDCard
     */
    public File writeToSDCardFromInput(String directory, String fileName, InputStream input) {
        File file = null;
        OutputStream os = null;
        try {
            file = new File(getSDCardRoot(directory) + File.separator + fileName);
            os = new FileOutputStream(file);
            byte[] data = new byte[1024];
            int length = -1;
            while ((length = input.read(data)) != -1) {
                os.write(data, 0, length);
            }
            // clear cache
            os.flush();
        } catch (Exception e) {
            Log.e("FileUtil", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * this url point to image(jpg)
     *
     * @param url
     * @return image name
     */
    public static String getUrlLastString(String url) {
        String[] str = url.split("/");
        int size = str.length;
        return str[size - 1];
    }
}
