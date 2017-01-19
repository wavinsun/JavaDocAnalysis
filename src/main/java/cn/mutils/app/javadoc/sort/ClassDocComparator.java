package cn.mutils.app.javadoc.sort;

import com.sun.javadoc.ClassDoc;

import java.util.Comparator;

public class ClassDocComparator implements Comparator<ClassDoc> {

    @Override
    public int compare(ClassDoc o1, ClassDoc o2) {
        return o1.qualifiedName().compareTo(o2.qualifiedName());
    }
}
