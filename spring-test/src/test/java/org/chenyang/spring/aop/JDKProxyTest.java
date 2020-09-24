package org.chenyang.spring.aop;

import org.chenyang.spring.aop.dp.ISubObject;
import org.chenyang.spring.aop.dp.MyJdkHandler;
import org.chenyang.spring.aop.dp.SubObjectImpl;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @author : ChenYang
 * @date : 2020-09-04 5:04 下午
 * @since :
 */
public class JDKProxyTest {

    @Test
    public void testJdkHandler() {
        // 传入动态代理的实现类class
        MyJdkHandler jdkHandler = new MyJdkHandler(SubObjectImpl.class);
        // 创建接口的代理对象，这里其实是生成了实现ISubjectObject接口的一个代理类对象
        ISubObject subObjectProxy = (ISubObject) Proxy.newProxyInstance(JDKProxyTest.class.getClassLoader(),
                new Class[] { ISubObject.class },
                jdkHandler);
        // 执行代理对象
        subObjectProxy.execute();
    }

    @Test
    public void testWriteProxyClass() throws IOException {
        byte[] proxyClassBytes = ProxyGenerator.generateProxyClass("Proxy1", new Class[]{ ISubObject.class }, 1);

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/chenyang/Downloads/Proxy1.class");
        fileOutputStream.write(proxyClassBytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
