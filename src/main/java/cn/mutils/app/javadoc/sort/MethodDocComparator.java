package cn.mutils.app.javadoc.sort;

import com.sun.javadoc.MethodDoc;

import java.util.Comparator;

public class MethodDocComparator implements Comparator<MethodDoc> {

    @Override
    public int compare(MethodDoc o1, MethodDoc o2) {
        return o1.name().compareTo(o2.name());
    }
}
