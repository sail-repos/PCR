//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class RequestTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new RequestTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.apache.catalina.connector.Request var0 = new org.apache.catalina.connector.Request();
//        var0.clearHeaders();
//        final org.apache.tomcat.util.buf.MessageBytes var1 = var0.getServletPathMB();
//        final boolean var2 = var0.isRequestedSessionIdFromCookie();
//        var0.setRequestedSessionCookie((boolean) var2);
//        final java.lang.String var3 = var0.getAuthType();
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.recycle();
//                    final org.apache.catalina.Context var4 = var0.getContext();
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
//                    final org.apache.catalina.Context var4 = var0.getContext();
//                    var0.recycle();
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
