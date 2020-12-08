package com.david.module.util.vendors;

public class ClazzUtil {

//    Bootstrap classLoader：采用native code实现，是JVM的一部分，主要加载JVM自身工作需要的类，
// 如java.lang.*、java.uti.*等； 这些类位于$JAVA_HOME/jre/lib/rt.jar。Bootstrap ClassLoader不继承自ClassLoader，因为它不是一个普通的Java类，底层由C++编写，已嵌入到了JVM内核当中，当JVM启动后，Bootstrap ClassLoader也随着启动，负责加载完核心类库后，并构造Extension ClassLoader和App ClassLoader类加载器。
//    BootStrap ClassLoader加载rt.jar下的文件，也就是java最最核心的部分；
// 然后由Extension ClassLoader加载ext下的文件；
// 再有App ClassLoader加载用户自己的文件。
//    由于BootStrap ClassLoader是用c++写的，所以在返回该ClassLoader时会返回null。

    /**
     * ClassLoader classLoader = String.class.getClassLoader(); 得到的是null
     * 是否是JAVA类
     *
     * @param clazz
     * @return
     */
    public static boolean isJavaClass(Class clazz) {
        return clazz != null && clazz.getClassLoader() == null;
    }
}
