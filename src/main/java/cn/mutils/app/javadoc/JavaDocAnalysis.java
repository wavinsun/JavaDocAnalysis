package cn.mutils.app.javadoc;

import cn.mutils.app.javadoc.model.ClassDocInfo;
import cn.mutils.app.javadoc.util.FileUtil;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 分析入口
 */
public class JavaDocAnalysis {

    protected static List<ClassDocInfo> sClassDocInfoList = new ArrayList<ClassDocInfo>();

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
        new JavaDocReporter(sClassDocInfoList).report(config.doc);
    }

}
