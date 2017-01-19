package cn.mutils.app.javadoc;

import cn.mutils.app.javadoc.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.sun.javadoc.ClassDoc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 分析入口
 */
public class JavaDocAnalysis {

    protected static List<ClassDoc> sClassDocList = new ArrayList<ClassDoc>();

    public static void main(String[] args) {
        JavaDocConfig config = new JavaDocConfig();
        File jsonFile = new File("javadoc.json");
        if (!jsonFile.isFile()) {
            config.src.add("src");
        } else {
            String jsonText = FileUtil.getString(jsonFile);
            config = JSON.parseObject(jsonText, JavaDocConfig.class);
        }
        JavaDocParser.parse(config);
        new JavaDocReporter(sClassDocList).report(config.doc);
    }

}
