//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class BufferedInputStreamTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new BufferedInputStreamTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final java11.io.BufferedInputStream var0 = new java11.io.BufferedInputStream((java11.io.InputStream) null);
//        final java11.io.BufferedInputStream var1 = new java11.io.BufferedInputStream((java11.io.InputStream) var0);
//
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    final long var2 = var1.skip((long) -3L);
//                    var1.close();
//                    var1.close();
//                    var1.close();
//                    var1.close();
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
//                    var1.close();
//                    var1.close();
//                    var1.close();
//                    var1.close();
//                    var1.close();
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
