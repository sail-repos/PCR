//package test.examples;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import scheduler.reex.JUnit4MCRRunner;
//import static org.junit.Assert.fail;
//
//@RunWith(JUnit4MCRRunner.class)
//public class SharedPoolDataSourceTester {
//
//    @Test
//    public void test() throws Throwable {
//
//        try {
//            new SharedPoolDataSourceTester().run();
//        } catch (Exception e) {
//            fail();
//        }
//    }
//
//    private void run() throws Throwable {
//
//        final org.apache.commons.dbcp.datasources.SharedPoolDataSource var0 = new org.apache.commons.dbcp.datasources.SharedPoolDataSource();
//        var0.setRollbackAfterValidation((boolean) true);
//        final boolean var1 = var0.isRollbackAfterValidation();
//        final int var2 = var0.getMinEvictableIdleTimeMillis();
//        var0.setMaxIdle((int) -3);
//        final boolean var3 = var0.isTestOnReturn();
//
//        Thread t1 = new Thread(new Runnable() {
//            public void run() {
//                try {
//
//                    var0.setDataSourceName((java.lang.String) null);
//                    final java.lang.String var4 = var0.getDataSourceName();
//                    var0.setDefaultAutoCommit((boolean) true);
//                    var0.setTestOnReturn((boolean) true);
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
//                    var0.setDefaultAutoCommit((boolean) true);
//                    var0.setTestOnReturn((boolean) true);
//                    final java.lang.String var4 = var0.getDataSourceName();
//                    var0.setDataSourceName((java.lang.String) "");
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
