package com.saas.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 
 * @since 03/03/2013 9:28 AM
 */
public abstract class FileUtils extends org.apache.commons.io.FileUtils{

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);


    public static void moveFile(String srcPath, String destPath) throws IOException {
        moveFile(new File(srcPath), new File(destPath));
    }


    public static void moveFile(File srcFile, File destFile) throws IOException {
        if (!srcFile.exists()) {
            if (logger.isWarnEnabled()) {
                logger.warn("Unable to get src file from path '" + srcFile.getPath() + "'");
            }
        }
        if (destFile.exists()) {
            if (logger.isWarnEnabled()) {
                logger.warn("dest file already exists '" + destFile.getPath() + "'");
            }
        }
        boolean success = srcFile.renameTo(destFile);

        if (success) {
        }

        if (logger.isTraceEnabled()) {
            logger.trace("Unable to move file using rename. Trying IO copy/delete");
        }
        copyFile(srcFile, destFile);
        if (logger.isTraceEnabled()) {
            logger.trace("copy src '" + srcFile.getPath() + " to dest '" + destFile.getPath() + "'");
        }
        srcFile.delete();
        if (logger.isTraceEnabled()) {
            logger.trace("delete src file '" + srcFile.getPath() + "'. move completed");
        }

    }


    public static void copyFile(File srcFile, File destFile) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
        byte[] buffer = new byte[8092];
        int read;
        while ((read = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, read);
        }
        bos.flush();
        bos.close();
        bis.close();
    }


    public static boolean deleteFile(String path) {
        if (StringUtils.hasText(path)) {
            return deleteFile(new File(path));
        }
        return true;
    }


    public static boolean deleteFile(File file) {
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }


}
