//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class InputBufferTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new InputBufferTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.apache.catalina.connector.InputBuffer var0 = new org.apache.catalina.connector.InputBuffer((int) 1);
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.setRequest((org.apache.coyote.Request) null);
//                    final int var1 = var0.read((byte[]) null, (int) 2, (int) 1);
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
//                    final byte[] var1 = org.apache.tomcat.util.buf.ByteChunk.convertToBytes((java.lang.String) "");
//                    final int var2 = var0.read((byte[]) var1, (int) 0, (int) -1);
//                    final org.apache.coyote.Request var3 = new org.apache.coyote.Request();
//                    var0.setRequest((org.apache.coyote.Request) var3);
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
