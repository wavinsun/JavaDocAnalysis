## Overview ##
<pre><code>└── <b>cn.mutils.app.javadoc</b>
    ├── <b>model</b>
    │   ├── ClassDocInfo  类文档信息
    │   ├── FieldDocInfo  成员变量文档信息
    │   ├── MethodDocInfo  方法文档信息
    │   ├── OverviewTreeNode  概览树节点
    │   └── PackageTreeNode  包结构树节点
    ├── <b>sort</b>
    │   ├── ClassDocInfoComparator  类文档信息排序辅助类
    │   ├── ClassDocInfoTreeComparator  类文档树信息排序辅助类
    │   ├── FieldDocInfoComparator  成员变量文档信息排序辅助类
    │   └── MethodDocInfoComparator  方法文档信息排序辅助类
    ├── <b>util</b>
    │   ├── FileUtil  文件实用类
    │   └── IOUtil  IO实用类
    ├── JavaDocAnalysis  分析入口
    ├── JavaDocConfig  配置信息
    ├── JavaDocParser  解析器
    └── JavaDocReporter  报表工具
</pre></code>

## cn.mutils.app.javadoc ##

### JavaDocAnalysis ###

>  分析入口

<br/><b>main</b>(String args)

------
### JavaDocConfig ###

>  配置信息

------
### JavaDocParser ###

>  解析器

<br/><b>parse</b>(JavaDocConfig config)

<br/><b>start</b>(RootDoc rootDoc)

>  覆盖基类方法<br/>
> <br/>
>  @param rootDoc doc<br/>
>  @return

------
### JavaDocReporter ###

>  报表工具

<br/><b>report</b>(String docPath)

>  根据路径导出markdown文档<br/>
> <br/>
>  @param docPath 路径

------
## cn.mutils.app.javadoc.model ##

### ClassDocInfo ###

>  类文档信息

------
### FieldDocInfo ###

>  成员变量文档信息

------
### MethodDocInfo ###

>  方法文档信息

------
### OverviewTreeNode ###

>  概览树节点

<br/><b>isClass</b>()

<br/><b>isLastOne</b>()

<br/><b>isPackage</b>()

<br/><b>parents</b>()

------
### PackageTreeNode ###

>  包结构树节点

<br/><b>isTogetherWithParent</b>()

>  可以合并 标识该节点是否可以合并到父节点<br/>
> <br/>
>  @return

------
## cn.mutils.app.javadoc.sort ##

### ClassDocInfoComparator ###

>  类文档信息排序辅助类

<br/><b>compare</b>(ClassDocInfo o1, ClassDocInfo o2)

------
### ClassDocInfoTreeComparator ###

>  类文档树信息排序辅助类

<br/><b>compare</b>(ClassDocInfo o1, ClassDocInfo o2)

------
### FieldDocInfoComparator ###

>  成员变量文档信息排序辅助类

<br/><b>compare</b>(FieldDocInfo o1, FieldDocInfo o2)

------
### MethodDocInfoComparator ###

>  方法文档信息排序辅助类

<br/><b>compare</b>(MethodDocInfo o1, MethodDocInfo o2)

------
## cn.mutils.app.javadoc.util ##

### FileUtil ###

>  文件实用类

<br/><b>getString</b>(File file)

------
### IOUtil ###

>  IO实用类

<br/><b>closeQuietly</b>(Closeable c)

<br/><b>copy</b>(InputStream in, OutputStream out)

------