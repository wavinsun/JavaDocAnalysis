package cn.mutils.app.javadoc.sort;

import cn.mutils.app.javadoc.model.ClassDocInfo;

import java.util.Comparator;

/**
 * 类文档信息排序辅助类
 */
public class ClassDocInfoComparator implements Comparator<ClassDocInfo> {

    @Override
    public int compare(ClassDocInfo o1, ClassDocInfo o2) {
        return o1.name.compareTo(o2.name);
    }

}
