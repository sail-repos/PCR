//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class Vector142Tester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new Vector142Tester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final java142.util.Vector var0 = new java142.util.Vector((int) 1, (int) 3);
//        final java142.util.Vector var1 = new java142.util.Vector((java.util.Collection) var0);
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var1.sort((java.util.Comparator) null);
//                    final boolean var2 = var0.contains((java.lang.Object) var0);
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
//                    final boolean var3 = var0.add((java.lang.Object) var0);
//                    final boolean var2 = var0.contains((java.lang.Object) var0);
//                    var1.setSize((int) 100);
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
