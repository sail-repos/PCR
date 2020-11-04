//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class ConcurrentHashMapTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new ConcurrentHashMapTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final java16027.util.concurrent.ConcurrentHashMap var0 = new java16027.util.concurrent.ConcurrentHashMap((int) 2, (float) 0.1f);
//        final java.util.Collection var1 = var0.values();
//        final java.lang.Object var2 = var0.get((java.lang.Object) var0);
//        final java.util.Collection var3 = var0.values();
//        final java.util.TreeSet var4 = new java.util.TreeSet();
//        final java.util.Comparator var5 = var4.comparator();
//        final java.util.TreeMap var6 = new java.util.TreeMap((java.util.Comparator) var5);
//        final java.util.ArrayList var7 = new java.util.ArrayList();
//        final java.lang.String var8 = var7.toString();
//        final java.util.LinkedList var9 = new java.util.LinkedList((java.util.Collection) var7);
//        final java.lang.String var10 = var9.toString();
//        final boolean var11 = var0.replace((java.lang.Object) var6, (java.lang.Object) var8, (java.lang.Object) var10);
//        final java.util.Set var12 = var0.entrySet();
//
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    final java.util.Vector var13 = new java.util.Vector((java.util.Collection) var3);
//                    final java.lang.String var14 = var13.toString();
//                    final java.util.concurrent.LinkedBlockingQueue var15 = new java.util.concurrent.LinkedBlockingQueue();
//                    final java.lang.Object var16 = var0.put((java.lang.Object) var14, (java.lang.Object) var15);
//                    final java.util.TreeMap var17 = new java.util.TreeMap((java.util.Map) var0);
//                    final java.lang.Object var18 = var0.get((java.lang.Object) var17);
//
//
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
//                    final java.util.TreeMap var13 = new java.util.TreeMap();
//                    final java.lang.Object var14 = var0.get((java.lang.Object) var13);
//                    final java.lang.Object var15 = var0.put((java.lang.Object) var9, (java.lang.Object) var9);
//
//
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
