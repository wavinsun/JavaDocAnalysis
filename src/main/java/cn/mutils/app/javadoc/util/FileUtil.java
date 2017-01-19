package cn.mutils.app.javadoc.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 文件实用类
 */
public class FileUtil {

    public static String getString(File file) {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            IOUtil.copy(fis, bos);
            return bos.toString("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtil.closeQuietly(bos);
            IOUtil.closeQuietly(fis);
        }
    }

}
