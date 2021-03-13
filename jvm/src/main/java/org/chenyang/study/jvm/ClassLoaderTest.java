package org.chenyang.study.jvm;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : ChenYang
 * @date : 2020-09-25 3:42 下午
 * @since :
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader myClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(".") + 1)+".class";
                try {
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    } else {
                        byte[] b = new byte[is.available()];
                        is.read(b);
                        return defineClass(name, b, 0, b.length);
                    }
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object object = myClassLoader.loadClass("org.chenyang.study.jvm.ClassLoaderTest");
        System.out.println(object.getClass());
        // 一个是由虚拟机的应用程序类加载器所加载的，另外一个是由我们自定义的类加载器加载的，虽然它们都来自同一个Class文件，但在Java虚拟机中仍然是两个互相独立的类，做对象所属类型检查时的结果自然为false。
        System.out.println(object instanceof org.chenyang.study.jvm.ClassLoaderTest);

    }
}
