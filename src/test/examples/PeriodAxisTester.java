//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class PeriodAxisTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new PeriodAxisTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.jfree.chart.axis.PeriodAxis var0 = new org.jfree.chart.axis.PeriodAxis((java.lang.String) null);
//        final org.jfree.data.time.RegularTimePeriod var1 = var0.getFirst();
//        final org.jfree.chart.axis.PeriodAxis var2 = new org.jfree.chart.axis.PeriodAxis((java.lang.String) "", (org.jfree.data.time.RegularTimePeriod) var1, (org.jfree.data.time.RegularTimePeriod) null);
//
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.setUpperMargin((double) -3.0d);
//                    var0.setUpperMargin((double) -1.0d);
//                    final boolean var1 = var0.isVisible();
//                    final double var5 = var0.getLowerBound();
//                    final boolean var7 = var0.isVisible();
//                    final double var6 = var0.getLowerBound();
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
//                    var2.centerRange((double) -3.0d);
//                    final boolean var1 = var0.isVisible();
//                    final double var5 = var0.getLowerBound();
//                    final double var3 = var0.getLowerBound();
//                    final boolean var4 = var0.isVisible();
//                    var0.setRangeWithMargins((double) -1.0d, (double) 0.1d);
//                    var0.setUpperMargin((double) 0.0d);
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
