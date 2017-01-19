package cn.mutils.app.javadoc;

import cn.mutils.app.javadoc.sort.ClassDocComparator;
import cn.mutils.app.javadoc.sort.MethodDocComparator;
import cn.mutils.app.javadoc.util.IOUtil;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 报表工具
 */
public class JavaDocReporter {

    private List<ClassDoc> mClassDocList;

    public JavaDocReporter(List<ClassDoc> classDocList) {
        Collections.sort(classDocList, new ClassDocComparator());
        mClassDocList = classDocList;
    }

    public void report(String docPath) {
        Writer writer = null;
        try {
            FileOutputStream fis = new FileOutputStream(docPath);
            writer = new OutputStreamWriter(fis, "UTF-8");
            String packageName = " ";
            for (ClassDoc classDoc : mClassDocList) {
                export(writer, classDoc, packageName);
                packageName = classDoc.containingPackage().name();
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            IOUtil.closeQuietly(writer);
        }
    }

    private static void export(Writer writer, ClassDoc doc, String lastPackage) throws Exception {
        String packageName = doc.containingPackage().name();
        String className = doc.qualifiedName();
        String simpleClassName = doc.simpleTypeName();
        System.out.println("export class " + className + " ...");
        boolean isNewPackage = !packageName.equals(lastPackage);
        if (isNewPackage) {
            writer.write("\n## " + packageName + " ##\n");
        }
        writer.write("\n### " + simpleClassName + " ###\n");
        writer.write("\n" + transformClassComment(doc.getRawCommentText()) + "\n");
        List<MethodDoc> methodDocList = getMethodDocList(doc);
        for (MethodDoc methodDoc : methodDocList) {
            export(writer, methodDoc);
        }
        writer.write("\n------");
        System.out.println("export class " + className + " success");
    }

    private static List<MethodDoc> getMethodDocList(ClassDoc doc) {
        List<MethodDoc> methodDocList = new ArrayList<MethodDoc>();
        for (MethodDoc methodDoc : doc.methods()) {
            methodDocList.add(methodDoc);
        }
        Collections.sort(methodDocList, new MethodDocComparator());
        return methodDocList;
    }

    private static void export(Writer writer, MethodDoc doc) throws Exception {
        String name = doc.name();
        writer.write("\n<pre><code>");
        String comment = doc.getRawCommentText();
        if (!comment.isEmpty()) {
            writer.write(comment + "\n");
        }
        writer.write("<b>" + name + "</b>" + getMethodParameters(doc));
        writer.write("\n</code></pre>\n");
        System.out.println("export method " + name + " success");
    }

    private static String getMethodParameters(MethodDoc methodDoc) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        Parameter[] parameters = methodDoc.parameters();
        for (int i = 0, length = parameters.length; i < length; i++) {
            Parameter parameter = parameters[i];
            sb.append(parameter.typeName());
            sb.append(" ");
            sb.append(parameter.name());
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private static String transformClassComment(String comment) {
        StringBuilder sb = new StringBuilder();
        String[] lines = comment.split("\n");
        for (int i = 0, length = lines.length; i < length; i++) {
            String line = lines[i];
            sb.append("> ");
            sb.append(line);
            if (i != length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
