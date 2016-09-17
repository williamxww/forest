package com.bow.forest.common.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author vv
 * @since 2016/9/17.
 */
public class FileUtil {

    public boolean deleteQuietly(File file) {
        return FileUtils.deleteQuietly(file);
    }

    public static void copyFile(File srcFile, File destFile) throws IOException {
        FileUtils.copyFile(srcFile, destFile);
    }

    public static void copyFileToDirectory(File srcFile, File destFile) throws IOException {
        FileUtils.copyFileToDirectory(srcFile, destFile);
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        return FileUtils.openOutputStream(file, append);
    }

    public static void writeStringToFile(File file, String data, String encoding, boolean
            append) throws IOException {
        FileUtils.writeStringToFile(file, data, encoding, append);
    }

}
