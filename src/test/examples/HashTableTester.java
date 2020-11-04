//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class HashTableTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new HashTableTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final java167.util.Hashtable var0 = new java167.util.Hashtable((int) 2);
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.rehash();
//                    final java.lang.Object var1 = var0.clone();
//                    final java.util.HashMap var5 = new java.util.HashMap((java.util.Map) var0);
//                    final java.lang.String var6 = var5.toString();
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
//                    final java.util.HashMap var5 = new java.util.HashMap((java.util.Map) var0);
//                    final java.lang.String var6 = var5.toString();
//                    final java.lang.Object var7 = var0.put((java.lang.Object) var6, (java.lang.Object) var6);
//                    final java.lang.Object var8 = var0.put((java.lang.Object) var6, (java.lang.Object) var5);
//                    final java.lang.Object var1 = var0.clone();
//                    var0.rehash();
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
