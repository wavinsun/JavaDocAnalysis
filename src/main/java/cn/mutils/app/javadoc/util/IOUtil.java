package cn.mutils.app.javadoc.util;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO实用类
 */
public class IOUtil {

    public static void copy(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[4096];// 4K
        int bufferIndex = -1;
        while ((bufferIndex = in.read(buffer)) != -1) {
            out.write(buffer, 0, bufferIndex);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Exception e) {
            //IOException
        }
    }
}
