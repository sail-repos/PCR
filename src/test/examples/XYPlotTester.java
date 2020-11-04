//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class XYPlotTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new XYPlotTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.jfree.chart.plot.XYPlot var0 = new org.jfree.chart.plot.XYPlot();
//        final org.jfree.chart.axis.AxisLocation var1 = var0.getRangeAxisLocation();
//        final boolean var2 = var0.isRangeCrosshairLockedOnData();
//        var0.setFixedDomainAxisSpace((org.jfree.chart.axis.AxisSpace) null);
//        final float var3 = var0.getForegroundAlpha();
//        var0.setRangeCrosshairLockedOnData((boolean) var2);
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.setOutlineStroke((java.awt.Stroke) null);
//                    final org.jfree.data.xy.XYDataset var4 = var0.getDataset((int) -2);
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
//                    final org.jfree.data.xy.XYDataset var4 = var0.getDataset((int) -2);
//                    final java.awt.Stroke var5 = org.jfree.chart.renderer.AbstractRenderer.DEFAULT_OUTLINE_STROKE;
//                    var0.setOutlineStroke((java.awt.Stroke) var5);
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
