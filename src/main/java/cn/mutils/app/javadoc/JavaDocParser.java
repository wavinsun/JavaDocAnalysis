package cn.mutils.app.javadoc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.RootDoc;
import com.sun.tools.javadoc.Main;

import java.io.File;

/**
 * 解析器
 */
public class JavaDocParser extends Doclet {

    public static void parse(JavaDocConfig config) {
        if (config == null || config.src == null) {
            System.err.println("src directory is null");
            System.exit(-1);
        }
        for (String dir : config.src) {
            travelSource(new File(dir));
        }
    }

    private static void travelSource(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                travelSource(f);
            }
        } else if (file.isFile()) {
            String path = file.getPath();
            int dotIndex = path.lastIndexOf('.');
            if (dotIndex == -1) {
                return;
            }
            if (!path.substring(dotIndex + 1).equalsIgnoreCase("java")) {
                return;
            }
            Main.execute("", JavaDocParser.class.getName(), new String[]{path, "-encoding", "UTF-8"});
        }
    }

    public static boolean start(RootDoc rootDoc) {
        for (ClassDoc classDoc : rootDoc.classes()) {
            JavaDocAnalysis.sClassDocList.add(classDoc);
        }
        return false;
    }

}
