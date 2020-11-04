//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class XYSeriesTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new XYSeriesTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.jfree.data.XYSeries var0 = new org.jfree.data.XYSeries((java.lang.String) "R", (boolean) true);
//
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.setMaximumItemCount((int) -1);
//                    var0.add((double) 1.0d, (java.lang.Number) null);
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
//                    var0.add((double) -0.1d, (java.lang.Number) null);
//                    var0.setMaximumItemCount((int) 0);
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
