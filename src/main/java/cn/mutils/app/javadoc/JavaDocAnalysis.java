package cn.mutils.app.javadoc;

import cn.mutils.app.javadoc.util.FileUtil;
import com.alibaba.fastjson.JSON;

import java.io.File;

/**
 * 分析入口
 */
public class JavaDocAnalysis {

    public static void main(String[] args) {
        JavaDocConfig config = new JavaDocConfig();
        File jsonFile = new File("javadoc.json");
        if (!jsonFile.isFile()) {
            config.src.add("src");
        } else {
            String jsonText = FileUtil.getString(jsonFile);
            config = JSON.parseObject(jsonText, JavaDocConfig.class);
        }
        if (config == null || config.src == null) {
            System.err.println("src directory is null");
            System.exit(-1);
        }
        new JavaDocReporter(JavaDocParser.parse(config)).report(config.doc);
    }

}
