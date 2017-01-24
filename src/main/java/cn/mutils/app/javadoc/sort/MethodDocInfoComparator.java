package cn.mutils.app.javadoc.sort;

import cn.mutils.app.javadoc.model.MethodDocInfo;

import java.util.Comparator;

/**
 * 方法文档信息排序辅助类
 */
public class MethodDocInfoComparator implements Comparator<MethodDocInfo> {

    @Override
    public int compare(MethodDocInfo o1, MethodDocInfo o2) {
        int result = o1.name.compareTo(o2.name);
        if (result != 0) {
            return result;
        }
        return o1.parameters.compareTo(o2.parameters);
    }

}
