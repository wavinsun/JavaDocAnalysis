package cn.mutils.app.javadoc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 包结构树节点
 */
public class PackageTreeNode {

    public String name = "";

    public PackageTreeNode parent;

    public List<PackageTreeNode> children = new ArrayList<PackageTreeNode>();

    public List<ClassDocInfo> classes = new ArrayList<ClassDocInfo>();

    public PackageTreeNode() {

    }

    public PackageTreeNode(String packageName, PackageTreeNode parentNode) {
        name = packageName;
        parent = parentNode;
    }

    /**
     * 可以合并 标识该节点是否可以合并到父节点
     *
     * @return
     */
    public boolean isTogetherWithParent() {
        if (parent == null) {
            return false;
        }
        if (parent.name.isEmpty()) { // 根节点
            return false;
        }
        return parent.classes.size() == 0 && parent.children.size() <= 1 && parent.children.indexOf(this) == 0;
    }

}
