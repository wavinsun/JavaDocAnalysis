package cn.mutils.app.javadoc;

import cn.mutils.app.javadoc.model.*;
import cn.mutils.app.javadoc.sort.ClassDocInfoComparator;
import cn.mutils.app.javadoc.sort.ClassDocInfoTreeComparator;
import cn.mutils.app.javadoc.sort.FieldDocInfoComparator;
import cn.mutils.app.javadoc.sort.MethodDocInfoComparator;
import cn.mutils.app.javadoc.util.IOUtil;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

/**
 * 报表工具
 */
public class JavaDocReporter {

    private List<ClassDocInfo> mClassDocInfoList; // 类列表
    private List<ClassDocInfo> mClassDocInfoTreeList; //  类树结构列表
    private PackageTreeNode mPackageTreeNodeRoot; // 包结构根节点
    private Map<String, PackageTreeNode> mPackageTreeNodeMap; // 包结构哈希表
    private OverviewTreeNode mOverviewTreeNodeRoot; // 预览包树结构根节点
    private Map<String, OverviewTreeNode> mOverviewTreeNodeMap; //  预览包结构哈希表

    public JavaDocReporter(List<ClassDocInfo> classDocInfoList) {
        Collections.sort(classDocInfoList, new ClassDocInfoComparator());
        mClassDocInfoList = classDocInfoList;
        mClassDocInfoTreeList = new ArrayList<ClassDocInfo>(mClassDocInfoList);
        Collections.sort(mClassDocInfoTreeList, new ClassDocInfoTreeComparator());
        buildPackageTree();
        buildOverviewTree();
    }

    private void buildOverviewTree() {
        mOverviewTreeNodeRoot = new OverviewTreeNode();
        if (mOverviewTreeNodeMap != null) {
            mOverviewTreeNodeMap.clear();
        } else {
            mOverviewTreeNodeMap = new HashMap<String, OverviewTreeNode>();
        }
        mOverviewTreeNodeMap.put(mOverviewTreeNodeRoot.name, mOverviewTreeNodeRoot);
        Map<String, Object> packageScannedMap = new HashMap<String, Object>(); // 纪录包节点是否被输出
        String packageName = " ";
        for (ClassDocInfo classDocInfo : mClassDocInfoTreeList) {
            if (!packageName.equals(classDocInfo.packageName)) {
                buildOverviewTreePackage(classDocInfo.packageName, packageScannedMap);
            }
            buildOverviewTreeClass(classDocInfo);
            packageName = classDocInfo.packageName;
        }
        mOverviewTreeNodeRoot.resetAllIds(-1);
    }

    private void buildOverviewTreePackage(String packageName, Map<String, Object> packageScannedMap) {
        if (packageName.isEmpty()) {
            return;
        }
        String[] packageElements = packageName.split("\\.");
        for (int i = 0, size = packageElements.length; i < size; i++) {
            String package4i = makePackage(packageElements, i);
            if (packageScannedMap.containsKey(package4i)) {
                continue;
            }
            packageScannedMap.put(package4i, null);
            PackageTreeNode node = mPackageTreeNodeMap.get(package4i);
            if (node == null) {
                continue;
            }
            OverviewTreeNode overviewTreeNode = null;
            if (node.isTogetherWithParent()) {
                overviewTreeNode = mOverviewTreeNodeMap.get(node.parent.name);
                if (overviewTreeNode == null) {
                    continue;
                }
                overviewTreeNode.name += ".";
            } else {
                overviewTreeNode = new OverviewTreeNode();
                OverviewTreeNode overviewNodeParent = mOverviewTreeNodeMap.get(node.parent.name);
                if (overviewNodeParent == null) {
                    continue;
                }
                overviewTreeNode.parent = overviewNodeParent;
                overviewTreeNode.parent.children.add(overviewTreeNode);
            }
            mOverviewTreeNodeMap.put(package4i, overviewTreeNode);
            overviewTreeNode.name += packageElements[i];
            overviewTreeNode.title = package4i;
        }
    }

