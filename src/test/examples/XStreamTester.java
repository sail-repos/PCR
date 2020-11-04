//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class XStreamTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new XStreamTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final com.thoughtworks.xstream.XStream var0 = new com.thoughtworks.xstream.XStream();
//        final com.thoughtworks.xstream.converters.reflection.ReflectionProvider var1 = var0.getReflectionProvider();
//        final com.thoughtworks.xstream.XStream var2 = new com.thoughtworks.xstream.XStream((com.thoughtworks.xstream.converters.reflection.ReflectionProvider) var1);
//
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    final com.thoughtworks.xstream.io.naming.NoNameCoder var3 = new com.thoughtworks.xstream.io.naming.NoNameCoder();
//                    final java.lang.String var4 = var3.encodeAttribute((java.lang.String) "M/");
//                    final java.lang.String var5 = var2.toXML((java.lang.Object) var4);
//                    var2.setMarshallingStrategy((com.thoughtworks.xstream.MarshallingStrategy) null);
//                } catch (Throwable t) {
//                    throw new RuntimeException(t);
//                }
//            }
//        });
//
//        Thread t2 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.autodetectAnnotations((boolean) false);
//                    var0.autodetectAnnotations((boolean) true);
//                    var0.useAttributeFor((java.lang.String) "", (java.lang.Class) null);
//                    var0.useAttributeFor((java.lang.String) "b#qFQ,Z", (java.lang.Class) null);
//                    var2.setMarshallingStrategy((com.thoughtworks.xstream.MarshallingStrategy) null);
//                    final com.thoughtworks.xstream.core.TreeMarshallingStrategy var3 = new com.thoughtworks.xstream.core.TreeMarshallingStrategy();
//                    var2.setMarshallingStrategy((com.thoughtworks.xstream.MarshallingStrategy) var3);
//                } catch (Throwable t) {
//                    throw new RuntimeException(t);
//                }
//            }
//        });
//
//        t1.start();
//        t2.start();
//
//        try {
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
