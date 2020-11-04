//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class TimeSeriesTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new TimeSeriesTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.jfree.data.time.TimeSeries var0 = new org.jfree.data.time.TimeSeries((java.lang.String) "B+_;", (java.lang.Class) null);
//        final org.jfree.data.time.TimeSeries var1 = var0.createCopy((int) 0, (int) 100);
//        final org.jfree.data.time.TimeSeries var2 = var1.createCopy((int) 1, (int) 100);
//        final org.jfree.data.time.TimeSeries var3 = var2.createCopy((int) 3, (int) 100);
//        final java.lang.Class var4 = var3.getTimePeriodClass();
//        final org.jfree.data.time.TimeSeries var5 = new org.jfree.data.time.TimeSeries((java.lang.String) "", (java.lang.Class) var4);
//        final org.jfree.data.time.TimeSeries var6 = var5.createCopy((int) -1, (int) 100);
//        final org.jfree.data.time.TimeSeries var7 = var6.addAndOrUpdate((org.jfree.data.time.TimeSeries) var5);
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var7.setRangeDescription((java.lang.String) null);
//                    final org.jfree.data.time.Day var8 = new org.jfree.data.time.Day();
//                    final org.jfree.data.time.RegularTimePeriod var9 = var8.previous();
//                    final org.jfree.data.time.TimeSeriesDataItem var10 = var7.addOrUpdate((org.jfree.data.time.RegularTimePeriod) var9, (java.lang.Number) null);
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
//                    final org.jfree.data.time.TimeSeries var8 = new org.jfree.data.time.TimeSeries((java.lang.String) null, (java.lang.Class) var4);
//                    final java.lang.Number var9 = var8.getValue((org.jfree.data.time.RegularTimePeriod) null);
//                    final org.jfree.data.time.TimeSeriesDataItem var10 = var7.addOrUpdate((org.jfree.data.time.RegularTimePeriod) null, (java.lang.Number) var9);
//                    var7.setRangeDescription((java.lang.String) null);
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
