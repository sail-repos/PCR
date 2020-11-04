//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class StringBufferTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new StringBufferTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final java16027.lang.StringBuffer var0 = new java16027.lang.StringBuffer((int) 3);
//        var0.trimToSize();
//        final java16027.lang.StringBuffer var1 = var0.append((java.lang.CharSequence) var0);
//        final java.util.concurrent.ConcurrentSkipListMap var2 = new java.util.concurrent.ConcurrentSkipListMap();
//        final java.lang.Object var3 = var2.remove((java.lang.Object) var2);
//        final java16027.lang.StringBuffer var4 = var0.append((java.lang.Object) var3);
//        final int var5 = var0.lastIndexOf((java.lang.String) "j");
//        final java16027.lang.StringBuffer var6 = var0.insert((int) 2, (java.lang.CharSequence) var1);
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    final java16027.lang.StringBuffer var7 = var0.insert((int) 1, (int) var5);
//                    var0.setLength((int) 0);
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
//                    final java16027.lang.StringBuffer var7 = var0.insert((int) 2, (int) -2);
//                    var0.setLength((int) 100);
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
