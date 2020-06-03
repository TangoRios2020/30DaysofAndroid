#### 1 Android 编译过程
AndroidManifest.xml --> info.plist

layout.xml -> View --> Android Asset Packaging Tool --> attach to .apk

MainActivity类的onCreate(Bundle)方法调用setContentView(...)方法时， MainActivity 使用 LayoutInflater 类实例化布局文件中定义的每一个 View 对象.

layout 也是一种文件, 和 textview 等同等地位, 只是用法不同.

老生常谈: 区分视图层, 和业务逻辑层

现代Android编译系统使用Gradle编译工具 --> Gradle 是一个编译工具
Gradle --> XcodeBuildTool

#### 2 Android 开发中的 MVC
##### 模型
##### 控制器: 
Activity 这里可以认为是控制器, 是视图对象与模型对象的联系纽带。控制器对象响应 视图对象触发的各类事件，此外还管理
着模型对象与视图层间的数据流动。 在Android的世界里，控制器通常是Activity、Fragment或Service的子类

##### 视图(布局)
常见的比如 XML 文件


#### 3 尽量保证单一职责