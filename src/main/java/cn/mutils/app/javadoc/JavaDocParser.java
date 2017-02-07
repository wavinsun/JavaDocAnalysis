package cn.mutils.app.javadoc;

import cn.mutils.app.javadoc.model.ClassDocInfo;
import cn.mutils.app.javadoc.model.FieldDocInfo;
import cn.mutils.app.javadoc.model.MethodDocInfo;
import com.sun.javadoc.*;
import com.sun.tools.javadoc.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析器
 */
public class JavaDocParser extends Doclet {

    protected static List<ClassDocInfo> sClassDocInfoList = new ArrayList<ClassDocInfo>();

    public static List<ClassDocInfo> parse(JavaDocConfig config) {
        sClassDocInfoList.clear();
        for (String dir : config.src) {
            travelSource(new File(dir));
        }
        return sClassDocInfoList;
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

    /**
     * 覆盖基类方法
     *
     * @param rootDoc doc
     * @return
     */
    public static boolean start(RootDoc rootDoc) {
        for (ClassDoc classDoc : rootDoc.classes()) {
            ClassDocInfo classDocInfo = new ClassDocInfo();
            classDocInfo.name = classDoc.qualifiedName();
            classDocInfo.simpleName = classDoc.typeName();
            classDocInfo.packageName = classDoc.containingPackage().name();
            classDocInfo.comment = classDoc.getRawCommentText();
            for (FieldDoc fieldDoc : classDoc.fields()) {
                if (!fieldDoc.isFinal() || !fieldDoc.isStatic()) {
                    continue;
                }
                FieldDocInfo fieldDocInfo = new FieldDocInfo();
                fieldDocInfo.name = fieldDoc.name();
                fieldDocInfo.type = fieldDoc.type().typeName();
                fieldDocInfo.comment = fieldDoc.getRawCommentText();
                classDocInfo.fields.add(fieldDocInfo);
            }
            for (MethodDoc methodDoc : classDoc.methods()) {
                MethodDocInfo methodDocInfo = new MethodDocInfo();
                methodDocInfo.name = methodDoc.name();
                methodDocInfo.comment = methodDoc.getRawCommentText();
                methodDocInfo.parameters = getMethodParameters(methodDoc);
                classDocInfo.methods.add(methodDocInfo);
            }
            sClassDocInfoList.add(classDocInfo);
        }
        return false;
    }

    private static String getMethodParameters(MethodDoc methodDoc) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        Parameter[] parameters = methodDoc.parameters();
        for (int i = 0, length = parameters.length; i < length; i++) {
            Parameter parameter = parameters[i];
            sb.append(parameter.type().typeName());
            sb.append(" ");
            sb.append(parameter.name());
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

}
