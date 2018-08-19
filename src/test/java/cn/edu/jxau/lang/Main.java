package cn.edu.jxau.lang;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.junit.Test;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runner.JUnitCore;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;

import java.lang.annotation.Native;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/6/4
 * Time:下午7:27
 */
public class Main {

    @Test
    public void test01() throws InvocationTargetException, IllegalAccessException {



        Student student = new Student();
        ConvertUtils.register(new Converter() {

            public Object convert(Class type, Object value) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return simpleDateFormat.parse(value.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, Date.class);

        BeanUtils.setProperty(student, "birthday", "1995-09-20");
        System.out.println(student);
    }


    ///Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/bin/java -ea -Didea.test.cyclic.buffer.size=1048576 -Dfile.encoding=UTF-8 -classpath "/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/junit/lib/junit-rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/junit/lib/junit5-rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/lib/tools.jar:/Users/fudashi/dev/iBeanUtils/target/test-classes:/Users/fudashi/dev/iBeanUtils/target/classes:/Users/fudashi/.m2/repository/org/projectlombok/lombok/1.16.20/lombok-1.16.20.jar:/Users/fudashi/.m2/repository/commons-beanutils/commons-beanutils/1.9.3/commons-beanutils-1.9.3.jar:/Users/fudashi/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar:/Users/fudashi/.m2/repository/commons-collections/commons-collections/3.2.2/commons-collections-3.2.2.jar:/Users/fudashi/.m2/repository/junit/junit/4.12/junit-4.12.jar:/Users/fudashi/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar" com.intellij.rt.execution.junit.JUnitStarter -ideVersion5 -junit4 cn.edu.jxau.lang.Main,test01
    //八月 19, 2018 6:03:20 下午 org.apache.commons.beanutils.converters.DateConverter toDate
    //警告:     DateConverter does not support default String to 'Date' conversion.
    //        八月 19, 2018 6:03:20 下午 org.apache.commons.beanutils.converters.DateConverter toDate
    //警告:     (N.B. Re-configure Converter or use alternative implementation)
    //
    //org.apache.commons.beanutils.ConversionException: DateConverter does not support default String to 'Date' conversion.
    //
    //at org.apache.commons.beanutils.converters.DateTimeConverter.toDate(DateTimeConverter.java:474)
    //at org.apache.commons.beanutils.converters.DateTimeConverter.convertToType(DateTimeConverter.java:347)
    //at org.apache.commons.beanutils.converters.AbstractConverter.convert(AbstractConverter.java:169)
    //at org.apache.commons.beanutils.ConvertUtilsBean.convert(ConvertUtilsBean.java:491)
    //at org.apache.commons.beanutils.BeanUtilsBean.setProperty(BeanUtilsBean.java:1007)
    //at org.apache.commons.beanutils.BeanUtils.setProperty(BeanUtils.java:454)
    //at cn.edu.jxau.lang.Main.test01(Main.java:29)
    //at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    //at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
    //at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    //at java.lang.reflect.Method.invoke(Method.java:498)
    //at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
    //at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
    //at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
    //at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
    //at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
    //at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
    //at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
    //at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
    //at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
    //at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
    //at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
    //at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
    //at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
    //at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
    //at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
    //at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
    //at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
    //at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)

}
