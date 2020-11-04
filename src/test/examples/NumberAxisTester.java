//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class NumberAxisTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new NumberAxisTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.jfree.chart.axis.NumberAxis var0 = new org.jfree.chart.axis.NumberAxis();
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    final org.jfree.chart.axis.NumberTickUnit var1 = org.jfree.chart.axis.NumberAxis.DEFAULT_TICK_UNIT;
//                    var0.setTickUnit((org.jfree.chart.axis.NumberTickUnit) var1);
//                    final double var2 = var0.calculateLowestVisibleTickValue();
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
//                    final org.jfree.chart.axis.NumberTickUnit var1 = org.jfree.chart.axis.NumberAxis.DEFAULT_TICK_UNIT;
//                    var0.centerRange((double) 3.0d);
//                    var0.setTickUnit((org.jfree.chart.axis.NumberTickUnit) var1);
//                    var0.centerRange((double) 0.1d);
//                    var0.setTickUnit((org.jfree.chart.axis.NumberTickUnit) null);
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
