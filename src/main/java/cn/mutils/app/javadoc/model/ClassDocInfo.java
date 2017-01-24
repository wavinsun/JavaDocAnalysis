package cn.mutils.app.javadoc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 类文档信息
 */
public class ClassDocInfo {

    public String name = "";
    public String packageName = "";
    public String simpleName = "";
    public String comment = "";
    public List<MethodDocInfo> methods = new ArrayList<MethodDocInfo>();
    public List<FieldDocInfo> fields = new ArrayList<FieldDocInfo>();

}
