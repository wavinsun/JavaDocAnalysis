package cn.mutils.app.javadoc.sort;

import cn.mutils.app.javadoc.model.FieldDocInfo;

import java.util.Comparator;

/**
 * 成员变量文档信息排序辅助类
 */
public class FieldDocInfoComparator implements Comparator<FieldDocInfo> {

    @Override
    public int compare(FieldDocInfo o1, FieldDocInfo o2) {
        return o1.name.compareTo(o2.name);
    }

}
