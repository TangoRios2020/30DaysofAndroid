#### 1 Android 编译过程
AndroidManifest.xml --> info.plist

layout.xml -> View --> Android Asset Packaging Tool --> attach to .apk

MainActivity类的onCreate(Bundle)方法调用setContentView(...)方法时， MainActivity 使用 LayoutInflater 类实例化布局文件中定义的每一个 View 对象.

layout 也是一种文件, 和 textview 等同等地位, 只是用法不同.

老生常谈: 区分视图层, 和业务逻辑层

现代Android编译系统使用Gradle编译工具 --> Gradle 是一个编译工具
Gradle --> XcodeBuildTool



