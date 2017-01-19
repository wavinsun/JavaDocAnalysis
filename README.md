# JavaDocAnalysis #
## 简介 ##
实现从java源代码直接提取javadoc元素生成markdown格式的API文档
## 配置 ##
javadoc.json为程序的配置文件，默认内容为：
<pre><code>{
  "src": [
    "src/main/java"
  ],
  "doc": "javadoc.md"
}</code></pre>
src属性为源代码目录，可以配置多个源代码目录，doc属性为markdown输出路径
## 使用 ##
windows操作系统直接启动javadoc.bat即可,类linux系统请使用javadoc.sh启动