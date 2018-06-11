# leabback
a library for tv lenkback 
上传项目到Jcenter
　　准备工作都做完啦，最后一步就是上传操作了，点击AndroidStudio底部的Terminal，观察下Terminal显示的路径是否是你当前项目的root。

这里如果你系统配置了gradle的用户环境，输入gradle install，如果没有配置gradle用户环境，输入gradlew install，如果没有问题，最终你会看到BUILD SUCCESSFUL。
如果你看到了生成javadoc时编译不过，那么要看下在gradle中task javadoc下有没有failOnError false这句话，在刚才编写gradle时提示过了。如果加了这句而你的javadoc写的不规范会有警告，你不用鸟它。
最后一步，运行gradle install后看到BUILD SUCCESSFUL后，再输入上传命令gradle bintrayUpload，等一分钟左右就执行完了，会提示SUCCESSFUL。
浏览器https://bintray.com/后会看到你的项目。
