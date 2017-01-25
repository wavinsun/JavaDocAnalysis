package cn.mutils.app.javadoc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 概览树节点
 */
public class OverviewTreeNode {

    public int id;
    public String name = "";
    public String title = "";
    public String comment = "";
    public OverviewTreeNode parent;
    public List<OverviewTreeNode> children = new ArrayList<OverviewTreeNode>();

    public OverviewTreeNode() {

    }

    public OverviewTreeNode(String packageOrClass, OverviewTreeNode parentNode) {
        name = packageOrClass;
        parent = parentNode;
    }

    public boolean isPackage() {
        if (parent == null) { //根节点
            return true;
        }
        return children.size() != 0;
    }

    public boolean isClass() {
        return !isPackage();
    }

    public boolean isLastOne() {
        if (parent == null) {
            return true;
        }
        return parent.children.indexOf(this) == parent.children.size() - 1;
    }

    public OverviewTreeNode[] parents() {
        List<OverviewTreeNode> parentList = new ArrayList<OverviewTreeNode>();
        OverviewTreeNode node = this;
        while (node.parent != null) {
            parentList.add(node.parent);
            node = node.parent;
        }
        return parentList.toArray(new OverviewTreeNode[parentList.size()]);
    }

    public int resetAllIds(int startId) {
        this.id = startId + 1;
        int end = this.id;
        for (OverviewTreeNode child : this.children) {
            end = child.resetAllIds(end);
        }
        return end;
    }

}