    private void buildOverviewTreeClass(ClassDocInfo classDocInfo) {
        String packageName = classDocInfo.packageName;
        OverviewTreeNode nodeParent = mOverviewTreeNodeMap.get(packageName);
        if (nodeParent == null) {
            return;
        }
        OverviewTreeNode node = new OverviewTreeNode(classDocInfo.simpleName, nodeParent);
        if (!classDocInfo.comment.isEmpty() && classDocInfo.comment.length() < 25) {
            if (classDocInfo.comment.indexOf('<') == -1 && classDocInfo.comment.indexOf('>') == -1) {
                if (isSingleLine(classDocInfo.comment)) {
                    node.comment = " " + classDocInfo.comment.replace("\n", "");
                }
            }
        }
        node.title = classDocInfo.name;
        nodeParent.children.add(node);
        mOverviewTreeNodeMap.put(classDocInfo.name, node);
    }

    private void buildPackageTree() {
        mPackageTreeNodeRoot = new PackageTreeNode();
        if (mPackageTreeNodeMap != null) {
            mPackageTreeNodeMap.clear();
        } else {
            mPackageTreeNodeMap = new HashMap<String, PackageTreeNode>();
        }
        mPackageTreeNodeMap.put(mPackageTreeNodeRoot.name, mPackageTreeNodeRoot);
        for (ClassDocInfo classDocInfo : mClassDocInfoTreeList) {
            String packageName = classDocInfo.packageName;
            PackageTreeNode node = mPackageTreeNodeMap.get(packageName);
            if (node != null) {
                node.classes.add(classDocInfo);
                continue;
            }
            if (packageName == null || packageName.isEmpty()) {
                mPackageTreeNodeRoot.classes.add(classDocInfo);
                continue;
            }
            PackageTreeNode parentNode = mPackageTreeNodeRoot;
            String[] packageElements = packageName.split("\\.");
            for (int i = 0, size = packageElements.length; i < size; i++) {
                String package4i = makePackage(packageElements, i);
                PackageTreeNode node4i = mPackageTreeNodeMap.get(package4i);
                if (node4i == null) {
                    node4i = new PackageTreeNode(package4i, parentNode);
                    mPackageTreeNodeMap.put(package4i, node4i);
                    parentNode.children.add(node4i);
                }
                parentNode = node4i;
            }
        }
    }

