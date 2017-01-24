package cn.mutils.app.javadoc.sort;

import cn.mutils.app.javadoc.model.ClassDocInfo;

import java.util.Comparator;

/**
 * 类文档树信息排序辅助类
 */
public class ClassDocInfoTreeComparator implements Comparator<ClassDocInfo> {

    @Override
    public int compare(ClassDocInfo o1, ClassDocInfo o2) {
        return (o1.packageName + "~." + o1.simpleName).compareTo(o2.packageName + "~." + o2.simpleName);
    }
}
