## Overview ##
<pre>
└── <a href="#id1" title="cn.mutils.app.javadoc"><b>cn.mutils.app.javadoc</b></a>
    ├── <a href="#id2" title="cn.mutils.app.javadoc.model"><b>model</b></a>
    │   ├── <a href="#id3" title="cn.mutils.app.javadoc.model.ClassDocInfo">ClassDocInfo</a>  类文档信息
    │   ├── <a href="#id4" title="cn.mutils.app.javadoc.model.FieldDocInfo">FieldDocInfo</a>  成员变量文档信息
    │   ├── <a href="#id5" title="cn.mutils.app.javadoc.model.MethodDocInfo">MethodDocInfo</a>  方法文档信息
    │   ├── <a href="#id6" title="cn.mutils.app.javadoc.model.OverviewTreeNode">OverviewTreeNode</a>  概览树节点
    │   └── <a href="#id7" title="cn.mutils.app.javadoc.model.PackageTreeNode">PackageTreeNode</a>  包结构树节点
    ├── <a href="#id8" title="cn.mutils.app.javadoc.sort"><b>sort</b></a>
    │   ├── <a href="#id9" title="cn.mutils.app.javadoc.sort.ClassDocInfoComparator">ClassDocInfoComparator</a>  类文档信息排序辅助类
    │   ├── <a href="#id10" title="cn.mutils.app.javadoc.sort.ClassDocInfoTreeComparator">ClassDocInfoTreeComparator</a>  类文档树信息排序辅助类
    │   ├── <a href="#id11" title="cn.mutils.app.javadoc.sort.FieldDocInfoComparator">FieldDocInfoComparator</a>  成员变量文档信息排序辅助类
    │   └── <a href="#id12" title="cn.mutils.app.javadoc.sort.MethodDocInfoComparator">MethodDocInfoComparator</a>  方法文档信息排序辅助类
    ├── <a href="#id13" title="cn.mutils.app.javadoc.util"><b>util</b></a>
    │   ├── <a href="#id14" title="cn.mutils.app.javadoc.util.FileUtil">FileUtil</a>  文件实用类
    │   └── <a href="#id15" title="cn.mutils.app.javadoc.util.IOUtil">IOUtil</a>  IO实用类
    ├── <a href="#id16" title="cn.mutils.app.javadoc.JavaDocAnalysis">JavaDocAnalysis</a>  分析入口
    ├── <a href="#id17" title="cn.mutils.app.javadoc.JavaDocConfig">JavaDocConfig</a>  配置信息
    ├── <a href="#id18" title="cn.mutils.app.javadoc.JavaDocParser">JavaDocParser</a>  解析器
    └── <a href="#id19" title="cn.mutils.app.javadoc.JavaDocReporter">JavaDocReporter</a>  报表工具
</pre>

<a name="id1"></a>

## cn.mutils.app.javadoc ##

<a name="id16"></a>

### JavaDocAnalysis ###

>  分析入口

<br/><b>main</b>(String args)

------
<a name="id17"></a>

### JavaDocConfig ###

>  配置信息

------
<a name="id18"></a>

### JavaDocParser ###

>  解析器

<br/><b>parse</b>(JavaDocConfig config)

<br/><b>start</b>(RootDoc rootDoc)

>  覆盖基类方法<br/>
> <br/>
>  @param rootDoc doc<br/>
>  @return

------
<a name="id19"></a>

### JavaDocReporter ###

>  报表工具

<br/><b>report</b>(String docPath)

>  根据路径导出markdown文档<br/>
> <br/>
>  @param docPath 路径

------
<a name="id2"></a>

## cn.mutils.app.javadoc.model ##

<a name="id3"></a>

### ClassDocInfo ###

>  类文档信息

------
<a name="id4"></a>

### FieldDocInfo ###

>  成员变量文档信息

------
<a name="id5"></a>

### MethodDocInfo ###

>  方法文档信息

------
<a name="id6"></a>

### OverviewTreeNode ###

>  概览树节点

<br/><b>isClass</b>()

<br/><b>isLastOne</b>()

<br/><b>isPackage</b>()

<br/><b>parents</b>()

<br/><b>resetAllIds</b>(int startId)

------
<a name="id7"></a>

### PackageTreeNode ###

>  包结构树节点

<br/><b>isTogetherWithParent</b>()

>  可以合并 标识该节点是否可以合并到父节点<br/>
> <br/>
>  @return

------
<a name="id8"></a>

## cn.mutils.app.javadoc.sort ##

<a name="id9"></a>

### ClassDocInfoComparator ###

>  类文档信息排序辅助类

<br/><b>compare</b>(ClassDocInfo o1, ClassDocInfo o2)

------
<a name="id10"></a>

### ClassDocInfoTreeComparator ###

>  类文档树信息排序辅助类

<br/><b>compare</b>(ClassDocInfo o1, ClassDocInfo o2)

------
<a name="id11"></a>

### FieldDocInfoComparator ###

>  成员变量文档信息排序辅助类

<br/><b>compare</b>(FieldDocInfo o1, FieldDocInfo o2)

------
<a name="id12"></a>

### MethodDocInfoComparator ###

>  方法文档信息排序辅助类

<br/><b>compare</b>(MethodDocInfo o1, MethodDocInfo o2)

------
<a name="id13"></a>

## cn.mutils.app.javadoc.util ##

<a name="id14"></a>

### FileUtil ###

>  文件实用类

<br/><b>getString</b>(File file)

------
<a name="id15"></a>

### IOUtil ###

>  IO实用类

<br/><b>closeQuietly</b>(Closeable c)

<br/><b>copy</b>(InputStream in, OutputStream out)

------