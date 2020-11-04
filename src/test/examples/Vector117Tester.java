//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class Vector117Tester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new Vector117Tester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final java117.util.Vector var0 = new java117.util.Vector((int) 3);
//        var0.copyInto((java.lang.Object[]) null);
//        var0.trimToSize();
//        final int var1 = var0.capacity();
//        final int var2 = var0.indexOf((java.lang.Object) null);
//        final boolean var3 = var0.isEmpty();
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    final int var4 = var0.indexOf((java.lang.Object) null);
//                    final int var5 = var0.indexOf((java.lang.Object) null);
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
//                    final int var4 = var0.indexOf((java.lang.Object) null);
//                    final boolean var6 = var0.isEmpty();
//                    final boolean var7 = var0.isEmpty();
//                    var0.insertElementAt((java.lang.Object) var0, (int) var1);
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
