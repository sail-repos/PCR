//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class FileAppenderTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new FileAppenderTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.apache.log4j.FileAppender var0 = new org.apache.log4j.FileAppender();
//        var0.append((org.apache.log4j.spi.LoggingEvent) null);
//        var0.setImmediateFlush((boolean) true);
//        var0.setBufferSize((int) 0);
//        var0.finalize();
//        final boolean var1 = var0.getBufferedIO();
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.setFile((java.lang.String) "");
//                    var0.activateOptions();
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
//                    var0.activateOptions();
//                    var0.activateOptions();
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