    /**
     * 根据路径导出markdown文档
     *
     * @param docPath 路径
     */
    public void report(String docPath) {
        if (mClassDocInfoList.isEmpty()) {
            return;
        }
        Writer writer = null;
        try {
            FileOutputStream fis = new FileOutputStream(docPath);
            writer = new OutputStreamWriter(fis, "UTF-8");
            exportTree(writer);
            String packageName = " ";
            for (ClassDocInfo classDocInfo : mClassDocInfoList) {
                export(writer, classDocInfo, packageName);
                packageName = classDocInfo.packageName;
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            IOUtil.closeQuietly(writer);
        }
    }

    private void exportTree(Writer writer) throws Exception {
        writer.write("## Overview ##\n<pre>\n");
        exportTree(writer, mOverviewTreeNodeRoot);
        writer.write("</pre>\n");
    }

    private void exportTree(Writer writer, OverviewTreeNode node) throws Exception {
        if (!node.name.isEmpty()) {
            OverviewTreeNode[] parents = node.parents();
            for (int i = parents.length - 1; i >= 0; i--) {
                OverviewTreeNode parentNode = parents[i];
                if (parentNode.name.isEmpty()) {
                    continue;
                }
                if (parentNode.isLastOne()) {
                    writer.write("    ");
                } else {
                    writer.write("│   ");
                }
            }
            if (node.isLastOne()) {
                writer.write("└──");
            } else {
                writer.write("├──");
            }
            writer.write(" <a href=\"#id" + node.id + "\" title=\"" + node.title + "\">");
            if (node.isPackage()) {
                writer.write("<b>" + node.name + "</b>");
            } else {
                writer.write(node.name);
            }
            writer.write("</a>");
            if (!node.comment.isEmpty()) {
                writer.write(node.comment);
            }
            writer.write("\n");
        }
        for (OverviewTreeNode subNode : node.children) {
            exportTree(writer, subNode);
        }
    }

    private static String makePackage(String[] packages, int packageIndex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= packageIndex; i++) {
            sb.append(packages[i]);
            if (i != packageIndex) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    private static boolean isSingleLine(String string) {
        if (string == null) {
            return true;
        }
        if (string.isEmpty()) {
            return true;
        }
        int firstLastIndexOfLine = string.lastIndexOf('\n');
        if (firstLastIndexOfLine == -1) {
            return true;
        } else {
            if (firstLastIndexOfLine != string.length() - 1) {
                return false;
            }
            return string.lastIndexOf('\n', string.length() - 2) == -1;
        }
    }

    private void export(Writer writer, ClassDocInfo doc, String lastPackage) throws Exception {
        System.out.println("export class " + doc.name + " ...");
        boolean isNewPackage = !doc.packageName.equals(lastPackage);
        if (isNewPackage) {
            String packageName = doc.packageName;
            if (packageName.isEmpty()) {
                packageName = "(default)";
            } else {
                OverviewTreeNode node = mOverviewTreeNodeMap.get(doc.packageName);
                writer.write("\n<a name=\"id" + node.id + "\"></a>\n");
                packageName = packageName.replace("_", "\\_");
            }
            writer.write("\n## " + packageName + " ##\n");
        }
        OverviewTreeNode node = mOverviewTreeNodeMap.get(doc.name);
        writer.write("\n<a name=\"id" + node.id + "\"></a>\n");
        writer.write("\n### " + doc.simpleName.replace("_", "\\_") + " ###\n");
        if (!doc.comment.isEmpty()) {
            writer.write("\n" + transformComment(doc.comment) + "\n");
        }
        Collections.sort(doc.fields, new FieldDocInfoComparator());
        for (FieldDocInfo fieldDocInfo : doc.fields) {
            export(writer, fieldDocInfo);
        }
        Collections.sort(doc.methods, new MethodDocInfoComparator());
        for (MethodDocInfo methodDocInfo : doc.methods) {
            export(writer, methodDocInfo);
        }
        writer.write("\n------");
        System.out.println("export class " + doc.name + " success");
    }

    private static void export(Writer writer, FieldDocInfo doc) throws Exception {
        writer.write("\n<br/><b>" + doc.name.replace("_", "\\_") + "</b> : " + doc.type.replace("_", "\\_") + "\n");
        if (!doc.comment.isEmpty()) {
            writer.write("\n" + transformComment(doc.comment) + "\n");
        }
        System.out.println("export field " + doc.name + " success");
    }

    private static void export(Writer writer, MethodDocInfo doc) throws Exception {
        writer.write("\n<br/><b>" + doc.name.replace("_", "\\_") + "</b>" + doc.parameters.replace("_", "\\_") + "\n");
        if (!doc.comment.isEmpty()) {
            writer.write("\n" + transformComment(doc.comment) + "\n");
        }
        System.out.println("export method " + doc.name + " success");
    }

    private static String transformComment(String comment) {
        StringBuilder sb = new StringBuilder();
        String[] lines = comment.split("\n");
        for (int i = 0, length = lines.length; i < length; i++) {
            String line = lines[i];
            line = line.replace("<U>", "");
            line = line.replace("</U>", "");
            line = line.replace("<S>", "");
            line = line.replace("</S>", "");
            sb.append("> ");
            sb.append(line);
            if (i != length - 1) {
                sb.append("<br/>\n");
            }
        }
        return sb.toString();
    }

}
