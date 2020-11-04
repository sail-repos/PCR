//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class LoggerTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new LoggerTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final java141.util.logging.Logger var0 = java141.util.logging.Logger.getAnonymousLogger();
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    final bug4779253.MyFilter var1 = new bug4779253.MyFilter();
//                    var0.setFilter((java141.util.logging.Filter) var1);
//                    final java141.util.logging.Level var2 = java141.util.logging.Level.WARNING;
//                    var0.log((java141.util.logging.Level) var2, (java.lang.String) "Z", (java.lang.Object) null);
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
//                    final java141.util.logging.Level var1 = java141.util.logging.Level.OFF;
//                    var0.log((java141.util.logging.Level) var1, (java.lang.String) "", (java.lang.Object) var0);
//                    var0.setFilter((java141.util.logging.Filter) null);
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
