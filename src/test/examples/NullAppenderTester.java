//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class NullAppenderTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new NullAppenderTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.apache.log4j.varia.NullAppender var0 = new org.apache.log4j.varia.NullAppender();
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.setThreshold((org.apache.log4j.Priority) null);
//                    final boolean var1 = var0.isAsSevereAsThreshold((org.apache.log4j.Priority) null);
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
//                    final java.lang.String var4 = var0.getName();
//                    final org.apache.log4j.Priority var6 = var0.getThreshold();
//                    final org.apache.log4j.Priority var1 = org.apache.log4j.Priority.WARN;
//                    final boolean var2 = var0.isAsSevereAsThreshold((org.apache.log4j.Priority) var1);
//                    var0.setThreshold((org.apache.log4j.Priority) var1);
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
